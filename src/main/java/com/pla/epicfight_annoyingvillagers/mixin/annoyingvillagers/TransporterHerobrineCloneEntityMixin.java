package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.TransporterHerobrineCloneEntity;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = TransporterHerobrineCloneEntity.class, remap = false)
public abstract class TransporterHerobrineCloneEntityMixin {
    @Inject(method = "playPortalSupportAnimation", at = @At("HEAD"), cancellable = true)
    private void playPortalSupportAnimation(RigAnimationId animationId, LivingEntity lookTarget, CallbackInfo ci) {
        TransporterHerobrineCloneEntity self = (TransporterHerobrineCloneEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (lookTarget != null && lookTarget.isAlive()) {
            self.getLookControl().setLookAt(lookTarget, 30.0F, 30.0F);
        }
        if (patch != null && !self.level().isClientSide()) {
            if (animationId == RigAnimationId.PORTAL_SUMMON) {
                patch.playAnimationSynchronized(AVAnimations.PORTAL_SUMMON, 0.0F);
            } else if (animationId == RigAnimationId.POINT_LEFT_HAND_TOWARD) {
                patch.playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F);
            } else if (animationId == RigAnimationId.POINT_LEFT_HAND_MIDDLE) {
                patch.playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_MIDDLE, 0.0F);
            }
        }
        ci.cancel();
    }
}
