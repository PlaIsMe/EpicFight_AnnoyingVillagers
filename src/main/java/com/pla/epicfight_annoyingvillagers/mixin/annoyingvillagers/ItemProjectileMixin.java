package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.ItemProjectile;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

@Mixin(value = ItemProjectile.class, remap = false)
public abstract class ItemProjectileMixin {
    @Inject(method = "applyLongStun", at = @At("HEAD"), cancellable = true)
    private void applyLongStun(Entity target, CallbackInfo ci) {
        LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (targetPatch != null && !targetPatch.isStunned()) {
            targetPatch.applyStun(StunType.LONG, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "getTargetHandPosition", at = @At("HEAD"), cancellable = true)
    private void getTargetHandPosition(LivingEntity owner, CallbackInfoReturnable<Vec3> cir) {
        Vec3 jointPos = null;

        try {
            jointPos = EpicfightUtil.getJointWithTranslation(
                    owner,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    Armatures.BIPED.get().toolR,
                    0.0F,
                    0.0D
            );
        } catch (Exception ignored) {
        }

        if (jointPos != null) {
            cir.setReturnValue(jointPos);
            return;
        }
        cir.setReturnValue(owner.getEyePosition()
                .add(owner.getLookAngle().scale(0.45D))
                .subtract(0.0D, 0.25D, 0.0D));
        return;
    }
}
