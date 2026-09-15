package com.pla.epicfight_annoyingvillagers.network;

import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEmote;
import com.pla.epicfight_annoyingvillagers.world.EmoteActions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

import java.util.function.Supplier;

public class BreakEmoteMessage {
    public BreakEmoteMessage() {
    }

    public static void encode(BreakEmoteMessage msg, FriendlyByteBuf buf) {
    }

    public static BreakEmoteMessage decode(FriendlyByteBuf buf) {
        return new BreakEmoteMessage();
    }

    public static void handle(BreakEmoteMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null) {
                return;
            }

            LivingEntityPatch<?> livingPatch =
                    EpicFightCapabilities.getEntityPatch(player, LivingEntityPatch.class);
            if (livingPatch == null) {
                return;
            }

            AnimationPlayer animationPlayer =
                    livingPatch.getAnimator().getPlayerFor(Animations.EMPTY_ANIMATION);
            if (animationPlayer == null) {
                return;
            }

            AssetAccessor<? extends DynamicAnimation> current = animationPlayer.getRealAnimation();
            if (!EmoteActions.isEmoteAnimation(current)) {
                return;
            }

            if (livingPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                serverPlayerPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
            } else {
                livingPatch.getAnimator().playAnimation(AVAnimations.IDLE_BREAK, 0.0F);
            }
        });

        ctx.setPacketHandled(true);
    }
}
