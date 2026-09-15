package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.DragonBeamEntity;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = DragonBeamEntity.class, remap = false)
public abstract class DragonBeamEntityMixin {
    @Inject(method = "dealEpicFightStaminaDamage", at = @At("HEAD"), cancellable = true)
    private void dealEpicFightStaminaDamage(Entity target, CallbackInfo ci) {
        DragonBeamEntity self = (DragonBeamEntity) (Object) this;
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        EpicfightUtil.dealStaminaDamage(
                self.level().damageSources().indirectMagic(self, self.caster.getSummoner()),
                0.1F,
                livingEntityPatch
        );
        ci.cancel();
    }
}
