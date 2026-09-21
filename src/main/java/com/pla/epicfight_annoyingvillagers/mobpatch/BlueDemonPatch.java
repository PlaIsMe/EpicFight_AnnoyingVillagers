package com.pla.epicfight_annoyingvillagers.mobpatch;

import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.epicfight_annoyingvillagers.capabilities.AVWeaponCapabilityPresets;
import com.pla.epicfight_annoyingvillagers.capabilities.WeaponCapabilityPresetTracking;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsBlueDemonTrident;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.List;

public class BlueDemonPatch extends FullDodgeAvNpcPatch<BlueDemonEntity> {
    public BlueDemonPatch(BlueDemonEntity original) {
        super(original);
    }

    @Override
    protected List<AdditionalAttackGroup> getAdditionalAttackGroups(CapabilityItem mainHandCap, CapabilityItem offHandCap, Style style) {
        var preset = WeaponCapabilityPresetTracking.getPreset(mainHandCap);
        if (preset == AVWeaponCapabilityPresets.LEGENDARY_SWORD && style == CapabilityItem.Styles.OCHS) {
            return List.of(
                    AdditionalAttackGroup.random(0.15F, AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_SPECIAL_LEGENDARY),
                    AdditionalAttackGroup.random(0.25F, AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_ELECTRIC_FIELD)
            );
        }
        return super.getAdditionalAttackGroups(mainHandCap, offHandCap, style);
    }
}
