package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.ElectricPhaseEntity;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

@Mixin(value = ElectricPhaseEntity.class, remap = false)
public abstract class ElectricPhaseEntityMixin {
    @Inject(method = "getOwnerSwordPosition", at = @At("HEAD"), cancellable = true)
    private static void getOwnerSwordPosition(LivingEntity owner, boolean offhand, CallbackInfoReturnable<Vec3> cir) {
        try {
            Vec3 pos = EpicfightUtil.getJointWithTranslation(
                    owner,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    offhand ? Armatures.BIPED.get().toolL : Armatures.BIPED.get().toolR,
                    1.0F,
                    0.25F
            );

            if (pos != null) {
                cir.setReturnValue(pos);
                return;
            }
        } catch (Exception ignored) {
        }

        Vec3 look = owner.getLookAngle();
        Vec3 side = new Vec3(-look.z, 0.0D, look.x);

        if (side.lengthSqr() > 1.0E-7D) {
            side = side.normalize();
        } else {
            side = Vec3.ZERO;
        }

        double sideOffset = offhand ? -0.35D : 0.35D;

        cir.setReturnValue(owner.position()
                .add(0.0D, owner.getBbHeight() * 0.65D, 0.0D)
                .add(look.scale(0.75D))
                .add(side.scale(sideOffset)));

        return;
    }
}
