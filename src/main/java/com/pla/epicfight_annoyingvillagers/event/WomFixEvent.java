package com.pla.epicfight_annoyingvillagers.event;

import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import reascer.wom.gameasset.WOMSkills;
import reascer.wom.skill.weaponinnate.DemonicAscensionSkill;
import reascer.wom.world.item.WOMItems;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@EventBusSubscriber
public class WomFixEvent {
    @SubscribeEvent
    public static void fixAntitheusCrash(PlayerTickEvent.Post playerTickEvent) {
        if (playerTickEvent.getEntity().level().isClientSide()) return;

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(playerTickEvent.getEntity(), PlayerPatch.class);
        if (!(playerPatch instanceof ServerPlayerPatch serverPlayerPatch)) return;

        SkillContainer skillContainer = serverPlayerPatch.getSkill(WOMSkills.DEMONIC_ASCENSION.get());
        if (skillContainer == null || !(skillContainer.getSkill() instanceof DemonicAscensionSkill demonicAscensionSkill)) return;

        if (!skillContainer.isActivated()) return;

        boolean holdingAntitheus = playerTickEvent.getEntity().getMainHandItem().is(WOMItems.ANTITHEUS.get());
        if (holdingAntitheus) return;
        demonicAscensionSkill.cancelOnServer(skillContainer, null);
        skillContainer.deactivate();
        if (serverPlayerPatch.getAnimator() != null) {
            serverPlayerPatch.getAnimator().stopPlaying(null);
        }
    }
}
