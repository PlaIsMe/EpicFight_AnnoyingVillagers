package com.pla.epicfight_annoyingvillagers.event;

import com.pla.epicfight_annoyingvillagers.gameasset.AnimsKick;
import com.pla.epicfight_annoyingvillagers.util.EpicFightNightFallUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import yesman.epicfight.api.animation.types.KnockdownAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.registry.entries.EpicFightParticles;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.Objects;

public class KickOnKeyPressedEvent {
    private static final String NBT_KICK_CD = "KickAttackCooldown";
    private static final String NBT_STUN_ESCAPE_CD = "StunEscapeCooldown";
    private static final String NBT_KICK_COMBO = "KickCombo";

    private static boolean canKick(AssetAccessor<? extends StaticAnimation> dynamicAnimation, LivingEntityPatch<?> livingEntityPatch) {
        return dynamicAnimation.get() instanceof KnockdownAnimation
                || (ModList.get().isLoaded("efn") && EpicFightNightFallUtil.isEFNStun(dynamicAnimation));
    }

    public static void execute(final Entity entity, int strafe) {
        if (entity instanceof LivingEntity) {
            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);

            if (livingEntityPatch != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();

                if (canKick(dynamicAnimation, livingEntityPatch)) {
                    if (entity.getPersistentData().getInt(NBT_STUN_ESCAPE_CD) != 0) return;
                    entity.getPersistentData().putInt(NBT_STUN_ESCAPE_CD, 5);

                    if (entity.level() instanceof ServerLevel serverLevel && entity instanceof Player player) {
                        serverLevel.sendParticles(
                                EpicFightParticles.WHITE_AFTERIMAGE.get(),
                                entity.getX(), entity.getY(), entity.getZ(),
                                1, 0.0D, 0.0D, 0.0D, Double.longBitsToDouble(entity.getId())
                        );
                        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 60, 1, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 60, 1, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1, false, false));
                    }
                    if (strafe < 0) {
                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_LEFT, 0.0F);
                    } else if (strafe > 0) {
                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_KNOCKDOWN_WAKEUP_RIGHT, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(Animations.BIPED_ROLL_BACKWARD, 0.0F);
                    }
                    return;
                }

                if (entity.level() instanceof ServerLevel) {
                    if (dynamicAnimation != Animations.EMPTY_ANIMATION) {
                        return;
                    }
                }

                if (entity.getPersistentData().getInt(NBT_KICK_CD) != 0) return;
                boolean sneaking = entity.isShiftKeyDown();
                boolean sprinting = entity.isSprinting();

                // Default kick
                entity.getPersistentData().putInt(NBT_KICK_CD, 2);
                if (sneaking) {
                    if (sprinting && isFistPlayer(entity)) {
                        livingEntityPatch.playAnimationSynchronized(AnimsKick.KICK_COMBO, 0.0F);
                    } else {
                        livingEntityPatch.playAnimationSynchronized(AnimsKick.KICK_H, 0.0F);
                    }
                    return;
                }

                if (sprinting) {
                    livingEntityPatch.playAnimationSynchronized(AnimsKick.KICK_RUSH, 0.0F);
                    return;
                }

                double combo = entity.getPersistentData().contains(NBT_KICK_COMBO)
                        ? entity.getPersistentData().getDouble(NBT_KICK_COMBO)
                        : 0.0;

                if (combo == 0.0) {
                    livingEntityPatch.playAnimationSynchronized(AnimsKick.KICK_1, 0.0F);
                    entity.getPersistentData().putDouble(NBT_KICK_COMBO, 1);
                } else if (combo == 1.0) {
                    livingEntityPatch.playAnimationSynchronized(AnimsKick.KICK_2, 0.0F);
                    entity.getPersistentData().putDouble(NBT_KICK_COMBO, 2);
                } else if (combo == 2.0) {
                    livingEntityPatch.playAnimationSynchronized(AnimsKick.KICK_3, 0.0F);
                    entity.getPersistentData().putDouble(NBT_KICK_COMBO, 3);
                } else if (combo == 3.0) {
                    livingEntityPatch.playAnimationSynchronized(AnimsKick.KICK_4, 0.0F);
                    entity.getPersistentData().putDouble(NBT_KICK_COMBO, 4);
                } else {
                    livingEntityPatch.playAnimationSynchronized(AnimsKick.KICK_C, 0.0F);
                    entity.getPersistentData().remove(NBT_KICK_COMBO);
                }
            }
        }
    }

    private static boolean isFistPlayer(Entity entity) {
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
        if (playerPatch == null) return false;

        return playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST;
    }
}
