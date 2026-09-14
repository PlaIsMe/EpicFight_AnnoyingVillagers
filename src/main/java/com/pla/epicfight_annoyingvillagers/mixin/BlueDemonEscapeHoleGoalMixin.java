package com.pla.epicfight_annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.goal.BlueDemonEscapeHoleGoal;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.epicfight_annoyingvillagers.util.EscapeAnimationCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BlueDemonEscapeHoleGoal.class, remap = false)
public abstract class BlueDemonEscapeHoleGoalMixin {
    @Inject(method = "getZiplineAnimationDurationTicks", at = @At("HEAD"), cancellable = true)
    private void getZiplineAnimationDurationTicks(CallbackInfoReturnable<Integer> cir) {
        BlueDemonEscapeHoleGoal self = (BlueDemonEscapeHoleGoal) (Object) this;
        cir.setReturnValue(EscapeAnimationCompat.durationTicks(AVAnimations.ZIPLINE));
    }

    @Inject(method = "playHeldZiplineAnimation", at = @At("HEAD"), cancellable = true)
    private void playHeldZiplineAnimation(CallbackInfo ci) {
        BlueDemonEscapeHoleGoal self = (BlueDemonEscapeHoleGoal) (Object) this;
        EscapeAnimationCompat.play(self.mob, AVAnimations.ZIPLINE);
        ci.cancel();
    }

    @Inject(method = "isZiplineAnimationActive", at = @At("HEAD"), cancellable = true)
    private void isZiplineAnimationActive(CallbackInfoReturnable<Boolean> cir) {
        BlueDemonEscapeHoleGoal self = (BlueDemonEscapeHoleGoal) (Object) this;
        cir.setReturnValue(EpicfightUtil.isPlaying(self.mob, AVAnimations.ZIPLINE));
    }

    @Inject(method = "getExitRollAnimationDurationTicks", at = @At("HEAD"), cancellable = true)
    private void getExitRollAnimationDurationTicks(CallbackInfoReturnable<Integer> cir) {
        BlueDemonEscapeHoleGoal self = (BlueDemonEscapeHoleGoal) (Object) this;
        cir.setReturnValue(EscapeAnimationCompat.durationTicks(EscapeAnimationCompat.roll(self.exitRoll)));
    }

    @Inject(method = "isExitRollAnimationActive", at = @At("HEAD"), cancellable = true)
    private void isExitRollAnimationActive(CallbackInfoReturnable<Boolean> cir) {
        BlueDemonEscapeHoleGoal self = (BlueDemonEscapeHoleGoal) (Object) this;
        cir.setReturnValue(EpicfightUtil.isPlaying(self.mob, EscapeAnimationCompat.roll(self.exitRoll)));
    }

    @Inject(method = "playExitRollAnimation", at = @At("HEAD"), cancellable = true)
    private void playExitRollAnimation(CallbackInfo ci) {
        BlueDemonEscapeHoleGoal self = (BlueDemonEscapeHoleGoal) (Object) this;
        EscapeAnimationCompat.play(self.mob, EscapeAnimationCompat.roll(self.exitRoll));
        ci.cancel();
    }

    @Inject(method = "stopEscapeAnimation", at = @At("HEAD"), cancellable = true)
    private void stopEscapeAnimation(CallbackInfo ci) {
        BlueDemonEscapeHoleGoal self = (BlueDemonEscapeHoleGoal) (Object) this;
        EscapeAnimationCompat.stop(self.mob, AVAnimations.ZIPLINE);
        EscapeAnimationCompat.stop(self.mob, EscapeAnimationCompat.roll(self.exitRoll));
        ci.cancel();
    }

}
