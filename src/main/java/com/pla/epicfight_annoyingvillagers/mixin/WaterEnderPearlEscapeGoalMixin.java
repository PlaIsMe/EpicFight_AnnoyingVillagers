package com.pla.epicfight_annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.goal.WaterEnderPearlEscapeGoal;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.epicfight_annoyingvillagers.util.EscapeAnimationCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = WaterEnderPearlEscapeGoal.class, remap = false)
public abstract class WaterEnderPearlEscapeGoalMixin {
    @Inject(method = "playPearlAnimation", at = @At("HEAD"), cancellable = true)
    private void playPearlAnimation(CallbackInfo ci) {
        WaterEnderPearlEscapeGoal self = (WaterEnderPearlEscapeGoal) (Object) this;
        EscapeAnimationCompat.play(self.mob, AVAnimations.POINT_LEFT_HAND_TOWARD);
        ci.cancel();
    }

    @Inject(method = "isEpicFightLongHitAnimation", at = @At("HEAD"), cancellable = true)
    private void isEpicFightLongHitAnimation(CallbackInfoReturnable<Boolean> cir) {
        WaterEnderPearlEscapeGoal self = (WaterEnderPearlEscapeGoal) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class);
        cir.setReturnValue(patch != null && patch.getAnimator().getPlayerFor(null) != null
                && EpicfightUtil.isLongHitAnimation(patch.getAnimator().getPlayerFor(null).getRealAnimation(), patch));
    }
}
