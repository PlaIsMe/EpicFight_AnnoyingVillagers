package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.goal.NullSummonSkeletonGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** The advanced Null patch owns this action while Epic Fight compatibility is loaded. */
@Mixin(value = NullSummonSkeletonGoal.class, remap = false)
public abstract class NullSummonSkeletonGoalMixin {
    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void useAdvancedCombatBehavior(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
