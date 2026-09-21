package com.pla.epicfight_annoyingvillagers.capabilities;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;
import yesman.epicfight.registry.entries.EpicFightAttributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.WeakHashMap;

/** Bridges native mob stamina APIs and supplies kick stamina when a patch has none. */
public final class MobStamina {
    private static final float DEFAULT_MAX_STAMINA = 15.0F;
    private static final int REGEN_DELAY_TICKS = 100;
    private static final float REGEN_PER_TICK = 0.01F;

    // Server-only, transient state. Values must not retain their weak patch keys.
    private static final Map<MobPatch<?>, StaminaPool> FALLBACK_POOLS = new WeakHashMap<>();
    private static final ClassValue<StaminaMethods> METHODS = new ClassValue<>() {
        @Override
        protected StaminaMethods computeValue(Class<?> type) {
            return new StaminaMethods(type);
        }
    };

    private MobStamina() {
    }

    public static float getDecreaseValue(MobPatch<?> patch, double percentage) {
        StaminaMethods methods = METHODS.get(patch.getClass());
        if (methods.supported()) {
            try {
                float current = ((Number) methods.getStamina.invoke(patch)).floatValue();
                float maximum = ((Number) methods.getMaxStamina.invoke(patch)).floatValue();
                return decrease(current, maximum, percentage);
            } catch (ReflectiveOperationException exception) {
                methods.reportFailure(patch, exception);
                return 0.0F;
            }
        }

        float maximum = getFallbackMaxStamina(patch);
        return decrease(getPool(patch, maximum).stamina, maximum, percentage);
    }

    /**
     * Returns true when the caller should neutralize the mob. Native damage handlers
     * own their break/recovery state and effects, so they must not be neutralized twice.
     */
    public static boolean dealStaminaDamage(DamageSource source, float amount, MobPatch<?> patch) {
        StaminaMethods methods = METHODS.get(patch.getClass());
        if (methods.supported()) {
            try {
                if (methods.damage != null) {
                    float previous = ((Number) methods.getStamina.invoke(patch)).floatValue();
                    Object result = methods.damage.invoke(patch, source, amount);
                    boolean broke = methods.damage.getReturnType() == boolean.class
                            ? Boolean.TRUE.equals(result)
                            : previous > 0.0F && ((Number) methods.getStamina.invoke(patch)).floatValue() <= 0.0F;
                    if (broke) {
                        suppressHitStun(source);
                    }
                    return false;
                }
                float stamina = ((Number) methods.getStamina.invoke(patch)).floatValue();
                if (!Float.isFinite(stamina) || stamina <= 0.0F) {
                    return false;
                }
                methods.setStamina.invoke(patch, Math.max(0.0F, stamina - amount));
                return amount >= stamina;
            } catch (ReflectiveOperationException exception) {
                // A native handler may have partially mutated its state before throwing.
                // Never damage a separate fallback pool after a failed native call.
                methods.reportFailure(patch, exception);
                return false;
            }
        }

        StaminaPool pool = getPool(patch, getFallbackMaxStamina(patch));
        if (pool.stamina <= 0.0F) {
            return false;
        }
        float previous = pool.stamina;
        pool.stamina = Math.max(0.0F, previous - amount);
        pool.regenerateAfter = patch.getOriginal().level().getGameTime() + REGEN_DELAY_TICKS;
        return amount >= previous;
    }

    private static float decrease(float current, float maximum, double percentage) {
        if (!Float.isFinite(current) || !Float.isFinite(maximum) || current <= 0.0F || maximum <= 0.0F) {
            return 0.0F;
        }
        return (float) Math.min(current, maximum * percentage);
    }

    private static float getFallbackMaxStamina(MobPatch<?> patch) {
        AttributeInstance attribute = patch.getOriginal().getAttribute(EpicFightAttributes.MAX_STAMINA);
        float maximum = attribute == null ? DEFAULT_MAX_STAMINA : (float) attribute.getValue();
        return Float.isFinite(maximum) && maximum > 0.0F ? maximum : DEFAULT_MAX_STAMINA;
    }

    private static StaminaPool getPool(MobPatch<?> patch, float maximum) {
        long now = patch.getOriginal().level().getGameTime();
        StaminaPool pool = FALLBACK_POOLS.computeIfAbsent(patch, ignored -> new StaminaPool(maximum, now));
        // Compute regeneration on access instead of adding a tick handler for every mob.
        long elapsed = Math.max(0L, now - Math.max(pool.lastUpdate, pool.regenerateAfter));
        pool.stamina = Math.min(maximum, pool.stamina + elapsed * maximum * REGEN_PER_TICK);
        pool.lastUpdate = now;
        return pool;
    }

    private static void suppressHitStun(DamageSource source) {
        if (source instanceof EpicFightDamageSource epicSource) {
            epicSource.setStunType(StunType.NONE);
            epicSource.addRuntimeTag(EpicFightDamageTypeTags.NO_STUN);
        }
    }

    private static final class StaminaPool {
        private float stamina;
        private long lastUpdate;
        private long regenerateAfter;

        private StaminaPool(float maximum, long now) {
            this.stamina = maximum;
            this.lastUpdate = now;
        }
    }

    private static final class StaminaMethods {
        private final Method getStamina;
        private final Method getMaxStamina;
        private final Method setStamina;
        private final Method damage;
        private boolean failureReported;

        private StaminaMethods(Class<?> type) {
            this.getStamina = findMethod(type, "getStamina", float.class);
            this.getMaxStamina = findMethod(type, "getMaxStamina", float.class);
            this.setStamina = findMethod(type, "setStamina", void.class, float.class);
            Method damage = findMethod(type, "dealStaminaDamage", boolean.class, DamageSource.class, float.class);
            this.damage = damage != null ? damage
                    : findMethod(type, "dealStaminaDamage", void.class, DamageSource.class, float.class);
        }

        private boolean supported() {
            return getStamina != null && getMaxStamina != null && (damage != null || setStamina != null);
        }

        private static Method findMethod(Class<?> type, String name, Class<?> returnType, Class<?>... parameters) {
            try {
                Method method = type.getMethod(name, parameters);
                return method.getReturnType() == returnType && !Modifier.isStatic(method.getModifiers()) ? method : null;
            } catch (NoSuchMethodException exception) {
                return null;
            }
        }

        private void reportFailure(MobPatch<?> patch, ReflectiveOperationException exception) {
            if (!failureReported) {
                failureReported = true;
                Throwable cause = exception instanceof InvocationTargetException invocation ? invocation.getCause() : exception;
                EpicFightAnnoyingVillagers.LOGGER.warn("Cannot access native stamina for mob patch {}", patch.getClass().getName(), cause);
            }
        }
    }
}
