package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.entity.goal.ThrowEnderPearlToTargetGoal;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = ThrowEnderPearlToTargetGoal.class, remap = false)
public abstract class ThrowEnderPearlToTargetGoalMixin {
    @Shadow @Final private AVNpc avNpc;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void preventPearlWhileLongHit(CallbackInfoReturnable<Boolean> cir) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(this.avNpc, LivingEntityPatch.class);
        if (patch == null || patch.getAnimator().getPlayerFor(null) == null) {
            return;
        }

        if (EpicfightUtil.isLongHitAnimation(
                patch.getAnimator().getPlayerFor(null).getRealAnimation(), patch)) {
            cir.setReturnValue(false);
        }
    }
}
