package com.pla.epicfight_annoyingvillagers.event;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.skill.LegendarySwordSkill;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, value = Dist.CLIENT)
public final class ClientModelPredicateEvent {
    private ClientModelPredicateEvent() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(
                    AnnoyingVillagersModItems.LEGENDARY_SWORD.get(),
                    ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "awakened"),
                    (stack, level, entity, seed) -> LegendarySwordSkill.isAwakened(stack, level) ? 1.0F : 0.0F
            );
            ItemProperties.register(
                    AnnoyingVillagersModItems.ENDER_AEGIS.get(),
                    ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "second_form"),
                    (stack, level, entity, seed) -> EnderAegisItem.isSecondForm(stack) ? 1.0F : 0.0F
            );
            ItemProperties.register(
                    AnnoyingVillagersModItems.ENDER_AEGIS.get(),
                    ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "blocking"),
                    (stack, level, entity, seed) -> entity != null
                            && entity.isUsingItem()
                            && entity.getUseItem() == stack ? 1.0F : 0.0F
            );
        });
    }
}
