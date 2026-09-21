package com.pla.epicfight_annoyingvillagers.skill;

import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVSword;
import net.minecraft.nbt.CompoundTag;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

import java.util.Random;

public class HookSwordSkill extends WeaponInnateSkill {
    public HookSwordSkill(WeaponInnateSkill.Builder<?> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, CompoundTag friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            if (new Random().nextBoolean()) {
                skillContainer.getExecutor().playAnimationSynchronized(AnimsAVSword.HOOK_SWORD_INNATE1, 0.0F);
            } else {
                skillContainer.getExecutor().playAnimationSynchronized(AnimsAVSword.HOOK_SWORD_INNATE2, 0.0F);
            }
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

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
    }
}
