package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.clazz.IdleAnimation;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.epicfight_annoyingvillagers.util.EscapeAnimationCompat;
import com.pla.epicfight_annoyingvillagers.util.IdleAnimationUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

/** Reuses AV's goal selection, duration, interruption checks and random cooldown. */
@Mixin(value = AVNpc.class, remap = false)
public abstract class AVNpcIdleAnimationMixin {
    @Unique private AssetAccessor<? extends StaticAnimation> av_efm$idleAnimation;

    @Inject(method = "isIdleAnimationGoalAvailable", at = @At("HEAD"), cancellable = true)
    private void idleAnimationAvailable(CallbackInfoReturnable<Boolean> cir) {
        AVNpc self = (AVNpc) (Object) this;
        cir.setReturnValue(EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class) != null);
    }

    @Inject(method = "canStartIdleAnimationGoal", at = @At("HEAD"))
    private void canStartIdle(IdleAnimation choice, CallbackInfoReturnable<Boolean> cir) {
        AVNpc self = (AVNpc) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch == null || self.isStrolling() || self.isLocked() || self.isUsingItem()
                || self.isSleeping() || self.isRecoveryActionActive() || self.isRecoveryDigging()
                || RigAnimationController.hasActiveAnimation(self) || EpicfightUtil.isStunned(self)
                || patch.getEntityState().inaction() || !patch.getEntityState().canBasicAttack()
                || patch instanceof AdvancedMobPatch<?> advanced && advanced.isCombatActionLocked()) {
            cir.setReturnValue(false);
            return;
        }
        var player = patch.getAnimator().getPlayerFor(null);
        cir.setReturnValue(player != null && player.getRealAnimation() == Animations.EMPTY_ANIMATION);
    }

    @Inject(method = "canContinueIdleAnimationGoal", at = @At("HEAD"), cancellable = true)
    private void canContinueIdle(IdleAnimation choice, int ticksLeft, CallbackInfoReturnable<Boolean> cir) {
        AVNpc self = (AVNpc) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch == null || this.av_efm$idleAnimation == null || self.isLocked()
                || self.isUsingItem() || self.isSleeping() || self.isStrolling()
                || self.isRecoveryActionActive() || self.isRecoveryDigging()
                || RigAnimationController.hasActiveAnimation(self) || EpicfightUtil.isStunned(self)) {
            cir.setReturnValue(false);
            return;
        }
        var player = patch.getAnimator().getPlayerFor(this.av_efm$idleAnimation);
        cir.setReturnValue(player != null && this.av_efm$idleAnimation.equals(player.getRealAnimation()));
    }

    @Inject(method = "onIdleAnimationGoalStart", at = @At("HEAD"), cancellable = true)
    private void startIdle(IdleAnimation choice, CallbackInfo ci) {
        AVNpc self = (AVNpc) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        this.av_efm$idleAnimation = IdleAnimationUtil.resolveIdleAnimation(choice);
        if (patch != null && this.av_efm$idleAnimation != null && !self.level().isClientSide()) {
            if (patch instanceof AdvancedMobPatch<?> advanced) advanced.lockCombatActions(this);
            patch.playAnimationSynchronized(this.av_efm$idleAnimation, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "onIdleAnimationGoalTick", at = @At("HEAD"), cancellable = true)
    private void tickIdle(IdleAnimation choice, CallbackInfo ci) {
        // The selected idle emotes loop themselves. Do not replay over combat or hit reactions.
        ci.cancel();
    }

    @Inject(method = "onIdleAnimationGoalStop", at = @At("HEAD"), cancellable = true)
    private void stopIdle(IdleAnimation choice, CallbackInfo ci) {
        AVNpc self = (AVNpc) (Object) this;
        if (this.av_efm$idleAnimation != null) {
            EscapeAnimationCompat.stop(self, this.av_efm$idleAnimation);
            this.av_efm$idleAnimation = null;
        }
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch instanceof AdvancedMobPatch<?> advanced) advanced.unlockCombatActions(this);
        ci.cancel();
    }
}
