package com.pla.epicfight_annoyingvillagers.util;

import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.annoyingvillagers.util.ScreenShakeUtil;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import com.pla.epicfight_annoyingvillagers.config.EpicFightAnnoyingVillagersConfig;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.network.ClientboundEpicFightCameraFx;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;
import net.shelmarow.combat_evolution.ai.CEHumanoidPatch;
import net.shelmarow.combat_evolution.ai.util.CEPatchUtils;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import net.shelmarow.combat_evolution.execution.ExecutionHandler;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionHitAnimation;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.damagesource.EpicFightDamageSource;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Objects;
import java.util.Random;

public class EpicfightUtil {
    public static void forceLookAt(Entity self, Entity target, float maxYawChange, float maxPitchChange) {
        if (target == null) return;

        Vec3 eye = self.getEyePosition();
        Vec3 to = target.getEyePosition().subtract(eye);

        double dx = to.x;
        double dy = to.y;
        double dz = to.z;

        double flat = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float)(Mth.atan2(dz, dx) * (180F / Math.PI)) - 90F;
        float targetPitch = (float)(-(Mth.atan2(dy, flat) * (180F / Math.PI)));

        float yaw = Mth.approachDegrees(self.getYRot(), targetYaw, maxYawChange);
        float pitch = Mth.clamp(Mth.approachDegrees(self.getXRot(), targetPitch, maxPitchChange), -90F, 90F);

        self.setYRot(yaw);
        self.setXRot(pitch);
        self.yRotO = yaw;
        self.xRotO = pitch;

        if (self instanceof Mob mob) {
            mob.yBodyRot = yaw;
            mob.yBodyRotO = yaw;
            mob.yHeadRot = yaw;
            mob.yHeadRotO = yaw;
        }
    }

    public static double calculateGuardBreakWakeUpChance(LivingEntity entity) {
        float hpPct = entity.getHealth() / entity.getMaxHealth();

        double min = EpicFightAnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE.get();
        double max = EpicFightAnnoyingVillagersConfig.MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE.get();

        if (max < min) {
            double tmp = max;
            max = min;
            min = tmp;
        }

        double chance;
        if (max == min) {
            chance = max;
        } else {
            double t = (1.0D - hpPct) / 0.5D;
            t = Mth.clamp(t, 0.0D, 1.0D);
            chance = max - t * (max - min);
        }

        return chance;
    }

    public static void postGuardBreakWakeUp(LivingEntity entity, LivingEntityPatch<?> livingEntityPatch, ServerLevel serverLevel) {
        serverLevel.sendParticles(
                EpicFightParticles.WHITE_AFTERIMAGE.get(),
                entity.getX(), entity.getY(), entity.getZ(),
                1, 0.0D, 0.0D, 0.0D, Double.longBitsToDouble(entity.getId())
        );
        entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1, false, false));
        entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 1, false, false));
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false));

        double chooseAnimation = new Random().nextDouble(0.0D, 1.0D);
        if (chooseAnimation <= 0.4D) {
            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
        } else if (chooseAnimation <= 0.8D) {
            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
        } else {
            livingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
        }
    }

    public static Vec3 getJointWithTranslation(Entity entity, Vec3f translation, Joint joint, float handToTip, double yOffset) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) return null;

        float interpolation = 0.0F;
        OpenMatrix4f m = livingEntityPatch.getArmature()
                .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(translation);
            OpenMatrix4f.mul(m, tLocal, m);
        }

        if (handToTip != 0.0f) {
            OpenMatrix4f tipOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
            OpenMatrix4f.mul(m, tipOffset, m);
        }

        float yawRad = (float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = livingEntityPatch.getOriginal();
        return new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0) + yOffset,
                m.m32 + base.getZ()
        );
    }

    public static boolean isStunned(LivingEntity entity) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch == null) return false;
        var player = patch.getAnimator().getPlayerFor(null);
        return patch.isStunned() || player != null && !player.isEmpty() && !player.isEnd()
                && isLongHitAnimation(player.getRealAnimation(), patch);
    }

    public static boolean isLongHitAnimationNotExecutedAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation, LivingEntityPatch<?> livingEntityPatch) {
        return !(dynamicAnimation.get() instanceof ExecutionHitAnimation)
                && (dynamicAnimation.get() instanceof KnockdownAnimation
                || (ModList.get().isLoaded("efn") && EpicFightNightFallUtil.isEFNStun(dynamicAnimation))
                || ExecutionHandler.isTargetGuardBreak(dynamicAnimation, livingEntityPatch));
    }

    public static boolean isLongHitAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation, LivingEntityPatch<?> livingEntityPatch) {
        return dynamicAnimation.get() instanceof ExecutionHitAnimation
                || dynamicAnimation.get() instanceof KnockdownAnimation
                || (ModList.get().isLoaded("efn") && EpicFightNightFallUtil.isEFNStun(dynamicAnimation))
                || ExecutionHandler.isTargetGuardBreak(dynamicAnimation, livingEntityPatch);
    }

    public static boolean isDamagableHitAnimation(AssetAccessor<? extends StaticAnimation> dynamicAnimation, LivingEntityPatch<?> livingEntityPatch) {
        return dynamicAnimation.get() instanceof ExecutionHitAnimation
                || dynamicAnimation.get() instanceof KnockdownAnimation
                || ExecutionHandler.isTargetGuardBreak(dynamicAnimation, livingEntityPatch);
    }

    public static void stopAnimationSynchronized(LivingEntity entity, AssetAccessor<? extends StaticAnimation> animation) {
        if (entity == null || animation == null) {
            return;
        }

        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) {
            return;
        }

        if (livingEntityPatch.isLogicalClient()) {
            livingEntityPatch.getAnimator().stopPlaying(animation);
        } else {
            livingEntityPatch.stopPlaying(animation);
        }
    }

    public static boolean isPlaying(LivingEntity entity, AssetAccessor<? extends StaticAnimation> animation) {
        if (entity == null || animation == null) {
            return false;
        }

        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null || livingEntityPatch.getAnimator().getPlayerFor(null) == null) {
            return false;
        }

        return livingEntityPatch.getAnimator().getPlayerFor(null).getRealAnimation() == animation;
    }

    public static void cancel(LivingEntity entity, AssetAccessor<? extends StaticAnimation> animation) {
        if (isPlaying(entity, animation)) {
            stopAnimationSynchronized(entity, animation);
        }
    }

    public static void cancelLater(LivingEntity entity, AssetAccessor<? extends StaticAnimation> animation, int delayTicks) {
        if (entity == null || animation == null) {
            return;
        }

        new DelayedTask(delayTicks) {
            @Override
            public void run() {
                if (entity.isRemoved() || !entity.isAlive()) {
                    return;
                }
                EpicfightUtil.cancel(entity, animation);
            }
        };
    }

    public static void dealStaminaDamageByPercentage(DamageSource damageSource, LivingEntityPatch<?> livingEntityPatch, double percentage, boolean playStunAnimation) {
        float decrease = 0.0F;
        if (livingEntityPatch instanceof AdvancedMobPatch<?> advancedMobPatch) {
            float currentStamina = advancedMobPatch.getStamina();
            float maxStamina = advancedMobPatch.getMaxStamina();
            float staminaToDecrease = (float) (maxStamina * percentage);
            decrease = Math.min(staminaToDecrease, currentStamina);
        } else if (livingEntityPatch instanceof CEHumanoidPatch) {
            float currentStamina = CEPatchUtils.getStamina(livingEntityPatch);
            float maxStamina = CEPatchUtils.getMaxStamina(livingEntityPatch);
            float staminaToDecrease = (float) (maxStamina * percentage);
            decrease = Math.min(staminaToDecrease, currentStamina);
        } else if (livingEntityPatch instanceof PlayerPatch<?> playerPatch) {
            float currentStamina = playerPatch.getStamina();
            float maxStamina = playerPatch.getMaxStamina();
            float staminaToDecrease = (float) (maxStamina * percentage);
            decrease = Math.min(staminaToDecrease, currentStamina);
        }
        dealStaminaDamage(damageSource, decrease, livingEntityPatch, playStunAnimation);
    }

    public static void dealStaminaDamage(DamageSource damageSource, float amount, LivingEntityPatch<?> livingEntityPatch, boolean playStunAnimation) {
        if (livingEntityPatch instanceof CEHumanoidPatch<?> ceHumanoidPatch) {
            if (!ceHumanoidPatch.dealStaminaDamage(damageSource, amount) && playStunAnimation) {
                livingEntityPatch.playAnimationSynchronized(AVAnimations.STUN_BACK, 0.0F);
            }
        } else if (livingEntityPatch instanceof AdvancedMobPatch<?> advancedMobPatch) {
            if (!advancedMobPatch.dealStaminaDamage(damageSource, amount) && playStunAnimation) {
                livingEntityPatch.playAnimationSynchronized(AVAnimations.STUN_BACK, 0.0F);
            }
        } else if (livingEntityPatch instanceof PlayerPatch<?> playerPatch) {
            float stamina = playerPatch.getStamina();
            playerPatch.setStamina(stamina - amount);
            if (amount >= stamina) {
                EpicFightDamageSource efSource = damageSource instanceof EpicFightDamageSource ? (EpicFightDamageSource)damageSource : null;
                if (efSource != null) {
                    efSource.setStunType(StunType.NONE);
                    Vec3 sourcePosition = efSource.getInitialPosition();
                    if (sourcePosition != null) {
                        playerPatch.getOriginal().lookAt(EntityAnchorArgument.Anchor.FEET, sourcePosition);
                    }
                }

                if (playerPatch.applyStun(StunType.NEUTRALIZE, 0.0F)) {
                    (playerPatch.getOriginal()).forceAddEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), 100), playerPatch.getOriginal());
                    Vec3 eyePosition = (playerPatch.getOriginal()).getEyePosition();
                    Vec3 viewVec = (playerPatch.getOriginal()).getLookAngle().scale(2.0F);
                    Vec3 pos = new Vec3(eyePosition.x + viewVec.x, eyePosition.y + viewVec.y, eyePosition.z + viewVec.z);
                    (playerPatch.getOriginal()).level().addParticle(EpicFightParticles.NEUTRALIZE.get(), pos.x, pos.y, pos.z, (double)0.0F, (double)0.0F, (double)0.0F);
                    playerPatch.playSound(EpicFightSounds.NEUTRALIZE_MOBS.get(), 1.0F, 1.0F);
                }
            }
        }
    }

    public static void breakWeaponOnParryOpAttack(DamageSource damageSource) {
        Entity attacker = damageSource.getEntity();
        if (attacker instanceof Player player) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (DangerousReactionUtil.isAnimationDangerous(dynamicAnimation)) {
                    int breakValue = EpicFightAnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get();
                    if (ModList.get().isLoaded("efn")) {
                        if (EpicFightNightFallUtil.isEfnWeapons(player.getMainHandItem())) {
                            breakValue = EpicFightAnnoyingVillagersConfig.WEAPON_BREAKING_MECHANISM_VALUE.get() * EpicFightNightFallUtil.MULTIPLIER_DAMAGE_VALUE;
                        }
                    }
                    player.getMainHandItem().hurtAndBreak(breakValue, player, (livingEntity) -> {
                        livingEntity.broadcastBreakEvent(EquipmentSlot.MAINHAND);
                    });
                }
            }
        }
    }

    public static void damageBlocked(DamageSource damagesource, Entity livingentity, ServerLevel level) {
        if (livingentity == null) return;
        if (!damagesource.is(DamageTypes.IN_WALL) && !damagesource.is(DamageTypes.IN_FIRE) && !damagesource.is(DamageTypes.ON_FIRE)) {
            livingentity.playSound(EpicFightSounds.CLASH.get(), 1.0F, 1.0F);
        }
        EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO,
                livingentity, damagesource.getEntity());
        if (damagesource.getEntity() instanceof Player player) {
            ScreenShakeUtil.applyScreenShake(level, player.getOnPos().getCenter(), 1.0, 20, 4);
        }
    }

    public static void damageBlockedForce(Entity defender, Entity attacker, ServerLevel level) {
        defender.playSound(EpicFightSounds.CLASH.get(), 1.0F, 1.0F);
        EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(level, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO,
                defender, attacker);
        if (attacker instanceof Player player) {
            ScreenShakeUtil.applyScreenShake(level, player.getOnPos().getCenter(), 1.0, 20, 4);
        }
    }

    public static AnimationEvent.InTimeEvent<?> cameraZoomInEvent(float time, float fovModifier, int durationTicks) {
        return AnimationEvent.InTimeEvent.create(time, (livingEntityPatch, self, params) -> {
            LivingEntity entity = livingEntityPatch.getOriginal();
            sendEpicFightCameraFx(entity, ClientboundEpicFightCameraFx.zoomIn(fovModifier, durationTicks));
        }, AnimationEvent.Side.SERVER);
    }

    public static AnimationEvent.InTimeEvent<?> cameraZoomOutBlurEvent(float time, float blurStrength, int blurTicks) {
        return AnimationEvent.InTimeEvent.create(time, (livingEntityPatch, self, params) -> {
            LivingEntity entity = livingEntityPatch.getOriginal();
            sendEpicFightCameraFx(entity, ClientboundEpicFightCameraFx.resetZoomAndBlur(blurStrength, blurTicks));
        }, AnimationEvent.Side.SERVER);
    }

    private static void sendEpicFightCameraFx(LivingEntity entity, ClientboundEpicFightCameraFx packet) {
        if (entity instanceof ServerPlayer player) {
            EpicFightAnnoyingVillagers.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> player), packet);
        }
    }
}
