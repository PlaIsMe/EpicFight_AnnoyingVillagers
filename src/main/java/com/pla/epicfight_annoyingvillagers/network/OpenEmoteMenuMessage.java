package com.pla.epicfight_annoyingvillagers.network;

import com.pla.epicfight_annoyingvillagers.world.EmoteMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenEmoteMenuMessage {

    public OpenEmoteMenuMessage() {
    }

    public static void encode(OpenEmoteMenuMessage msg, FriendlyByteBuf buf) {
    }

    public static OpenEmoteMenuMessage decode(FriendlyByteBuf buf) {
        return new OpenEmoteMenuMessage();
    }

    public static void handle(OpenEmoteMenuMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            ServerPlayer serverPlayer = ctx.getSender();
            if (serverPlayer == null || !serverPlayer.isAlive() || serverPlayer.isSpectator()
                    || serverPlayer.containerMenu instanceof EmoteMenu) {
                return;
            }

            serverPlayer.openMenu(
                    new SimpleMenuProvider(
                            (containerId, inventory, player) -> new EmoteMenu(containerId, inventory),
                            Component.translatable("gui.epicfight_annoyingvillagers.emote.title")
                    )
            );
        });

        ctx.setPacketHandled(true);
    }
}
