package com.pla.epicfight_annoyingvillagers.mixin.compat.smartnpc;

import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAvNpcPatch;
import com.pla.smart_npc.compat.epicfight.AdvancedPlayerNpcPatch;
import com.pla.smart_npc.compat.epicfight.WeaponCapabilityPresetTracking;
import com.pla.smart_npc.compat.epicfight.advancedmobpatch.AdvancedMobPatch.AdditionalAttackGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.List;

@Mixin(value = AdvancedPlayerNpcPatch.class, remap = false)
public abstract class AdvancedPlayerNpcPatchMixin {
    @Inject(method = "addMoreAttackGroups", at = @At("RETURN"), cancellable = true)
    private void addAvModAttackGroups(
            CapabilityItem mainHandCap,
            CapabilityItem offHandCap,
            Style style,
            CallbackInfoReturnable<List<AdditionalAttackGroup>> cir
    ) {
        List<AdditionalAttackGroup> originalGroups = cir.getReturnValue();
        cir.setReturnValue(AdvancedAvNpcPatch.addAvModAttackGroups(
                WeaponCapabilityPresetTracking.getPreset(mainHandCap),
                style,
                AdditionalAttackGroup::random,
                () -> originalGroups
        ));
    }
}
