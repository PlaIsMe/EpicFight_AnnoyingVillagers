package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.annoyingvillagers.util.DangerousReactionAnimations;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import com.pla.epicfight_annoyingvillagers.util.DangerousReactionUtil;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.epicfight_annoyingvillagers.util.GoalAnimationCompat;
import com.pla.epicfight_annoyingvillagers.util.ReactionAnimationTracker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = DangerousReactionAnimations.class, remap = false)
public abstract class DangerousReactionAnimationsMixin {
    @Inject(method = "isDangerous", at = @At("RETURN"), cancellable = true)
    private static void isDangerous(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && entity != null) {
            cir.setReturnValue(DangerousReactionUtil.isCurrentAnimationDangerous(
                    EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class)));
        }
    }

    @Inject(method = "animationKey", at = @At("RETURN"), cancellable = true)
    private static void animationKey(LivingEntity entity, CallbackInfoReturnable<Object> cir) {
        if (entity == null || entity instanceof Mob mob && RigAnimationController.isDangerous(mob)) return;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch == null) return;
        var player = patch.getAnimator().getPlayerFor(null);
        if (player != null && !player.isEmpty() && !player.isEnd()) cir.setReturnValue(player.getRealAnimation());
    }

    @Inject(method = "animationStartTick", at = @At("RETURN"), cancellable = true)
    private static void animationStartTick(LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        if (entity == null || entity.level().isClientSide()
                || entity instanceof Mob mob && RigAnimationController.isDangerous(mob)) return;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch == null) return;
        var player = patch.getAnimator().getPlayerFor(null);
        if (player != null && !player.isEmpty() && !player.isEnd()) {
            cir.setReturnValue(ReactionAnimationTracker.startTick(entity, player.getRealAnimation()));
        }
    }

    @Inject(method = "isStunned", at = @At("RETURN"), cancellable = true)
    private static void isStunned(Mob mob, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || EpicfightUtil.isStunned(mob));
    }

    @Inject(method = "isBusy", at = @At("RETURN"), cancellable = true)
    private static void isBusy(Mob mob, CallbackInfoReturnable<Boolean> cir) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class);
        if (patch != null) cir.setReturnValue(cir.getReturnValue() || patch.getEntityState().inaction()
                || !patch.getEntityState().canBasicAttack()
                || patch instanceof AdvancedMobPatch<?> advanced && advanced.isCombatActionLocked());
    }

    @Inject(method = "isPlaying", at = @At("RETURN"), cancellable = true)
    private static void isPlaying(Mob mob, RigAnimationId id, CallbackInfoReturnable<Boolean> cir) {
        AssetAccessor<? extends StaticAnimation> animation = reactionAnimation(mob, id);
        if (animation != null && !cir.getReturnValue()) cir.setReturnValue(EpicfightUtil.isPlaying(mob, animation));
    }

    @Inject(method = "play", at = @At("HEAD"), cancellable = true)
    private static void play(Mob mob, RigAnimationId id, CallbackInfo ci) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class);
        AssetAccessor<? extends StaticAnimation> animation = reactionAnimation(mob, id);
        if (patch == null || animation == null) return;
        if (!mob.level().isClientSide() && mob.isAlive() && !EpicfightUtil.isStunned(mob)) {
            patch.playAnimationSynchronized(animation, 0.0F);
        }
        ci.cancel();
    }

    @Unique
    private static AssetAccessor<? extends StaticAnimation> reactionAnimation(Mob mob, RigAnimationId id) {
        return switch (id) {
            case ROLL_BACKWARD -> mob instanceof NullEntity ? WOMAnimations.SHADOWSTEP_BACKWARD : Animations.BIPED_ROLL_BACKWARD;
            case STEP_BACKWARD -> mob instanceof NullEntity ? WOMAnimations.SHADOWSTEP_BACKWARD : Animations.BIPED_STEP_BACKWARD;
            case JUMP -> Animations.BIPED_JUMP;
            case POINT_LEFT_HAND_TOWARD -> GoalAnimationCompat.animation(id);
            default -> null;
        };
    }
}
