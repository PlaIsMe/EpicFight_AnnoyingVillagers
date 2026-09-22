package com.pla.epicfight_annoyingvillagers;


import com.pla.annoyingvillagers.init.*;
import com.pla.epicfight_annoyingvillagers.capabilities.AVWeaponCapabilityPresets;
import com.pla.epicfight_annoyingvillagers.config.EpicFightAnnoyingVillagersConfig;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkillCategories;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkillDataKeys;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkillSlots;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkills;
import com.pla.epicfight_annoyingvillagers.init.EpicFightAnnoyingVillagersModPatchEntities;
import com.pla.epicfight_annoyingvillagers.network.NetworkRegister;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

@Mod(EpicFightAnnoyingVillagers.MODID)
public class EpicFightAnnoyingVillagers {
    public static final Logger LOGGER = LogManager.getLogger(EpicFightAnnoyingVillagers.class);
    public static final String MODID = "epicfight_annoyingvillagers";
    public EpicFightAnnoyingVillagers(IEventBus modEventBus, ModContainer modContainer) {
        SkillCategory.ENUM_MANAGER.registerEnumCls(MODID, AVSkillCategories.class);
        SkillSlot.ENUM_MANAGER.registerEnumCls(MODID, AVSkillSlots.class);
        modEventBus.addListener(this::commonSetup);
        // Epic Fight 21 scales every LivingEntityPatch attack animation from the
        // entity's vanilla ATTACK_SPEED attribute. Register the patched mob
        // attributes on the mod bus, where EntityAttributeModificationEvent fires.
        modEventBus.addListener(EpicFightAnnoyingVillagersModPatchEntities::addEpicFightAttributes);
        EpicFightEventHooks.Registry.WEAPON_CAPABILITY_PRESET.registerEvent(
                AVWeaponCapabilityPresets::register, MODID);
        EpicFightEventHooks.Registry.ENTITY_PATCH.registerEvent(
                EpicFightAnnoyingVillagersModPatchEntities::setPatch, MODID);
        modEventBus.addListener(NetworkRegister::register);
        modContainer.registerConfig(ModConfig.Type.COMMON, EpicFightAnnoyingVillagersConfig.SPEC, "epicfight_annoyingvillagers-server.toml");
        AVSkillDataKeys.DATA_KEYS.register(modEventBus);
        AVSkills.SKILLS.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(EpicFightAnnoyingVillagers::registerArmatures);
    }

    public static void registerArmatures() {
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.STEVE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.ANGRY_STEVE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.ALEX.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.JEV.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.CHRIS.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.NULL_SWORD.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.NULL_SHOVEL.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.NULL_AXE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.NULL_PICKAXE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.NULL_HOE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.NULL_SKELETON.get(), Armatures.SKELETON);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.NULL.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.HEROBRINE_CLONE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.HEROBRINE_7.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.BLUE_DEMON.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.INFECTED_CHRIS.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.INFECTED_PLAYER_NPC.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.HEROBRINE_GREG.get(), Armatures.BIPED);
        Armatures.registerEntityTypeArmature(AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(), Armatures.BIPED);
    }
}
