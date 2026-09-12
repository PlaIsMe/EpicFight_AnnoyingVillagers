package com.pla.epicfight_annoyingvillagers.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class EpicFightAnnoyingVillagersConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<Double> MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE;
    public static ForgeConfigSpec.ConfigValue<Double> MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE;
    public static ForgeConfigSpec.IntValue WEAPON_BREAKING_MECHANISM_VALUE;

    static {
        WEAPON_BREAKING_MECHANISM_VALUE = BUILDER.comment("Weapon durability consumed when a dangerous player attack is parried; zero disables durability loss.")
                .defineInRange("weaponBreakingMechanismValue", 100, 0, 10000);
        MOB_GUARD_BREAK_WAKE_UP_MIN_CHANCE = BUILDER.comment(
                        "[ONLY WORK WHEN EpicFight: KickSkill is installed] Min chance for mob can wake up automatically on guard break")
                .defineInRange("mobGuardBreakWakeUpMinChance", 0.05D, 0.0D, 1.0D);

        MOB_GUARD_BREAK_WAKE_UP_MAX_CHANCE = BUILDER.comment(
                        "[ONLY WORK WHEN EpicFight: KickSkill is installed] Max chance for mob can wake up automatically on guard break")
                .defineInRange("mobGuardBreakWakeUpMaxChance", 0.4D, 0.0D, 1.0D);
        SPEC = BUILDER.build();
    }
}
