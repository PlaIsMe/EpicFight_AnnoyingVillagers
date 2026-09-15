package com.pla.epicfight_annoyingvillagers.network;

import com.pla.epicfight_annoyingvillagers.world.EmoteActions;
import com.pla.epicfight_annoyingvillagers.world.EmoteMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record EmoteButtonMessage(ResourceLocation actionKey) {

    public static void encode(EmoteButtonMessage msg, FriendlyByteBuf buf) {
        buf.writeResourceLocation(msg.actionKey());
    }

    public static EmoteButtonMessage decode(FriendlyByteBuf buf) {
        return new EmoteButtonMessage(buf.readResourceLocation());
    }

    public static void handle(EmoteButtonMessage msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player == null || !(player.containerMenu instanceof EmoteMenu)) {
                return;
            }

            boolean played = EmoteActions.run(msg.actionKey(), player);
            if (played) {
                player.closeContainer();
            }
        });

        ctx.setPacketHandled(true);
    }
}
