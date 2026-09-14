package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers.client;

import com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo.DiamondAttractorParticleEmitterInfo;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

@Mixin(value = DiamondAttractorParticleEmitterInfo.class, remap = false)
public abstract class DiamondAttractorParticleEmitterInfoMixin {
    @Inject(method = "getSwordPosition", at = @At("HEAD"), cancellable = true)
    private void getSwordPosition(Entity entity, float partialTick, CallbackInfoReturnable<Vec3> cir) {
        DiamondAttractorParticleEmitterInfo self = (DiamondAttractorParticleEmitterInfo) (Object) this;
        try {
            cir.setReturnValue(EpicfightUtil.getJointWithTranslation(
                    entity,
                    new Vec3f((float) self.swordLocalOffset.x, (float) self.swordLocalOffset.y, (float) self.swordLocalOffset.z),
                    Armatures.BIPED.get().toolR,
                    partialTick,
                    0.0F
            ));
            return;
        } catch (Exception ignored) {
            cir.setReturnValue(null);
            return;
        }
    }
}
