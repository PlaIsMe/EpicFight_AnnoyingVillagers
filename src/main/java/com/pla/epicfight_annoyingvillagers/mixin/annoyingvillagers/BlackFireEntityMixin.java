package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.BlackFireEntity;
import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;

@Mixin(value = BlackFireEntity.class, remap = false)
public abstract class BlackFireEntityMixin {
    @Inject(method = "getOwnerSwordPosition", at = @At("HEAD"), cancellable = true)
    private static void getOwnerSwordPosition(LivingEntity owner, CallbackInfoReturnable<Vec3> cir) {
        try {
            Vec3 pos = EpicfightUtil.getJointWithTranslation(
                    owner,
                    new Vec3f(0.0F, 0.0F, 0.0F),
                    Armatures.BIPED.get().toolR,
                    (float) 1.0,
                    0.0F
            );

            if (pos != null) {
                cir.setReturnValue(pos);
                return;
            }
        } catch (Exception ignored) {
        }
        cir.setReturnValue(CommonUtil.getVanillaSwordOrBodyPosition(owner));
        return;
    }
}
