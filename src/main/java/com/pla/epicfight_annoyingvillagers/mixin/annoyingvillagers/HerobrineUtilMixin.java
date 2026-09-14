package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.Vec3f;

@Mixin(value = HerobrineUtil.class, remap = false)
public abstract class HerobrineUtilMixin {
    @Inject(method = "getJointOrVanillaActionPosition", at = @At("HEAD"), cancellable = true)
    private static void getJointOrVanillaActionPosition(Entity entity, Object joint, float partialTick, CallbackInfoReturnable<Vec3> cir) {
        if (joint != null) {
            try {
                Vec3 jointVec = EpicfightUtil.getJointWithTranslation(
                        entity, new Vec3f(0, 0, 0),
                        (Joint) joint, partialTick, 0.0F
                );
                if (jointVec != null) {
                    cir.setReturnValue(jointVec);
                    return;
                }
            } catch (Exception ignored) {
            }
        }
        cir.setReturnValue(CommonUtil.getVanillaSwordOrBodyPosition(entity, entity.level().isClientSide ? partialTick : 1.0F));
        return;
    }
}
