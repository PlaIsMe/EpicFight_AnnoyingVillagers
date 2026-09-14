package com.pla.epicfight_annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.goal.AdvancedEscapeHoleGoal;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.epicfight_annoyingvillagers.util.EscapeAnimationCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.gameasset.Animations;

@Mixin(value = AdvancedEscapeHoleGoal.class, remap = false)
public abstract class AdvancedEscapeHoleGoalMixin {
    @Inject(method = "prepareRandomExitRoll", at = @At("HEAD"), cancellable = true)
    private void prepareRandomExitRoll(CallbackInfoReturnable<RigAnimationId> cir) {
        AdvancedEscapeHoleGoal<?> self = (AdvancedEscapeHoleGoal<?>) (Object) this;
        RigAnimationId roll = self.mob.getRandom().nextBoolean() ? RigAnimationId.ROLL_FORWARD : RigAnimationId.ROLL_BACKWARD;
        double dx = self.exitPosition.x - self.mob.getX();
        double dz = self.exitPosition.z - self.mob.getZ();
        if (roll == RigAnimationId.ROLL_BACKWARD) {
            dx = -dx;
            dz = -dz;
        }
        if (dx * dx + dz * dz > 1.0E-6D) {
            float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
            self.mob.setYRot(yaw);
            self.mob.yBodyRot = yaw;
            self.mob.yHeadRot = yaw;
        }
        cir.setReturnValue(roll);
    }

    @Inject(method = "isEscapeAttackLocked", at = @At("HEAD"), cancellable = true)
    private void isEscapeAttackLocked(CallbackInfoReturnable<Boolean> cir) {
        AdvancedEscapeHoleGoal<?> self = (AdvancedEscapeHoleGoal<?>) (Object) this;
        AdvancedMobPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.mob, AdvancedMobPatch.class);
        cir.setReturnValue(patch != null && patch.isCombatActionLocked());
    }

    @Inject(method = "hasBlockingEscapeAnimation", at = @At("HEAD"), cancellable = true)
    private void hasBlockingEscapeAnimation(CallbackInfoReturnable<Boolean> cir) {
        AdvancedEscapeHoleGoal<?> self = (AdvancedEscapeHoleGoal<?>) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class);
        cir.setReturnValue(patch != null && patch.getEntityState().inaction()
                && patch.getAnimator().getPlayerFor(null) != null
                && !(patch.getAnimator().getPlayerFor(null).getRealAnimation().get() instanceof AttackAnimation));
    }

    @Inject(method = "acquireEscapeAttackLock", at = @At("HEAD"), cancellable = true)
    private void acquireEscapeAttackLock(CallbackInfo ci) {
        AdvancedEscapeHoleGoal<?> self = (AdvancedEscapeHoleGoal<?>) (Object) this;
        AdvancedMobPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.mob, AdvancedMobPatch.class);
        if (patch != null) patch.lockCombatActions(self);
        ci.cancel();
    }

    @Inject(method = "releaseEscapeAttackLock", at = @At("HEAD"), cancellable = true)
    private void releaseEscapeAttackLock(CallbackInfo ci) {
        AdvancedEscapeHoleGoal<?> self = (AdvancedEscapeHoleGoal<?>) (Object) this;
        AdvancedMobPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.mob, AdvancedMobPatch.class);
        if (patch != null) patch.unlockCombatActions(self);
        ci.cancel();
    }

    @Inject(method = "stopEscapeShieldGuard", at = @At("HEAD"), cancellable = true)
    private void stopEscapeShieldGuard(CallbackInfo ci) {
        AdvancedEscapeHoleGoal<?> self = (AdvancedEscapeHoleGoal<?>) (Object) this;
        AdvancedMobPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.mob, AdvancedMobPatch.class);
        if (patch != null) patch.cancelGuard();
        ci.cancel();
    }

    @Inject(method = "stopActiveEscapeProfileAttack", at = @At("HEAD"), cancellable = true)
    private void stopActiveEscapeProfileAttack(CallbackInfo ci) {
        AdvancedEscapeHoleGoal<?> self = (AdvancedEscapeHoleGoal<?>) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class);
        if (patch != null && patch.getAnimator().getPlayerFor(null) != null
                && patch.getAnimator().getPlayerFor(null).getRealAnimation().get() instanceof AttackAnimation) {
            EscapeAnimationCompat.stop(self.mob, patch.getAnimator().getPlayerFor(null).getRealAnimation());
        }
        ci.cancel();
    }

    @Inject(method = "isEscapeRigStunned", at = @At("HEAD"), cancellable = true)
    private void isEscapeRigStunned(CallbackInfoReturnable<Boolean> cir) {
        AdvancedEscapeHoleGoal<?> self = (AdvancedEscapeHoleGoal<?>) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.mob, LivingEntityPatch.class);
        cir.setReturnValue(patch != null && patch.isStunned());
    }

    @Inject(method = "chooseEscapeRollAnimation", at = @At("HEAD"), cancellable = true)
    private void chooseEscapeRollAnimation(CallbackInfoReturnable<RigAnimationId> cir) {
        AdvancedEscapeHoleGoal<?> self = (AdvancedEscapeHoleGoal<?>) (Object) this;
        cir.setReturnValue(self.mob.getRandom().nextBoolean() ? RigAnimationId.ROLL_FORWARD : RigAnimationId.ROLL_BACKWARD);
    }

    @Inject(method = "isBackwardEscapeRoll", at = @At("HEAD"), cancellable = true)
    private void isBackwardEscapeRoll(RigAnimationId animation, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(EscapeAnimationCompat.roll(animation) == Animations.BIPED_ROLL_BACKWARD);
    }
}
