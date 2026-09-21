package com.pla.epicfight_annoyingvillagers.mixin.efclash_blade;

import com.pla.annoyingvillagers.clazz.HookDisarmLaunch;
import com.pla.annoyingvillagers.entity.*;
import com.pla.epicfight_annoyingvillagers.animations.KickAttackAnimation;
import com.pla.epicfight_annoyingvillagers.config.EpicFightAnnoyingVillagersConfig;
import com.pla.epicfight_annoyingvillagers.gameasset.*;
import com.pla.annoyingvillagers.item.FlankerHookedSwordItem;
import com.pla.annoyingvillagers.item.HookedDiamondSwordItem;
import com.pla.annoyingvillagers.item.HookedGoldenSwordItem;
import com.pla.annoyingvillagers.item.HookedIronSwordItem;
import com.pla.epicfight_annoyingvillagers.util.CommonUtil;
import com.pla.epicfight_annoyingvillagers.util.DangerousReactionUtil;
import com.pla.epicfight_annoyingvillagers.util.EpicFightNightFallUtil;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.efclash_blade.event.MobClashBladeEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;
import java.util.Random;

@Mixin(value = MobClashBladeEvent.class, remap = false)
public class MobClashBladeMixin {
    @Inject(method = "customAdditionClashBladeLogic", at = @At("HEAD"), cancellable = true)
    private static void addMoreClashBladeCondition(LivingIncomingDamageEvent livingAttackEvent,
                                                   LivingEntityPatch<?> defenderLivingEntityPatch,
                                                   AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                   EntityState defenderEntityState, Entity attacker, Entity defender,
                                                   CallbackInfoReturnable<Boolean> cir) {
        if (EpicfightUtil.isLongHitAnimation(defenderDynamicAnimation, defenderLivingEntityPatch)) {
            cir.setReturnValue(false);
            return;
        }

        // Auto clash while playing animation
        if (defender instanceof AegisHerobrineEntity
                && defenderDynamicAnimation == AnimsEnderAegis.ENDER_AEGIS_INNATE
                && defenderEntityState.getLevel() == 3) {
            cir.setReturnValue(true);
            return;
        }

    }

    @Inject(method = "customPreAdditionClashBlade", at = @At("HEAD"), cancellable = true)
    private static void customLogicBeforeClashing(LivingIncomingDamageEvent livingAttackEvent,
                                                  LivingEntityPatch<?> defenderLivingEntityPatch,
                                                  AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                  EntityState defenderEntityState, Entity attacker,
                                                  Entity defender, int clashBy,
                                                  CallbackInfo ci) {
        if (defender instanceof LivingEntity livingEntity
                && defender.level() instanceof ServerLevel serverLevel) {
            // Herobrine playing animation
            if (clashBy != 0) {
                if (ModList.get().isLoaded("efn")) {
                    if (defender instanceof AegisHerobrineEntity || defender instanceof GlaiveHerobrineEntity
                            || defender instanceof SledgehammerHerobrineEntity || defender instanceof ReaperHerobrineEntity
                            || defender instanceof SwordsmanHerobrineEntity || defender instanceof ShadowHerobrineEntity) {
//                        HerobrineMob herobrineMob = (HerobrineMob) defender;
//                        if (herobrineMob.getLivingEntityPatch() != null) {
//                            EpicFightNightFallUtil.playEfnGuardHit(herobrineMob.getLivingEntityPatch(), herobrineMob.getEfnGuardHitState(), livingAttackEvent.getSource());
//                            herobrineMob.postPlayEfnGuardHit();
//                        }
                    }
                } else {
                    if (defender instanceof AegisHerobrineEntity || defender instanceof GlaiveHerobrineEntity
                            || defender instanceof SledgehammerHerobrineEntity || defender instanceof ReaperHerobrineEntity) {
                        defenderLivingEntityPatch.playAnimationSynchronized(AnimsAgony.AGONY_GUARD_HIT_1, 0.0F);
                    }
                    if (defender instanceof SwordsmanHerobrineEntity) {
                        defenderLivingEntityPatch.playAnimationSynchronized(AnimsSolar.SOLAR_GUARD_HIT, 0.0F);
                    }
                }
            }
        }
    }

    @Inject(method = "blacklistClashBladeAnimation", at = @At("HEAD"), cancellable = true)
    private static void rejectClashBladeFromAnimationsCondition(LivingIncomingDamageEvent livingAttackEvent,
                                                                LivingEntityPatch<?> defenderLivingEntityPatch,
                                                                AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                                                EntityState defenderEntityState, Entity attacker, Entity defender,
                                                                CallbackInfoReturnable<Boolean> cir) {
        if (defender instanceof LivingEntity livingDefender
                && CommonUtil.isHookSword(livingDefender.getMainHandItem())
                && CommonUtil.isHookSwordClashAnimation(defenderDynamicAnimation)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "customPostAdditionClashBlade", at = @At("HEAD"))
    private static void moreLogicAfterClashing(LivingIncomingDamageEvent livingAttackEvent,
                                               LivingEntityPatch<?> defenderLivingEntityPatch,
                                               AssetAccessor<? extends StaticAnimation> defenderDynamicAnimation,
                                               EntityState defenderEntityState, Entity attacker,
                                               Entity defender, int clashBy,
                                               CallbackInfo ci) {
        if (!(defender.level() instanceof ServerLevel serverLevel)) return;

        // Hook Sword
        if (attacker instanceof LivingEntity livingAttacker && defender instanceof LivingEntity defenderLivingDefender) {
            if (defenderLivingDefender.getMainHandItem().getItem() instanceof HookedIronSwordItem
                    || defenderLivingDefender.getMainHandItem().getItem() instanceof HookedGoldenSwordItem
                    || defenderLivingDefender.getMainHandItem().getItem() instanceof HookedDiamondSwordItem
                    || defenderLivingDefender.getMainHandItem().getItem() instanceof FlankerHookedSwordItem) {
                if (defenderDynamicAnimation == AnimsAVSword.HOOK_SWORD_INNATE1) {
                    CommonUtil.applyHookClashDisarmLogic(
                            defenderLivingDefender,
                            livingAttacker,
                            serverLevel,
                            AVAnimations.KNOCKDOWN_RIGHT,
                            HookDisarmLaunch.RIGHT
                    );
                }

                if (defenderDynamicAnimation == AnimsAVSword.HOOK_SWORD_INNATE2) {
                    CommonUtil.applyHookClashDisarmLogic(
                            defenderLivingDefender,
                            livingAttacker,
                            serverLevel,
                            AVAnimations.KNOCKDOWN_LEFT,
                            HookDisarmLaunch.LEFT
                    );
                }

                if (defenderDynamicAnimation == AnimsAVSword.HOOK_SWORD_DUAL_INNATE || defenderDynamicAnimation == AnimsAVSword.FLANKER_HOOK_SWORD_INNATE) {
                    CommonUtil.applyHookClashDisarmLogic(
                            defenderLivingDefender,
                            livingAttacker,
                            serverLevel,
                            AVAnimations.STUN_BACK,
                            HookDisarmLaunch.BACKWARD
                    );
                }
            }
        }

        LivingEntityPatch<?> attackerLivingEntityPatch = EpicFightCapabilities.getEntityPatch(attacker, LivingEntityPatch.class);

        // Clash kick post
        if (clashBy == 0) {
            if (attackerLivingEntityPatch != null) {
                AssetAccessor<? extends StaticAnimation> attackerDynamicAnimation = Objects.requireNonNull(attackerLivingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (attackerDynamicAnimation != null) {
                    if (attackerDynamicAnimation.get() instanceof KickAttackAnimation) {
                        EpicfightUtil.dealStaminaDamageByPercentage(
                                livingAttackEvent.getSource(),
                                attackerLivingEntityPatch,
                                EpicFightAnnoyingVillagersConfig.KICK_STAMINA_DECREASE_PERCENTAGE.get()
                        );
                    }
                }
            }
        }

    }
}
