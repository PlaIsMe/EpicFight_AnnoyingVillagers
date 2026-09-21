package com.pla.epicfight_annoyingvillagers.gameasset;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.skill.EnderSlayerScytheSkill;
import com.pla.epicfight_annoyingvillagers.skill.LegendarySwordSkill;
import com.pla.epicfight_annoyingvillagers.skill.NullWeaponSkill;
import com.pla.epicfight_annoyingvillagers.skill.TridentFestivalSkill;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import yesman.epicfight.registry.EpicFightRegistries;
import yesman.epicfight.skill.SkillDataKey;

public class AVSkillDataKeys {
    public static final DeferredRegister<SkillDataKey<?>> DATA_KEYS = DeferredRegister.create(EpicFightRegistries.Keys.SKILL_DATA_KEY, EpicFightAnnoyingVillagers.MODID);
    public static final DeferredHolder<SkillDataKey<?>, SkillDataKey<Boolean>> IS_TRIDENT_RANGED_MODE;
    public static final DeferredHolder<SkillDataKey<?>, SkillDataKey<Integer>> TRIDENT_AMOUNT;
    public static final DeferredHolder<SkillDataKey<?>, SkillDataKey<Boolean>> LEGENDARY_SWORD_AWAKENED;
    public static final DeferredHolder<SkillDataKey<?>, SkillDataKey<Boolean>> ENDER_SLAYER_SCYTHE_SUMMON_PENDING;
    public static final DeferredHolder<SkillDataKey<?>, SkillDataKey<Integer>> NULL_WEAPON_RELEASE_STACKS;

    public AVSkillDataKeys() {
    }

    static {
        IS_TRIDENT_RANGED_MODE = DATA_KEYS.register("is_trident_ranged_mode", () -> SkillDataKey.createSkillDataKey(ByteBufCodecs.BOOL, false, true, TridentFestivalSkill.class));
        TRIDENT_AMOUNT = DATA_KEYS.register("trident_amount", () -> SkillDataKey.createSkillDataKey(ByteBufCodecs.INT, 0, true, TridentFestivalSkill.class));
        LEGENDARY_SWORD_AWAKENED = DATA_KEYS.register("legendary_sword_awakened", () -> SkillDataKey.createSkillDataKey(ByteBufCodecs.BOOL, false, true, LegendarySwordSkill.class));
        ENDER_SLAYER_SCYTHE_SUMMON_PENDING = DATA_KEYS.register("ender_slayer_scythe_summon_pending", () -> SkillDataKey.createSkillDataKey(ByteBufCodecs.BOOL, false, true, EnderSlayerScytheSkill.class));
        NULL_WEAPON_RELEASE_STACKS = DATA_KEYS.register("null_weapon_release_stacks", () -> SkillDataKey.createSkillDataKey(ByteBufCodecs.INT, 0, true, NullWeaponSkill.class));
    }
}
