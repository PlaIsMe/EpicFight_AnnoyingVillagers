package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.entity.goal.AnimatedMobGoal;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.epicfight_annoyingvillagers.util.EscapeAnimationCompat;
import com.pla.epicfight_annoyingvillagers.util.GoalAnimationCompat;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = AnimatedMobGoal.class, remap = false)
public abstract class AnimatedMobGoalMixin {
    @Unique private AssetAccessor<? extends StaticAnimation> av_efm$ownedAnimation;

    @Inject(method = "isAnimationStunned", at = @At("HEAD"), cancellable = true)
    private void isAnimationStunned(CallbackInfoReturnable<Boolean> cir) {
        AnimatedMobGoal self = (AnimatedMobGoal) (Object) this;
        cir.setReturnValue(EpicfightUtil.isStunned(self.animationMob));
    }

    @Inject(method = "isAnimationBusy", at = @At("HEAD"), cancellable = true)
    private void isAnimationBusy(CallbackInfoReturnable<Boolean> cir) {
        AnimatedMobGoal self = (AnimatedMobGoal) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.animationMob, LivingEntityPatch.class);
        cir.setReturnValue(patch == null || EpicfightUtil.isStunned(self.animationMob)
                || RigAnimationController.hasActiveAnimation(self.animationMob)
                || patch.getEntityState().inaction() || !patch.getEntityState().canBasicAttack()
                || patch instanceof AdvancedMobPatch<?> advanced && advanced.isCombatActionLocked());
    }

    @Inject(method = "playGoalAnimation", at = @At("HEAD"), cancellable = true)
    private void playGoalAnimation(RigAnimationId id, LivingEntity target, CallbackInfo ci) {
        AnimatedMobGoal self = (AnimatedMobGoal) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.animationMob, LivingEntityPatch.class);
        if (patch != null && !self.animationMob.level().isClientSide()) {
            this.av_efm$ownedAnimation = GoalAnimationCompat.animation(id);
            if (target != null && target.isAlive()) self.animationMob.getLookControl().setLookAt(target, 30.0F, 30.0F);
            if (patch instanceof AdvancedMobPatch<?> advanced) advanced.lockCombatActions(self);
            patch.playAnimationSynchronized(this.av_efm$ownedAnimation, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "isGoalAnimationPlaying", at = @At("HEAD"), cancellable = true)
    private void isGoalAnimationPlaying(RigAnimationId id, CallbackInfoReturnable<Boolean> cir) {
        AnimatedMobGoal self = (AnimatedMobGoal) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.animationMob, LivingEntityPatch.class);
        boolean playing = false;
        if (patch != null && this.av_efm$ownedAnimation == GoalAnimationCompat.animation(id)) {
            var player = patch.getAnimator().getPlayerFor(this.av_efm$ownedAnimation);
            playing = player != null && !player.isEmpty() && !player.isEnd()
                    && this.av_efm$ownedAnimation.equals(player.getRealAnimation());
        }
        cir.setReturnValue(playing);
    }

    @Inject(method = "usesAnimationEvents", at = @At("HEAD"), cancellable = true)
    private void usesAnimationEvents(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "finishGoalAnimation", at = @At("HEAD"), cancellable = true)
    private void finishGoalAnimation(CallbackInfo ci) {
        AnimatedMobGoal self = (AnimatedMobGoal) (Object) this;
        if (this.av_efm$ownedAnimation != null) {
            // Does not cancel a newer animation (for example, a stun or dodge).
            EscapeAnimationCompat.stop(self.animationMob, this.av_efm$ownedAnimation);
            this.av_efm$ownedAnimation = null;
        }
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self.animationMob, LivingEntityPatch.class);
        if (patch instanceof AdvancedMobPatch<?> advanced) advanced.unlockCombatActions(self);
        ci.cancel();
    }
}
