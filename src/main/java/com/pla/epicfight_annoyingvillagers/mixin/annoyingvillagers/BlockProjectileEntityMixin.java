package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.BlockProjectileEntity;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

@Mixin(value = BlockProjectileEntity.class, remap = false)
public abstract class BlockProjectileEntityMixin {
    @Inject(method = "applyLongStun", at = @At("HEAD"), cancellable = true)
    private void applyLongStun(Entity entity, CallbackInfo ci) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            livingEntityPatch.applyStun(StunType.LONG, 20.0F);
        }
        ci.cancel();
    }
}
