package com.pla.epicfight_annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraft.resources.ResourceLocation;
import yesman.epicfight.api.client.event.types.registry.RegisterPatchedRenderersEvent;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;

import java.util.function.Function;

public class RenderEngine {
    private static void register(RegisterPatchedRenderersEvent.Item event, String name,
                                 Function<JsonElement, RenderItemBase> factory) {
        event.addItemRenderer(ResourceLocation.fromNamespaceAndPath(EpicFightAnnoyingVillagers.MODID, name), factory);
        // Core item skins and existing resource packs still use the original renderer IDs.
        event.addItemRenderer(ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, name), factory);
    }

    public static void registerRenderer(RegisterPatchedRenderersEvent.Item add) {
        register(add, "legendary_sword", RenderLegendarySword::new);
        register(add, "great_sword", RenderGreatSword::new);
        register(add, "obsidian_weapon", RenderObsidianWeapon::new);
        register(add, "shadow_obsidian_weapon", RenderShadowObsidianWeapon::new);
        register(add, "shadow_obsidian_pillar", RenderShadowObsidianPillar::new);
        register(add, "shadow_obsidian_sword", RenderShadowObsidianSword::new);
        register(add, "diamond_blaster_sword", RenderDiamondBlasterSword::new);
        register(add, "diamond_bolt", RenderDiamondBolt::new);
        register(add, "dnax_hooked_sword", RenderDNAxHookSword::new);
        register(add, "red_axe", RenderRedAxe::new);
        register(add, "blackscratcher", RenderBlackscratcher::new);
        register(add, "twin_diamond_spear", RenderItemBase::new);
    }
}
