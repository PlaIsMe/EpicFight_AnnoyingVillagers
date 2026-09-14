package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.item.HookGunItem;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = HookGunItem.class, remap = false)
public abstract class HookGunItemMixin {
    @Inject(method = "getHookStartPosition", at = @At("HEAD"), cancellable = true)
    private static void getHookStartPosition(LivingEntity owner, boolean rightHand, CallbackInfoReturnable<Vec3> cir) {
        try {
            Vec3 pos = EpicfightUtil.getJointWithTranslation(
                    owner,
                    new Vec3f(0.0F, -0.3F, 0.0F),
                    rightHand ? Armatures.BIPED.get().toolR : Armatures.BIPED.get().toolL,
                    0.0F,
                    0.0D
            );

            if (pos != null) {
                cir.setReturnValue(pos);
                return;
            }
        } catch (Exception ignored) {
        }
    }

    @Inject(method = "setHookHandAnimationState", at = @At("HEAD"), cancellable = true)
    private static void setHookHandAnimationState(LivingEntity owner, boolean rightHand, byte nextState, CallbackInfo ci) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(owner, LivingEntityPatch.class);
        AssetAccessor<? extends StaticAnimation> animation = epicfightAnnoyingVillagers$hookHandAnimation(rightHand, nextState);
        String tag = HookGunItem.getHookHandAnimationTag(rightHand);
        if (patch != null && animation != null && !owner.level().isClientSide()) {
            if (owner.getPersistentData().getByte(tag) != nextState) {
                patch.playAnimationSynchronized(animation, 0.0F);
            }
            owner.getPersistentData().putByte(tag, nextState);
        }
        ci.cancel();
    }

    @Inject(method = "stopHookHandAnimations", at = @At("HEAD"), cancellable = true)
    private static void stopHookHandAnimations(LivingEntity owner, boolean rightHand, CallbackInfo ci) {
        EpicfightUtil.stopAnimationSynchronized(owner, epicfightAnnoyingVillagers$hookHandAnimation(rightHand, HookGunItem.HOOK_ANIMATION_NORMAL));
        EpicfightUtil.stopAnimationSynchronized(owner, epicfightAnnoyingVillagers$hookHandAnimation(rightHand, HookGunItem.HOOK_ANIMATION_TOP));
        ci.cancel();
    }

    @Unique
    private static AssetAccessor<? extends StaticAnimation> epicfightAnnoyingVillagers$hookHandAnimation(boolean rightHand, byte state) {
        if (state == HookGunItem.HOOK_ANIMATION_NORMAL) {
            return rightHand ? AVAnimations.HOOK_HAND_RIGHT : AVAnimations.HOOK_HAND_LEFT;
        }
        if (state == HookGunItem.HOOK_ANIMATION_TOP) {
            return rightHand ? AVAnimations.HOOK_HAND_RIGHT_TOP : AVAnimations.HOOK_HAND_LEFT_TOP;
        }
        return null;
    }
}
