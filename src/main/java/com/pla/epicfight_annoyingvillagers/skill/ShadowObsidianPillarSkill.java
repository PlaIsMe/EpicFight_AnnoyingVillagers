package com.pla.epicfight_annoyingvillagers.skill;

import com.pla.epicfight_annoyingvillagers.gameasset.AnimsObsidianWeapon;
import net.minecraft.nbt.CompoundTag;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

import java.util.UUID;

public class ShadowObsidianPillarSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("b7ee1d55-ecdf-49e4-bcba-5f26cd097d35");

    public ShadowObsidianPillarSkill(WeaponInnateSkill.Builder<?> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, CompoundTag friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            skillContainer.getExecutor().playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_TWOHAND_1, 0.0F);
        }
    }

    @Override
    public void cancelOnServer(SkillContainer skillContainer, CompoundTag friendlyByteBuf) {
        skillContainer.deactivate();
        super.cancelOnServer(skillContainer, friendlyByteBuf);
    }

    public void executeOnClient(SkillContainer container, CompoundTag args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, CompoundTag args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }
}
