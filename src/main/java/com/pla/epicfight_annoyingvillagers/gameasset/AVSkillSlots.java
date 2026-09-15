package com.pla.epicfight_annoyingvillagers.gameasset;

import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

public enum AVSkillSlots implements SkillSlot {

    KICK(AVSkillCategories.KICK),
    STUN_ESCAPE(AVSkillCategories.STUN_ESCAPE);

    final AVSkillCategories category;
    final int id;

    AVSkillSlots(AVSkillCategories category) {
        this.category = category;
        this.id = SkillSlot.ENUM_MANAGER.assign(this);
    }

    public SkillCategory category() {
        return this.category;
    }

    public int universalOrdinal() {
        return this.id;
    }
}
