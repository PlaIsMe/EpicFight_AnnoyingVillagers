package com.pla.epicfight_annoyingvillagers.client.gui;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.init.EpicFightAnnoyingVillagersModMenus;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EpicFightAnnoyingVillagersClientScreens {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() ->
                MenuScreens.register(EpicFightAnnoyingVillagersModMenus.EMOTE.get(), EmoteScreen::new)
        );
    }
}
