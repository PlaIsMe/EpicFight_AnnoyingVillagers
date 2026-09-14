package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.EliteHerobrineKnockedEntity;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = EliteHerobrineKnockedEntity.class, remap = false)
public abstract class EliteHerobrineKnockedEntityMixin {
    @Inject(method = "playBeingEatenAnimation", at = @At("HEAD"), cancellable = true)
    private void playBeingEatenAnimation(CallbackInfo ci) {
        EliteHerobrineKnockedEntity self = (EliteHerobrineKnockedEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            if (self.eatCount == 1 || self.eatCount == 2) {
                patch.playAnimationSynchronized(AVAnimations.EATING_ELITE_1, 0.0F);
            } else if (self.eatCount == 3 || self.eatCount == 4) {
                patch.playAnimationSynchronized(AVAnimations.EATING_ELITE_2, 0.0F);
            } else if (self.eatCount == 5 || self.eatCount == 6) {
                patch.playAnimationSynchronized(AVAnimations.EATING_ELITE_3, 0.0F);
            } else if (self.eatCount > 6) {
                patch.playAnimationSynchronized(AVAnimations.EATING_ELITE_4, 0.0F);
            }
        }
        ci.cancel();
    }
}
