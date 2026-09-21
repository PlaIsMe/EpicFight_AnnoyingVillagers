package com.pla.epicfight_annoyingvillagers.skill;

import com.pla.epicfight_annoyingvillagers.gameasset.AnimsObsidianSledgehammer;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.ObsidianSledgehammerItem;
import com.pla.epicfight_annoyingvillagers.util.ItemStackData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

public class ObsidianSledgeHammerSkill extends WeaponInnateSkill {
    public ObsidianSledgeHammerSkill(WeaponInnateSkill.Builder<?> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, CompoundTag friendlyByteBuf) {
        if (!skillContainer.isActivated()) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            skillContainer.getExecutor().playAnimationSynchronized(AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_INNATE, 0.0F);
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
        Player player = container.getExecutor().getOriginal();
        ItemStack itemStack = player.getMainHandItem();
        if (container.getStack() == 1 &&
                itemStack.getItem() instanceof ObsidianSledgehammerItem && !ItemStackData.getBoolean(itemStack, "PlaySound")) {
            container.getExecutor().getOriginal().playSound(AnnoyingVillagersModSounds.ELITE_HEROBRINE_WEAPON_SCREAMING.get(), 0.5F, 1.0F);
            ItemStackData.putBoolean(itemStack, "PlaySound", true);
        } else if (container.getStack() < 1 &&
                itemStack.getItem() instanceof ObsidianSledgehammerItem && ItemStackData.getBoolean(itemStack, "PlaySound")) {
            ItemStackData.remove(itemStack, "PlaySound");
        }
    }
}
