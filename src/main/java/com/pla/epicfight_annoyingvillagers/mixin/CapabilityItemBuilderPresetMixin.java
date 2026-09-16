package com.pla.epicfight_annoyingvillagers.mixin;

import com.pla.epicfight_annoyingvillagers.capabilities.WeaponCapabilityPresetTracking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mixin(value = CapabilityItem.Builder.class, remap = false)
public abstract class CapabilityItemBuilderPresetMixin {
    @Inject(method = "build", at = @At("RETURN"), require = 1)
    private void annoyingvillagers$rememberPreset(CallbackInfoReturnable<CapabilityItem> cir) {
        WeaponCapabilityPresetTracking.recordBuiltCapability(
                (CapabilityItem.Builder) (Object) this, cir.getReturnValue());
    }
}
