package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.event.ThrowingPearlKeyPressedEvent;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import java.util.Objects;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = ThrowingPearlKeyPressedEvent.class, remap = false)
public abstract class ThrowingPearlKeyPressedEventMixin {
    @Inject(method = "playThrowingPearlAnimation", at = @At("HEAD"), cancellable = true)
    private static void playThrowingPearlAnimation(Entity entity, CallbackInfo ci) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            livingEntityPatch.playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "efmConditionToExecute", at = @At("HEAD"), cancellable = true)
    private static void efmConditionToExecute(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) {
            cir.setReturnValue(false);
            return;
        }
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, livingEntityPatch)) {
            cir.setReturnValue(false);
            return;
        }
        if (dynamicAnimation != Animations.EMPTY_ANIMATION) {
            cir.setReturnValue(false);
            return;
        }
        cir.setReturnValue(true);
        return;
    }
}
