package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers.client;

import com.pla.annoyingvillagers.compat.aaa_particles.emitterinfo.BlackFireParticleEmitterInfo;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

@Mixin(value = BlackFireParticleEmitterInfo.class, remap = false)
public abstract class BlackFireParticleEmitterInfoMixin {
    @Inject(method = "getSwordPosition", at = @At("HEAD"), cancellable = true)
    private static void getSwordPosition(Entity entity, float partialTick, CallbackInfoReturnable<Vec3> cir) {
        try {
            cir.setReturnValue(EpicfightUtil.getJointWithTranslation(
                    entity,
                    new Vec3f(0.0F, 0.0F, 0.0F),
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
