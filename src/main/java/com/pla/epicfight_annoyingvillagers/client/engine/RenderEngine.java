package com.pla.epicfight_annoyingvillagers.client.engine;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;

@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class RenderEngine {
    @SubscribeEvent
    public static void registerRenderer(PatchedRenderersEvent.RegisterItemRenderer add) {
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "legendary_sword"), RenderLegendarySword::new);
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "great_sword"), RenderGreatSword::new);
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "obsidian_weapon"), RenderObsidianWeapon::new);
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "shadow_obsidian_weapon"), RenderShadowObsidianWeapon::new);
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "shadow_obsidian_pillar"), RenderShadowObsidianPillar::new);
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "shadow_obsidian_sword"), RenderShadowObsidianSword::new);
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "diamond_blaster_sword"), RenderDiamondBlasterSword::new);
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "diamond_bolt"), RenderDiamondBolt::new);
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "dnax_hooked_sword"), RenderDNAxHookSword::new);
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "red_axe"), RenderRedAxe::new);
        add.addItemRenderer(ResourceLocation.tryBuild(EpicFightAnnoyingVillagers.MODID, "blackscratcher"), RenderBlackscratcher::new);
    }
}
