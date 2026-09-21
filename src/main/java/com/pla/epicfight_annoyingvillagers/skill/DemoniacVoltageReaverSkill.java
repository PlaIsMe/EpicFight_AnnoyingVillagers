package com.pla.epicfight_annoyingvillagers.skill;

import com.pla.epicfight_annoyingvillagers.gameasset.AnimsDemoniacVoltageReaver;
import com.pla.epicfight_annoyingvillagers.util.ItemStackData;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.event.EntityEventListener;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Objects;
import java.util.UUID;

public class DemoniacVoltageReaverSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("a86b0713-5f98-4e04-9930-fee81f157780");

    public DemoniacVoltageReaverSkill(WeaponInnateSkill.Builder<?> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, CompoundTag friendlyByteBuf) {
        Player player = skillContainer.getExecutor().getOriginal();
        if (DemoniacVoltageReaverItem.checkNearbyTarget(player)) {
            skillContainer.getExecutor().playAnimationSynchronized(AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE, 0.0F);
            super.executeOnServer(skillContainer, friendlyByteBuf);
        }
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        Player player = container.getExecutor().getOriginal();
        ItemStack stack = player.getMainHandItem();

        boolean isCorrectItem = stack.getItem() instanceof DemoniacVoltageReaverItem;
        boolean isSnaking = ItemStackData.getBoolean(stack, "SnakeAnimation");
        boolean isActivated = container.isActivated();

        return isCorrectItem && !isSnaking && !isActivated && super.canExecute(container);
    }

    @Override
    public void onInitiate(SkillContainer container, EntityEventListener eventListener) {
        super.onInitiate(container, eventListener);
        eventListener.registerEvent(
                EpicFightEventHooks.Player.CAST_SKILL, (event) -> {
            Player player = container.getExecutor().getOriginal();
            ItemStack item = player.getMainHandItem();
            Skill skill = event.getSkillContainer().getSkill();
            if ((skill.getCategory() == SkillCategories.BASIC_ATTACK) &&
                    (item.getItem() instanceof DemoniacVoltageReaverItem
                            && ItemStackData.getBoolean(item, "SnakeAnimation"))) {
                event.cancel();
            }
        }, this);
        eventListener.registerEvent(EpicFightEventHooks.Entity.TAKE_DAMAGE_INCOME, (pre) -> {
            if (pre.getEntityPatch().isLogicalClient()) return;

            final PlayerPatch<?> playerPatch = (PlayerPatch<?>) pre.getEntityPatch();
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (dynamicAnimation == null) return;

            if (dynamicAnimation == AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE_SPECIAL) {
                pre.cancel();
                pre.setResult(AttackResult.ResultType.BLOCKED);
            }
        }, this);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListenersBelongTo(this);
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
                itemStack.getItem() instanceof DemoniacVoltageReaverItem
                && !ItemStackData.getBoolean(itemStack, "SnakeAnimation")
                && !ItemStackData.getBoolean(itemStack, "PlaySound")) {
            container.getExecutor().getOriginal().playSound(AnnoyingVillagersModSounds.ELITE_HEROBRINE_WEAPON_SCREAMING.get(), 0.5F, 1.0F);
            ItemStackData.putBoolean(itemStack, "PlaySound", true);
        } else if (container.getStack() < 1 &&
                itemStack.getItem() instanceof DemoniacVoltageReaverItem && ItemStackData.getBoolean(itemStack, "PlaySound")) {
            ItemStackData.remove(itemStack, "PlaySound");
        }
    }
}
