package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.goal.HerobrinePortalDangerousReactionGoal;
import com.pla.epicfight_annoyingvillagers.util.DangerousReactionUtil;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = HerobrinePortalDangerousReactionGoal.class, remap = false)
public abstract class HerobrinePortalDangerousReactionGoalMixin {
    @Inject(method = "canReact", at = @At("HEAD"), cancellable = true)
    private void canReact(CallbackInfoReturnable<Boolean> cir) {
        HerobrinePortalDangerousReactionGoal self = (HerobrinePortalDangerousReactionGoal) (Object) this;
        if (EpicFightCapabilities.getEntityPatch(self.getMob(), LivingEntityPatch.class) != null) {
            cir.setReturnValue(DangerousReactionUtil.canReact(self.getMob()));
        }
    }

    @Inject(method = "getDangerousAnimationStartTick", at = @At("HEAD"), cancellable = true)
    private void getDangerousAnimationStartTick(Mob target, CallbackInfoReturnable<Integer> cir) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (patch == null || !DangerousReactionUtil.isCurrentAnimationDangerous(patch)) return;
        var player = patch.getAnimator().getPlayerFor(null);
        cir.setReturnValue(target.tickCount - Math.round(player.getElapsedTime() * 20.0F));
    }

    @Inject(method = "getDangerousAnimationKey", at = @At("HEAD"), cancellable = true)
    private void getDangerousAnimationKey(Mob target, CallbackInfoReturnable<Object> cir) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (patch == null || !DangerousReactionUtil.isCurrentAnimationDangerous(patch)) return;
        cir.setReturnValue(patch.getAnimator().getPlayerFor(null).getRealAnimation());
    }
}
