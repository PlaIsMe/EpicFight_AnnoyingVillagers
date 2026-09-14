package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.BlueDemonThunderBeamEntity;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

@Mixin(value = BlueDemonThunderBeamEntity.class, remap = false)
public abstract class BlueDemonThunderBeamEntityMixin {
    @Inject(method = "resolveBeamHandPositions", at = @At("HEAD"), cancellable = true)
    private void resolveBeamHandPositions(LivingEntity caster, CallbackInfoReturnable<Vec3[]> cir) {
        Vec3 handLeft = EpicfightUtil.getJointWithTranslation(
                caster, new Vec3f(0,0,0), Armatures.BIPED.get().handL, 0.0F, 0.0F
        );
        Vec3 handRight = EpicfightUtil.getJointWithTranslation(
                caster, new Vec3f(0,0,0), Armatures.BIPED.get().handR, 0.0F, 0.0F
        );
        cir.setReturnValue(new Vec3[]{handLeft, handRight});
        return;
    }
}
