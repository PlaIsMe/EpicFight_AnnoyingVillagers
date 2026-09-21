package com.pla.epicfight_annoyingvillagers.client;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.client.engine.RenderEngine;
import com.pla.epicfight_annoyingvillagers.event.ThrowableSpearAimEvent;
import com.pla.epicfight_annoyingvillagers.init.EpicFightAnnoyingVillagersModPatchedEntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.api.client.event.EpicFightClientEventHooks;

@EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class EpicFightClientRegistration {
    private EpicFightClientRegistration() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ThrowableSpearAimEvent.register();
        EpicFightClientEventHooks.Registry.PATCHED_ITEM.registerEvent(
                RenderEngine::registerRenderer, EpicFightAnnoyingVillagers.MODID);
        EpicFightClientEventHooks.Registry.ADD_PATCHED_ENTITY.registerEvent(
                EpicFightAnnoyingVillagersModPatchedEntityRenderers::onPatchedRenderer,
                EpicFightAnnoyingVillagers.MODID);
    }
}
