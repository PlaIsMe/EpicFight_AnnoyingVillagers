package com.pla.epicfight_annoyingvillagers.gameasset;

import com.hm.efn.gameasset.animations.EFNDualSwordAnimations;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.skill.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.registry.EpicFightRegistries;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.ExtraDamageInstance;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;
import java.util.function.Function;

public class AVSkills {
    public static DeferredHolder<Skill, Skill> ENDER_AEGIS;
    public static DeferredHolder<Skill, Skill> ENDER_GLAIVE;
    public static DeferredHolder<Skill, Skill> DEMONIAC_VOLTAGE_REAVER;
    public static DeferredHolder<Skill, Skill> OBSIDIAN_SLEDGEHAMMER;
    public static DeferredHolder<Skill, Skill> ENDER_SLAYER_SCYTHE;
    public static DeferredHolder<Skill, Skill> NULL_WEAPON;
    public static DeferredHolder<Skill, Skill> OBSIDIAN_WEAPON;
    public static DeferredHolder<Skill, Skill> SHADOW_OBSIDIAN_PILLAR;
    public static DeferredHolder<Skill, Skill> SHADOW_OBSIDIAN_PILLAR_SWORD;
    public static DeferredHolder<Skill, Skill> SHADOW_OBSIDIAN_SWORD;
    public static DeferredHolder<Skill, Skill> SHADOW_OBSIDIAN_SWORD_DUAL;

    public static DeferredHolder<Skill, Skill> TRIDENT_FESTIVAL;
    public static DeferredHolder<Skill, Skill> LEGENDARY_SWORD;

    public static DeferredHolder<Skill, Skill> SWORD;
    public static DeferredHolder<Skill, Skill> DUAL_SWORD;
    public static DeferredHolder<Skill, Skill> WOOPIE_THE_SWORD;
    public static DeferredHolder<Skill, Skill> GREAT_SWORD;
    public static DeferredHolder<Skill, Skill> THUNDER_DIAMOND_BLADE;
    public static DeferredHolder<Skill, Skill> DUAL_THUNDER_DIAMOND_BLADE;
    public static DeferredHolder<Skill, Skill> BLACK_FIRE_SWORD;
    public static DeferredHolder<Skill, Skill> DIAMOND_ATTRACTOR_SWORD;
    public static DeferredHolder<Skill, Skill> DIAMOND_BLASTER_SWORD;
    public static DeferredHolder<Skill, Skill> HACKER_SWORD;
    public static DeferredHolder<Skill, Skill> HOOK_SWORD;
    public static DeferredHolder<Skill, Skill> DUAL_HOOK_SWORD;
    public static DeferredHolder<Skill, Skill> FLANKER_HOOK_SWORD;
    public static DeferredHolder<Skill, Skill> DNAX_HOOK_SWORD;
    public static DeferredHolder<Skill, Skill> DUAL_DNAX_HOOK_SWORD;

    public static DeferredHolder<Skill, Skill> TACHI;

    public static DeferredHolder<Skill, Skill> LONGSWORD;
    public static DeferredHolder<Skill, Skill> DUAL_LONGSWORD;

    public static DeferredHolder<Skill, Skill> HELICOPTER;
    public static DeferredHolder<Skill, Skill> GREATAXE;

    public static DeferredHolder<Skill, Skill> AXE;
    public static DeferredHolder<Skill, Skill> EARTH_AXE;
    public static DeferredHolder<Skill, Skill> DUAL_AXE_SPIN;

    public static DeferredHolder<Skill, Skill> DAGGER;

    public static DeferredHolder<Skill, Skill> SPEAR;
    public static DeferredHolder<Skill, Skill> STAFF;
    public static DeferredHolder<Skill, Skill> SICKLE;
    public static DeferredHolder<Skill, Skill> BLACKSCRATCHER;

    public static DeferredHolder<Skill, Skill> KICK;
    public static DeferredHolder<Skill, Skill> STUN_ESCAPE;

    public static final DeferredRegister<Skill> SKILLS = DeferredRegister.create(
            EpicFightRegistries.Keys.SKILL, EpicFightAnnoyingVillagers.MODID);

    private static DeferredHolder<Skill, Skill> register(String name, Function<ResourceLocation, ? extends Skill> factory) {
        return SKILLS.register(name, () -> factory.apply(ResourceLocation.fromNamespaceAndPath(EpicFightAnnoyingVillagers.MODID, name)));
    }

    static {
        ENDER_AEGIS = register("ender_aegis", id -> WeaponInnateSkill.createWeaponInnateBuilder(EnderAegisSkill::new).build(id));
        ENDER_GLAIVE = register("ender_glaive", id -> WeaponInnateSkill.createWeaponInnateBuilder(EnderGlaiveSkill::new).build(id));
        DEMONIAC_VOLTAGE_REAVER = register("demoniac_voltage_reaver", id -> WeaponInnateSkill.createWeaponInnateBuilder(DemoniacVoltageReaverSkill::new).build(id));
        OBSIDIAN_SLEDGEHAMMER = register("obsidian_sledgehammer", id -> WeaponInnateSkill.createWeaponInnateBuilder(ObsidianSledgeHammerSkill::new).build(id));
        ENDER_SLAYER_SCYTHE = register("ender_slayer_scythe", id -> WeaponInnateSkill.createWeaponInnateBuilder(EnderSlayerScytheSkill::new).setActivateType(Skill.ActivateType.DURATION).build(id));
        OBSIDIAN_WEAPON = register("obsidian_weapon", id -> WeaponInnateSkill.createWeaponInnateBuilder(ObsidianWeaponSkill::new).build(id));
        NULL_WEAPON = register("null_weapon", id -> WeaponInnateSkill.createWeaponInnateBuilder(NullWeaponSkill::new).setActivateType(Skill.ActivateType.DURATION).build(id));
        SHADOW_OBSIDIAN_PILLAR = register("shadow_obsidian_pillar", id -> WeaponInnateSkill.createWeaponInnateBuilder(ShadowObsidianPillarSkill::new).build(id));
        SHADOW_OBSIDIAN_PILLAR_SWORD = register("shadow_obsidian_pillar_sword", id -> WeaponInnateSkill.createWeaponInnateBuilder(ShadowObsidianPillarSwordSkill::new).build(id));
        SHADOW_OBSIDIAN_SWORD = register("shadow_obsidian_sword", id -> WeaponInnateSkill.createWeaponInnateBuilder(ShadowObsidianSwordSkill::new).build(id));
        SHADOW_OBSIDIAN_SWORD_DUAL = register("shadow_obsidian_sword_dual", id -> WeaponInnateSkill.createWeaponInnateBuilder(ShadowObsidianSwordDualSkill::new).build(id));

        LEGENDARY_SWORD = register("legendary_sword", id -> WeaponInnateSkill.createWeaponInnateBuilder(LegendarySwordSkill::new).build(id));
        TRIDENT_FESTIVAL = register("trident_festival", id -> WeaponInnateSkill.createWeaponInnateBuilder(TridentFestivalSkill::new).build(id));

        SWORD = register("sword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(EFNDualSwordAnimations.NF_DUAL_DODGE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        DUAL_SWORD = register("dual_sword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsEpicFightAwaken.HOOK_SLASH_GROUND).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        WOOPIE_THE_SWORD = register("woopie_the_sword", id -> WeaponInnateSkill.createWeaponInnateBuilder(WoopieTheSwordSkill::new).build(id));
        GREAT_SWORD = register("great_sword", id -> WeaponInnateSkill.createWeaponInnateBuilder(GreatSwordSkill::new).setActivateType(Skill.ActivateType.DURATION).build(id));
        THUNDER_DIAMOND_BLADE = register("thunder_diamond_blade", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSword.THUNDER_DIAMOND_BLADE_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        DUAL_THUNDER_DIAMOND_BLADE = register("dual_thunder_diamond_blade", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSword.THUNDER_DIAMOND_BLADE_DUAL_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F)).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F)).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.2F)).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        BLACK_FIRE_SWORD = register("black_fire_sword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSword.BLACK_FIRE_SWORD_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        DIAMOND_ATTRACTOR_SWORD = register("diamond_attractor_sword", id -> WeaponInnateSkill.createWeaponInnateBuilder(DiamondAttractorSwordSkill::new).build(id));
        DIAMOND_BLASTER_SWORD = register("diamond_blaster_sword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSword.DIAMOND_BLASTER_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        HACKER_SWORD = register("hacker_sword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSword.HACKER_SWORD_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        HOOK_SWORD = register("hook_sword", id -> WeaponInnateSkill.createWeaponInnateBuilder(HookSwordSkill::new).build(id));
        DUAL_HOOK_SWORD = register("dual_hook_sword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSword.HOOK_SWORD_DUAL_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        FLANKER_HOOK_SWORD = register("flanker_hook_sword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSword.FLANKER_HOOK_SWORD_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        DNAX_HOOK_SWORD = register("dnax_hook_sword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSword.DNAX_HOOK_SWORD_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        DUAL_DNAX_HOOK_SWORD = register("dual_dnax_hook_sword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSword.DNAX_HOOK_SWORD_DUAL_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));

        TACHI = register("tachi", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVTachi.AV_TACHI_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        LONGSWORD = register("longsword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsEpicFightAwaken.STRAIGHTSWORD_HEAVY_AUTO5).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        DUAL_LONGSWORD = register("dual_longsword", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsEpicFightAwaken.DP_DUSK_REAVER_2).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));

        HELICOPTER = register("helicopter", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVGreatsword.AV_GREATSWORD_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        GREATAXE = register("greataxe", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVGreatsword.AV_GREATAXE_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));

        AXE = register("axe", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVAxe.AV_AXE_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        EARTH_AXE = register("earth_axe", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVAxe.EARTH_AXE_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        DUAL_AXE_SPIN = register("dual_axe_spin", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVAxe.AV_AXE_DUAL_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));

        DAGGER = register("dagger", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSword.AV_DAGGER_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));

        SPEAR = register("spear", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSpear.AV_SPEAR_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        STAFF = register("staff", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSpear.STAFF_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        SICKLE = register("sickle", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSpear.SICKLE_INNATE).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));
        BLACKSCRATCHER = register("blackscratcher", id -> SimpleWeaponInnateSkill.createSimpleWeaponInnateBuilder(SimpleWeaponInnateSkill::new).setAnimations(AnimsAVSpear.BLACKSCRATCHER_ATTACK).newProperty().addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.adder(1.0F)).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F)).addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(20.0F)).addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.6F)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG).addProperty(AnimationProperty.AttackPhaseProperty.EXTRA_DAMAGE, Set.of(ExtraDamageInstance.SWEEPING_EDGE_ENCHANTMENT.create())).addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.WEAPON_INNATE)).build(id));

        AVSkills.KICK = register("kick", id -> PassiveSkill.createPassiveBuilder(KickSkill::new)
                .setCategory(AVSkillCategories.KICK).build(id));
        AVSkills.STUN_ESCAPE = register("stun_escape", id -> PassiveSkill.createPassiveBuilder(StunEscapeSkill::new)
                .setCategory(AVSkillCategories.STUN_ESCAPE).build(id));
    }
}
