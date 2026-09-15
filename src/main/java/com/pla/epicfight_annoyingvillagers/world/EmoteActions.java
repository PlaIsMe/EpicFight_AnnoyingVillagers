package com.pla.epicfight_annoyingvillagers.world;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEmote;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class EmoteActions {
    public static final int PAGE_SIZE = 8;

    private EmoteActions() {}

    @FunctionalInterface
    public interface EmoteExecutor {
        boolean execute(ServerPlayer player);
    }

    public record EmoteAction(
            ResourceLocation key,
            String translationKey,
            ResourceLocation icon,
            Supplier<? extends AssetAccessor<? extends StaticAnimation>> animationSupplier,
            EmoteExecutor executor
    ) {
        public AssetAccessor<? extends StaticAnimation> animation() {
            return animationSupplier.get();
        }
    }

    private static ResourceLocation key(String path) {
        return ResourceLocation.fromNamespaceAndPath(EpicFightAnnoyingVillagers.MODID, path);
    }

    private static ResourceLocation icon(String path) {
        return ResourceLocation.fromNamespaceAndPath(
                EpicFightAnnoyingVillagers.MODID,
                "textures/gui/emotes/" + path + ".png"
        );
    }

    private static EmoteAction emote(String name, Supplier<? extends AssetAccessor<? extends StaticAnimation>> animation) {
        return new EmoteAction(
                key(name),
                "gui.epicfight_annoyingvillagers.emote." + name,
                icon(name),
                animation,
                player -> playEmote(player, animation.get())
        );
    }

    private static final List<EmoteAction> ACTIONS = List.of(
            emote("nl_bow_emote", () -> AnimsEmote.NL_BOW_EMOTE),
            emote("nl_bowing_emote", () -> AnimsEmote.NL_BOWING_EMOTE),
            emote("nl_goad_emote", () -> AnimsEmote.NL_GOAD_EMOTE),
            emote("nl_lol_emote", () -> AnimsEmote.NL_LOL_EMOTE),
            emote("nl_wave_emote", () -> AnimsEmote.NL_WAVE_EMOTE),
            emote("lay_emote", () -> AnimsEmote.LAY_EMOTE),
            emote("push_up_emote", () -> AnimsEmote.PUSH_UP_EMOTE),
            emote("sit_emote", () -> AnimsEmote.SIT_EMOTE),
            emote("slight_emote", () -> AnimsEmote.SLIGHT_EMOTE),
            emote("death_emote", () -> AnimsEmote.DEATH_EMOTE),
            emote("funny_emote", () -> AnimsEmote.FUNNY_EMOTE),
            emote("attention_emote", () -> AnimsEmote.ATTENTION_EMOTE),
            emote("flapping_emote", () -> AnimsEmote.FLAPPING_EMOTE),
            emote("fun_jump_emote", () -> AnimsEmote.FUN_JUMP_EMOTE),
            emote("jump_emote", () -> AnimsEmote.JUMP_EMOTE),
            emote("prone_emote", () -> AnimsEmote.PRONE_EMOTE),
            emote("salute_emote", () -> AnimsEmote.SALUTE_EMOTE),
            emote("lay_relax_emote", () -> AnimsEmote.LAY_RELAX_EMOTE),
            emote("one_arm_lay_emote", () -> AnimsEmote.ONE_ARM_LAY_EMOTE),
            emote("salute_left_hand_emote", () -> AnimsEmote.SALUTE_LEFT_HAND_EMOTE),
            emote("sit_no_weapon_emote", () -> AnimsEmote.SIT_NO_WEAPON_EMOTE),
            emote("sorrow_emote", () -> AnimsEmote.SORROW_EMOTE),
            emote("surrender_emote", () -> AnimsEmote.SURRENDER_EMOTE),
            emote("dance_1", () -> AnimsEmote.DANCE_1),
            emote("dance_2", () -> AnimsEmote.DANCE_2),
            emote("dance_3", () -> AnimsEmote.DANCE_3),
            emote("dance_4", () -> AnimsEmote.DANCE_4),
            emote("dance_5", () -> AnimsEmote.DANCE_5),
            emote("dance_6", () -> AnimsEmote.DANCE_6),
            emote("dance_7", () -> AnimsEmote.DANCE_7),
            emote("dance_8", () -> AnimsEmote.DANCE_8),
            emote("dance_9", () -> AnimsEmote.DANCE_9),
            emote("dance_10", () -> AnimsEmote.DANCE_10),
            emote("dance_11", () -> AnimsEmote.DANCE_11),
            emote("dance_12", () -> AnimsEmote.DANCE_12)
    );

    private static boolean playEmote(ServerPlayer player, AssetAccessor<? extends StaticAnimation> animation) {
        if (animation == null || !player.isAlive() || player.isSpectator() || player.isPassenger()) return false;
        PlayerPatch<?> patch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (patch instanceof ServerPlayerPatch serverPlayerPatch && !patch.isStunned()
                && !patch.getEntityState().inaction() && patch.getEntityState().canBasicAttack()) {
            serverPlayerPatch.playAnimationSynchronized(animation, 0.0F);
            return true;
        }
        return false;
    }

    public static boolean isEmoteAnimation(AssetAccessor<? extends DynamicAnimation> animation) {
        return animation != null && ACTIONS.stream().anyMatch(action -> animation.equals(action.animation()));
    }

    private static final Map<ResourceLocation, EmoteAction> BY_KEY = ACTIONS.stream()
            .collect(Collectors.toMap(EmoteAction::key, Function.identity()));

    public static int pageCount() {
        return Math.max(1, (ACTIONS.size() + PAGE_SIZE - 1) / PAGE_SIZE);
    }

    public static List<EmoteAction> getPage(int page) {
        int clampedPage = Math.max(0, Math.min(page, pageCount() - 1));
        int from = clampedPage * PAGE_SIZE;
        int to = Math.min(from + PAGE_SIZE, ACTIONS.size());
        return ACTIONS.subList(from, to);
    }

    public static boolean run(ResourceLocation key, ServerPlayer player) {
        EmoteAction action = BY_KEY.get(key);
        return action != null && action.executor().execute(player);
    }
}
