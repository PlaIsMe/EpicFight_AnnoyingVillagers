package com.pla.epicfight_annoyingvillagers.skill;

import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVSword;
import net.minecraft.nbt.CompoundTag;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.event.EntityEventListener;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;

import java.util.Objects;
import java.util.UUID;

public class WoopieTheSwordSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("5a6ceb12-eacb-49c6-8030-37942b192e1d");
    public WoopieTheSwordSkill(WeaponInnateSkill.Builder<?> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, CompoundTag friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            skillContainer.getExecutor().playAnimationSynchronized(AnimsAVSword.WOOPIE_INNATE, 0.0F);
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
    public void onInitiate(SkillContainer container, EntityEventListener eventListener) {
        super.onInitiate(container, eventListener);
        eventListener.registerEvent(
                EpicFightEventHooks.Entity.DELIVER_DAMAGE_PRE,
                (DealDamageEvent.Pre event) -> {
                    if (event.getEntityPatch().isLogicalClient()) return;

                    final PlayerPatch<?> playerPatch = (PlayerPatch<?>) event.getEntityPatch();
                    AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                    if (dynamicAnimation == null) return;

                    if (dynamicAnimation == AnimsAVSword.WOOPIE_INNATE_SPECIAL && container.getStack() < 1) {
                        WoopieTheSwordSkill woopieTheSwordSkill = (WoopieTheSwordSkill) container.getSkill();
                        float currentResource = container.getResource();
                        float neededResource = container.getNeededResource();
                        woopieTheSwordSkill.setConsumptionSynchronize(container, currentResource + neededResource);
                    }
                }, this,
                10
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListenersBelongTo(this);
        super.onRemoved(container);
    }
}
