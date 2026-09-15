package com.pla.epicfight_annoyingvillagers.mixin;

import com.pla.epicfight_annoyingvillagers.util.ReactionAnimationTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = StaticAnimation.class, remap = false)
public abstract class ReactionAnimationStartMixin {
    @Inject(method = "begin", at = @At("HEAD"))
    private void recordCastStart(LivingEntityPatch<?> patch, CallbackInfo ci) {
        if (patch.isLogicalClient()) return;
        StaticAnimation self = (StaticAnimation) (Object) this;
        ReactionAnimationTracker.began(patch.getOriginal(), self.getAccessor());
    }
}
