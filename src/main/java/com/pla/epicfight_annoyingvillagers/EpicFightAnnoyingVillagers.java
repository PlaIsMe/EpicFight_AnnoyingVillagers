package com.pla.epicfight_annoyingvillagers;


import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.pla.annoyingvillagers.init.*;
import com.pla.annoyingvillagers.network.*;
import com.pla.epicfight_annoyingvillagers.capabilities.AVWeaponCapabilityPresets;
import com.pla.epicfight_annoyingvillagers.config.EpicFightAnnoyingVillagersConfig;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkillCategories;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkillDataKeys;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkillSlots;
import com.pla.epicfight_annoyingvillagers.network.ClientboundEpicFightCameraFx;
import com.pla.epicfight_annoyingvillagers.network.KickMessage;
import com.pla.epicfight_annoyingvillagers.init.EpicFightAnnoyingVillagersModMenus;
import com.pla.epicfight_annoyingvillagers.network.BreakEmoteMessage;
import com.pla.epicfight_annoyingvillagers.network.EmoteButtonMessage;
import com.pla.epicfight_annoyingvillagers.network.OpenEmoteMenuMessage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.skill.SkillCategory;
import yesman.epicfight.skill.SkillSlot;

@Mod(EpicFightAnnoyingVillagers.MODID)
public class EpicFightAnnoyingVillagers {
    public static final Logger LOGGER = LogManager.getLogger(EpicFightAnnoyingVillagers.class);
    public static final String MODID = "epicfight_annoyingvillagers";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry
            .newSimpleChannel(ResourceLocation.fromNamespaceAndPath(EpicFightAnnoyingVillagers.MODID, "main"), () -> "1", "1"::equals, "1"::equals);
    private static int messageID = 0;

    public EpicFightAnnoyingVillagers(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        SkillCategory.ENUM_MANAGER.registerEnumCls(MODID, AVSkillCategories.class);
        SkillSlot.ENUM_MANAGER.registerEnumCls(MODID, AVSkillSlots.class);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(AVWeaponCapabilityPresets::register);
        context.registerConfig(ModConfig.Type.COMMON, EpicFightAnnoyingVillagersConfig.SPEC, "epicfight_annoyingvillagers-server.toml");
        AVSkillDataKeys.DATA_KEYS.register(modEventBus);
        EpicFightAnnoyingVillagersModMenus.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(EpicFightAnnoyingVillagers::registerArmatures);
    }

    public static <T> void addNetworkMessage(Class<T> oclass, BiConsumer<T, FriendlyByteBuf> biconsumer, Function<FriendlyByteBuf, T> function, BiConsumer<T, Supplier<Context>> biconsumer1) {
        EpicFightAnnoyingVillagers.PACKET_HANDLER.registerMessage(EpicFightAnnoyingVillagers.messageID, oclass, biconsumer, function, biconsumer1);
        ++EpicFightAnnoyingVillagers.messageID;
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class initer {
        @SubscribeEvent
        public static void init(FMLCommonSetupEvent fmlCommonSetupEvent) {
            EpicFightAnnoyingVillagers.addNetworkMessage(
                    ClientboundEpicFightCameraFx.class,
                    ClientboundEpicFightCameraFx::encode,
                    ClientboundEpicFightCameraFx::decode,
                    ClientboundEpicFightCameraFx::handle
            );

            EpicFightAnnoyingVillagers.addNetworkMessage(
                    KickMessage.class,
                    KickMessage::buffer,
                    KickMessage::new,
                    KickMessage::handle
            );
            EpicFightAnnoyingVillagers.addNetworkMessage(
                    EmoteButtonMessage.class, EmoteButtonMessage::encode,
                    EmoteButtonMessage::decode, EmoteButtonMessage::handle);
            EpicFightAnnoyingVillagers.addNetworkMessage(
                    OpenEmoteMenuMessage.class, OpenEmoteMenuMessage::encode,
                    OpenEmoteMenuMessage::decode, OpenEmoteMenuMessage::handle);
            EpicFightAnnoyingVillagers.addNetworkMessage(
                    BreakEmoteMessage.class, BreakEmoteMessage::encode,
                    BreakEmoteMessage::decode, BreakEmoteMessage::handle);
        }
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
