package com.pla.epicfight_annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.skill.LegendarySwordSkill;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModelPredicateEvent {
    private ClientModelPredicateEvent() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(
                AnnoyingVillagersModItems.LEGENDARY_SWORD.get(),
                ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "awakened"),
                (stack, level, entity, seed) -> LegendarySwordSkill.isAwakened(stack, level) ? 1.0F : 0.0F
        ));
    }
}
