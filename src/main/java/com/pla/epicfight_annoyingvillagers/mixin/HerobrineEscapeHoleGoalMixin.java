package com.pla.epicfight_annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.goal.HerobrineEscapeHoleGoal;
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

@Mixin(value = HerobrineEscapeHoleGoal.class, remap = false)
public abstract class HerobrineEscapeHoleGoalMixin {
    @Inject(method = "getFlyUpAnimationDurationTicks", at = @At("HEAD"), cancellable = true)
    private void getFlyUpAnimationDurationTicks(CallbackInfoReturnable<Integer> cir) {
        HerobrineEscapeHoleGoal self = (HerobrineEscapeHoleGoal) (Object) this;
        if (EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class) == null) return;
        cir.setReturnValue(EscapeAnimationCompat.durationTicks(AVAnimations.FLY_UP));
    }

    @Inject(method = "playFlyUpAnimation", at = @At("HEAD"), cancellable = true)
    private void playFlyUpAnimation(CallbackInfo ci) {
        HerobrineEscapeHoleGoal self = (HerobrineEscapeHoleGoal) (Object) this;
        if (EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class) == null) return;
        EscapeAnimationCompat.play(self.mob, AVAnimations.FLY_UP);
        boolean active = EpicfightUtil.isPlaying(self.mob, AVAnimations.FLY_UP);
        if (!active) self.finished = true;
        ci.cancel();
    }

    @Inject(method = "isFlyUpAnimationActive", at = @At("HEAD"), cancellable = true)
    private void isFlyUpAnimationActive(CallbackInfoReturnable<Boolean> cir) {
        HerobrineEscapeHoleGoal self = (HerobrineEscapeHoleGoal) (Object) this;
        if (EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class) == null) return;
        cir.setReturnValue(EpicfightUtil.isPlaying(self.mob, AVAnimations.FLY_UP));
    }

    @Inject(method = "getExitRollAnimationDurationTicks", at = @At("HEAD"), cancellable = true)
    private void getExitRollAnimationDurationTicks(CallbackInfoReturnable<Integer> cir) {
        HerobrineEscapeHoleGoal self = (HerobrineEscapeHoleGoal) (Object) this;
        if (EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class) == null) return;
        cir.setReturnValue(EscapeAnimationCompat.durationTicks(EscapeAnimationCompat.roll(self.exitRoll)));
    }

    @Inject(method = "isExitRollAnimationActive", at = @At("HEAD"), cancellable = true)
    private void isExitRollAnimationActive(CallbackInfoReturnable<Boolean> cir) {
        HerobrineEscapeHoleGoal self = (HerobrineEscapeHoleGoal) (Object) this;
        if (EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class) == null) return;
        cir.setReturnValue(EpicfightUtil.isPlaying(self.mob, EscapeAnimationCompat.roll(self.exitRoll)));
    }

    @Inject(method = "playExitRollAnimation", at = @At("HEAD"), cancellable = true)
    private void playExitRollAnimation(CallbackInfo ci) {
        HerobrineEscapeHoleGoal self = (HerobrineEscapeHoleGoal) (Object) this;
        if (EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class) == null) return;
        var animation = EscapeAnimationCompat.roll(self.exitRoll);
        EscapeAnimationCompat.play(self.mob, animation);
        ci.cancel();
    }

    @Inject(method = "stopEscapeAnimation", at = @At("HEAD"), cancellable = true)
    private void stopEscapeAnimation(CallbackInfo ci) {
        HerobrineEscapeHoleGoal self = (HerobrineEscapeHoleGoal) (Object) this;
        if (EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class) == null) return;
        EscapeAnimationCompat.stop(self.mob, AVAnimations.FLY_UP);
        EscapeAnimationCompat.stop(self.mob, EscapeAnimationCompat.roll(self.exitRoll));
        ci.cancel();
    }

    @Inject(method = "getFlyUpAnimationStartTick", at = @At("HEAD"), cancellable = true)
    private void getFlyUpAnimationStartTick(CallbackInfoReturnable<Integer> cir) {
        HerobrineEscapeHoleGoal self = (HerobrineEscapeHoleGoal) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class);
        if (patch == null) return;
        if (!EpicfightUtil.isPlaying(self.mob, AVAnimations.FLY_UP)) {
            cir.setReturnValue(-1);
            return;
        }
        cir.setReturnValue(self.mob.tickCount - Math.round(patch.getAnimator().getPlayerFor(null).getElapsedTime() * 20.0F));
    }

    @Inject(method = "getRemainingAuthoredFlyRise", at = @At("HEAD"), cancellable = true)
    private void getRemainingAuthoredFlyRise(float elapsed, CallbackInfoReturnable<Double> cir) {
        HerobrineEscapeHoleGoal self = (HerobrineEscapeHoleGoal) (Object) this;
        if (EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class) == null) return;
        float end = AVAnimations.FLY_UP.get().getTotalTime();
        float time = Math.min(end, Math.max(0.0F, elapsed / 20.0F));
        var coord = AVAnimations.FLY_UP.get().getCoord();
        cir.setReturnValue((double) (coord.getInterpolatedTranslation(end).y - coord.getInterpolatedTranslation(time).y));
    }

}
