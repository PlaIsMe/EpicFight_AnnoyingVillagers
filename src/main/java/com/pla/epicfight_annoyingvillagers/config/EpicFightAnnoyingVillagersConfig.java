package com.pla.epicfight_annoyingvillagers.config;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EpicFightAnnoyingVillagersConfig {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static ModConfigSpec.ConfigValue<Double> MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE;
    public static ModConfigSpec.ConfigValue<Double> MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE;
    public static ModConfigSpec.IntValue WEAPON_BREAKING_MECHANISM_VALUE;

    public static ModConfigSpec.ConfigValue<List<? extends String>> WEAPON_CAPABILITY_REDIRECTS;
    public static ModConfigSpec.ConfigValue<Double> KICK_STAMINA_DECREASE_PERCENTAGE;

    private static volatile List<? extends String> cachedRawEntries = List.of();
    private static volatile Map<ResourceLocation, ResourceLocation> cachedRedirects = Map.of();

    static {
        WEAPON_BREAKING_MECHANISM_VALUE = BUILDER.comment("Weapon durability consumed when a dangerous player attack is parried; zero disables durability loss.")
                .defineInRange("weaponBreakingMechanismValue", 100, 0, 10000);
        MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE = BUILDER.comment(
                        "[ONLY WORK WHEN EpicFight: KickSkill is installed] Min chance for mob can wake up automatically on guard break")
                .defineInRange("mobGuardBreakWakeUpMinChance", 0.05D, 0.0D, 1.0D);

        MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE = BUILDER.comment(
                        "[ONLY WORK WHEN EpicFight: KickSkill is installed] Max chance for mob can wake up automatically on guard break")
                .defineInRange("mobGuardBreakWakeUpMaxChance", 0.4D, 0.0D, 1.0D);
        WEAPON_CAPABILITY_REDIRECTS = BUILDER.comment(
                        "Redirect an item's Epic Fight weapon capability preset only for AV NPC combat.",
                        "Format: source_item_id;target_weapon_capability_preset_id",
                        "The held item is not replaced. Only the Epic Fight capability/moveset used by the NPC is redirected.",
                        "Example: wom:agony;epicfight:spear")
                .defineList("weaponCapabilityRedirects", List.of(
                        "wom:agony;epicfight:spear",
                        "wom:ender_slayer_scythe;epicfight:spear",
                        "wom:enderblaster;epicfight:fist"
                ), value -> value instanceof String);
        KICK_STAMINA_DECREASE_PERCENTAGE = BUILDER.comment(
                        "Mob's stamina will be decreased by this percentage when get hit by Kick")
                .defineInRange("kickStaminaDecreasePercentage", 0.3D, 0.0D, 1.0D);
        SPEC = BUILDER.build();
    }

    public static ResourceLocation getWeaponCapabilityRedirect(ResourceLocation sourceItemId) {
        if (sourceItemId == null) {
            return null;
        }
        refreshCacheIfNeeded();
        return cachedRedirects.get(sourceItemId);
    }

    private static void refreshCacheIfNeeded() {
        List<? extends String> rawEntries = WEAPON_CAPABILITY_REDIRECTS.get();
        if (rawEntries == cachedRawEntries || rawEntries.equals(cachedRawEntries)) {
            return;
        }

        synchronized (EpicFightAnnoyingVillagersConfig.class) {
            rawEntries = WEAPON_CAPABILITY_REDIRECTS.get();
            if (rawEntries == cachedRawEntries || rawEntries.equals(cachedRawEntries)) {
                return;
            }

            Map<ResourceLocation, ResourceLocation> parsed = new LinkedHashMap<>();
            for (String rawEntry : rawEntries) {
                if (rawEntry == null) {
                    continue;
                }

                // Accept both normal ResourceLocation text and the escaped-colon form
                // people often paste from config/documentation examples (wom\:agony).
                String entry = rawEntry.trim().replace("\\:", ":");
                int separator = entry.indexOf(';');
                if (separator <= 0 || separator != entry.lastIndexOf(';') || separator >= entry.length() - 1) {
                    EpicFightAnnoyingVillagers.LOGGER.warn("Ignoring invalid AV NPC Epic Fight weapon capability redirect '{}'. Expected source_item;target_preset.", rawEntry);
                    continue;
                }

                ResourceLocation source = ResourceLocation.tryParse(entry.substring(0, separator).trim());
                ResourceLocation target = ResourceLocation.tryParse(entry.substring(separator + 1).trim());
                if (source == null || target == null) {
                    EpicFightAnnoyingVillagers.LOGGER.warn("Ignoring invalid AV NPC Epic Fight weapon capability redirect '{}'.", rawEntry);
                    continue;
                }
                parsed.put(source, target);
            }

            cachedRawEntries = List.copyOf(rawEntries);
            cachedRedirects = Map.copyOf(parsed);
        }
    }
}
