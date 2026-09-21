package com.pla.epicfight_annoyingvillagers.mixin.efclash_blade;

import com.pla.annoyingvillagers.clazz.HookDisarmLaunch;
import com.pla.annoyingvillagers.item.FlankerHookedSwordItem;
import com.pla.annoyingvillagers.item.HookedDiamondSwordItem;
import com.pla.annoyingvillagers.item.HookedGoldenSwordItem;
import com.pla.annoyingvillagers.item.HookedIronSwordItem;
import com.pla.efclash_blade.skill.ClashBladeSkill;
import com.pla.epicfight_annoyingvillagers.config.EpicFightAnnoyingVillagersConfig;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVSword;
import com.pla.epicfight_annoyingvillagers.util.CommonUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(value = ClashBladeSkill.class, remap = false)
public class ClashBladeMixin {
    @Inject(method = "blacklistClashBladeAnimation", at = @At("HEAD"), cancellable = true)
    private static void rejectClashBladeFromAnimationsCondition(AssetAccessor<? extends StaticAnimation> dynamicAnimation,
                                                                EntityState entityState,
                                                                ServerPlayer serverPlayer,
                                                                CallbackInfoReturnable<Boolean> cir) {
    }

    @Inject(method = "getWeaponDestroyValueOnClash", at = @At("HEAD"), cancellable = true)
    private static void forceDestroyWeaponValueOnClash(AssetAccessor<? extends StaticAnimation> dynamicAnimation,
                                                       DamageSource damageSource,
                                                       PlayerPatch<?> playerPatch,
                                                       ServerLevel serverLevel,
                                                       CallbackInfoReturnable<Integer> cir) {
    }

    @Inject(method = "moreLogicAfterClashing", at = @At("HEAD"))
    private static void forceMoreLogicAfterClash(AssetAccessor<? extends StaticAnimation> dynamicAnimation,
                                                 DamageSource damageSource,
                                                 PlayerPatch<?> playerPatch,
                                                 ServerLevel serverLevel,
                                                 CallbackInfo ci) {
        Player player = playerPatch.getOriginal();
        if (player.getMainHandItem().getItem() instanceof HookedIronSwordItem
                || player.getMainHandItem().getItem() instanceof HookedGoldenSwordItem
                || player.getMainHandItem().getItem() instanceof HookedDiamondSwordItem
                || player.getMainHandItem().getItem() instanceof FlankerHookedSwordItem) {
            if (dynamicAnimation == AnimsAVSword.HOOK_SWORD_INNATE1) {
                CommonUtil.applyHookClashDisarmLogic(
                        player,
                        damageSource,
                        serverLevel,
                        AVAnimations.KNOCKDOWN_RIGHT,
                        HookDisarmLaunch.RIGHT
                );
                return;
            }

            if (dynamicAnimation == AnimsAVSword.HOOK_SWORD_INNATE2) {
                CommonUtil.applyHookClashDisarmLogic(
                        player,
                        damageSource,
                        serverLevel,
                        AVAnimations.KNOCKDOWN_LEFT,
                        HookDisarmLaunch.LEFT
                );
                return;
            }

            if (dynamicAnimation == AnimsAVSword.HOOK_SWORD_DUAL_INNATE || dynamicAnimation == AnimsAVSword.FLANKER_HOOK_SWORD_INNATE) {
                CommonUtil.applyHookClashDisarmLogic(
                        player,
                        damageSource,
                        serverLevel,
                        AVAnimations.STUN_BACK,
                        HookDisarmLaunch.BACKWARD
                );
            }
        }
    }
}
