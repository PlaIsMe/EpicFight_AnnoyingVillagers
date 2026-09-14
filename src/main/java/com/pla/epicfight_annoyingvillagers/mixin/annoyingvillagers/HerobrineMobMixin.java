package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.entity.AegisHerobrineEntity;
import com.pla.annoyingvillagers.entity.GlaiveHerobrineEntity;
import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.entity.ReaperHerobrineEntity;
import com.pla.annoyingvillagers.entity.SledgehammerHerobrineEntity;
import com.pla.annoyingvillagers.entity.SwordsmanHerobrineEntity;
import com.pla.annoyingvillagers.entity.TransporterHerobrineCloneEntity;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVSpear;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

@Mixin(value = HerobrineMob.class, remap = false)
public abstract class HerobrineMobMixin {
    @Inject(method = "playFallAnimation", at = @At("HEAD"), cancellable = true)
    private void playFallAnimation(CallbackInfo ci) {
        HerobrineMob self = (HerobrineMob) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            patch.applyStun(StunType.FALL, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "playInitAnimation", at = @At("HEAD"), cancellable = true)
    private void playInitAnimation(CallbackInfo ci) {
        HerobrineMob self = (HerobrineMob) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null && !self.level().isClientSide()) {
            if (self instanceof GlaiveHerobrineEntity) {
                patch.playAnimationSynchronized(AnimsAVSpear.AV_SPEAR_GUARD, 0.0F);
            } else if (self instanceof TransporterHerobrineCloneEntity) {
                patch.playAnimationSynchronized(AVAnimations.PORTAL_SUMMON, 0.0F);
            } else if (!(self instanceof SledgehammerHerobrineEntity)
                 && !(self instanceof SwordsmanHerobrineEntity)
                 && !(self instanceof AegisHerobrineEntity)
                 && !(self instanceof ReaperHerobrineEntity)
                 && !(self instanceof NullEntity)) {
                patch.playAnimationSynchronized(AVAnimations.HEROBRINE_ANIMATE, 0.0F);
            }
        }
        ci.cancel();
    }

    @Inject(method = "playStageChangeAnimation", at = @At("HEAD"), cancellable = true)
    private void playStageChangeAnimation(CallbackInfo ci) {
        HerobrineMob self = (HerobrineMob) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            patch.playAnimationSynchronized(AVAnimations.HEROBRINE_STAGE_CHANGE, 0.0F);
        }
        ci.cancel();
    }
}
