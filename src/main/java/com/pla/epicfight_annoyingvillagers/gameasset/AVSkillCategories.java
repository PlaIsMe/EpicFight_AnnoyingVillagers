package com.pla.epicfight_annoyingvillagers.gameasset;

import yesman.epicfight.skill.SkillCategory;

public enum AVSkillCategories implements SkillCategory {

    KICK(true, true, true),
    STUN_ESCAPE(true, true, true);

    final boolean shouldSave;
    final boolean shouldSyncronize;
    final boolean modifiable;
    final int id;

    private AVSkillCategories(boolean flag, boolean flag1, boolean flag2) {
        this.shouldSave = flag;
        this.shouldSyncronize = flag1;
        this.modifiable = flag2;
        this.id = SkillCategory.ENUM_MANAGER.assign(this);
    }

    public boolean shouldSave() {
        return this.shouldSave;
    }

    public boolean shouldSynchronize() {
        return this.shouldSyncronize;
    }

    public boolean learnable() {
        return this.modifiable;
    }

    public int universalOrdinal() {
        return this.id;
    }
}
