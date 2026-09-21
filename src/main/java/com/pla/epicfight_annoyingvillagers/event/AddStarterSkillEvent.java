package com.pla.epicfight_annoyingvillagers.event;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkillSlots;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkills;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import yesman.epicfight.client.online.cosmetics.Emote;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.common.BiDirectionalSyncEmoteSlots;
import yesman.epicfight.registry.EpicFightRegistries;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.emote.PlayerEmoteSlots;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EventBusSubscriber
public class AddStarterSkillEvent {
    private static final String KEY = EpicFightAnnoyingVillagers.MODID + ":has_joined_before";
    private static final String STARTER_EMOTES_KEY = EpicFightAnnoyingVillagers.MODID + ":has_starter_emotes_v1";
    private static final int EMOTES_PER_TAB = 6;
    private static final int MAX_EMOTE_TABS = 9;
    private static final List<ResourceKey<Emote>> STARTER_EMOTES = List.of(
            emoteKey("lay_emote"),
            emoteKey("push_up_emote"),
            emoteKey("sit_emote"),
            emoteKey("slight_emote"),
            emoteKey("death_emote"),
            emoteKey("funny_emote"),
            emoteKey("attention_emote"),
            emoteKey("flapping_emote"),
            emoteKey("fun_jump_emote"),
            emoteKey("jump_emote"),
            emoteKey("prone_emote"),
            emoteKey("salute_emote"),
            emoteKey("dance_1"),
            emoteKey("dance_2"),
            emoteKey("dance_3"),
            emoteKey("dance_4"),
            emoteKey("dance_5"),
            emoteKey("dance_6"),
            emoteKey("dance_7"),
            emoteKey("dance_8"),
            emoteKey("dance_9"),
            emoteKey("dance_10"),
            emoteKey("dance_11"),
            emoteKey("piglin_celebrate_emote"),
            emoteKey("nl_bow_emote"),
            emoteKey("nl_bowing_emote"),
            emoteKey("nl_goad_emote"),
            emoteKey("nl_lol_emote"),
            emoteKey("nl_wave_emote"),
            emoteKey("lay_relax_emote"),
            emoteKey("one_arm_lay_emote"),
            emoteKey("salute_left_hand_emote"),
            emoteKey("sit_no_weapon_emote"),
            emoteKey("sorrow_emote"),
            emoteKey("surrender_emote")
    );

    private static ResourceKey<Emote> emoteKey(String path) {
        return ResourceKey.create(
                EpicFightRegistries.Keys.EMOTE,
                ResourceLocation.fromNamespaceAndPath(EpicFightAnnoyingVillagers.MODID, path)
        );
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent playerloggedinevent) {
        execute(playerloggedinevent, playerloggedinevent.getEntity().level(), playerloggedinevent.getEntity().getX(), playerloggedinevent.getEntity().getY(), playerloggedinevent.getEntity().getZ(), playerloggedinevent.getEntity());
    }

    @SubscribeEvent
    public static void onClone(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;

        Player oldPlayer = event.getOriginal();
        Player newPlayer = event.getEntity();

        CompoundTag oldData = oldPlayer.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag newRoot = newPlayer.getPersistentData();
        CompoundTag newData = newRoot.getCompound(Player.PERSISTED_NBT_TAG);

        newData.merge(oldData);
        newRoot.put(Player.PERSISTED_NBT_TAG, newData);
    }

    public static void execute(LevelAccessor levelaccessor, double d0, double d1, double d2, Entity entity) {
        execute(null, levelaccessor, d0, d1, d2, entity);
    }

    private static CompoundTag persisted(Player p) {
        CompoundTag root = p.getPersistentData();
        CompoundTag data = root.getCompound(Player.PERSISTED_NBT_TAG);
        root.put(Player.PERSISTED_NBT_TAG, data);
        return data;
    }
    public static boolean hasJoinedBefore(Player p) {
        return persisted(p).getBoolean(KEY);
    }
    public static void markJoined(Player p) {
        persisted(p).putBoolean(KEY, true);
    }

    private static boolean hasStarterEmotes(Player player) {
        return persisted(player).getBoolean(STARTER_EMOTES_KEY);
    }

    private static void markStarterEmotesAdded(Player player) {
        persisted(player).putBoolean(STARTER_EMOTES_KEY, true);
    }

    private static void giveSkill(ServerPlayer player, ServerPlayerPatch patch, SkillSlot slot, Skill skill) {
        if (skill == null) return;

        SkillContainer container = patch.getPlayerSkills().getSkillContainerFor(slot);
        if (container == null) return;

        if (container.setSkill(skill)) {
            if (skill.getCategory().learnable()) {
                patch.getPlayerSkills().addLearnedSkill(skill);
            }

            EpicFightNetworkManager.sendToPlayer(container.createSyncPacketToLocalPlayer(), player);
            EpicFightNetworkManager.sendToAllPlayerTrackingThisEntity(container.createSyncPacketToRemotePlayer(), player);
        }
    }

    private static boolean addStarterEmotes(ServerPlayer player, ServerPlayerPatch patch) {
        PlayerEmoteSlots emoteSlots = patch.getEmoteSlots();
        List<AssignedEmote> assignedEmotes = new ArrayList<>();
        Set<String> assignedIds = new HashSet<>();

        emoteSlots.listEmotes((tab, slot, emote) -> {
            if (emote != null) {
                assignedEmotes.add(new AssignedEmote(tab, slot, emote));
                assignedIds.add(emote.getRegisteredName());
            }
        });

        List<Holder.Reference<Emote>> missingEmotes = new ArrayList<>();
        for (ResourceKey<Emote> emoteKey : STARTER_EMOTES) {
            if (assignedIds.contains(emoteKey.location().toString())) {
                continue;
            }

            Holder.Reference<Emote> emote = player.registryAccess().holder(emoteKey).orElse(null);
            if (emote == null) {
                EpicFightAnnoyingVillagers.LOGGER.warn("Could not add starter emote {} because it is not registered", emoteKey.location());
                return false;
            }
            missingEmotes.add(emote);
        }

        if (missingEmotes.isEmpty()) {
            return true;
        }

        int currentTabs = Math.max(emoteSlots.tabs(), 1);
        int requiredTabs = Math.max(
                currentTabs,
                (assignedEmotes.size() + missingEmotes.size() + EMOTES_PER_TAB - 1) / EMOTES_PER_TAB
        );
        if (requiredTabs > MAX_EMOTE_TABS) {
            EpicFightAnnoyingVillagers.LOGGER.warn(
                    "Could not add all starter emotes for {}: the Epic Fight emote wheel would require {} tabs, but supports at most {}",
                    player.getGameProfile().getName(),
                    requiredTabs,
                    MAX_EMOTE_TABS
            );
            return false;
        }

        boolean[][] occupied = new boolean[requiredTabs][EMOTES_PER_TAB];
        if (emoteSlots.tabs() != requiredTabs) {
            emoteSlots.reset(requiredTabs);
            for (AssignedEmote assigned : assignedEmotes) {
                emoteSlots.setEmote(assigned.tab(), assigned.slot(), assigned.emote());
            }
        }
        for (AssignedEmote assigned : assignedEmotes) {
            occupied[assigned.tab()][assigned.slot()] = true;
        }

        int missingIndex = 0;
        for (int tab = 0; tab < requiredTabs && missingIndex < missingEmotes.size(); tab++) {
            for (int slot = 0; slot < EMOTES_PER_TAB && missingIndex < missingEmotes.size(); slot++) {
                if (!occupied[tab][slot]) {
                    emoteSlots.setEmote(tab, slot, missingEmotes.get(missingIndex++));
                }
            }
        }

        EpicFightNetworkManager.sendToPlayer(new BiDirectionalSyncEmoteSlots(patch), player);
        return true;
    }

    private static void execute(@Nullable Event event, LevelAccessor levelaccessor, double d0, double d1, double d2, final Entity entity) {
        if (entity instanceof ServerPlayer serverPlayer && !entity.level().isClientSide() && entity.getServer() != null) {
            ServerPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, ServerPlayerPatch.class);
            if (playerPatch == null) return;

            if (!hasJoinedBefore(serverPlayer)) {
                    giveSkill(serverPlayer, playerPatch, AVSkillSlots.KICK, AVSkills.KICK.get());
                    giveSkill(serverPlayer, playerPatch, AVSkillSlots.STUN_ESCAPE, AVSkills.STUN_ESCAPE.get());
                markJoined(serverPlayer);
            }

            if (!hasStarterEmotes(serverPlayer) && addStarterEmotes(serverPlayer, playerPatch)) {
                markStarterEmotesAdded(serverPlayer);
            }
        }
    }

    private record AssignedEmote(int tab, int slot, Holder.Reference<Emote> emote) {
    }
}
