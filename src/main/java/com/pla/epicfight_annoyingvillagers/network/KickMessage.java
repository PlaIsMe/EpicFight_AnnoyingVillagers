package com.pla.epicfight_annoyingvillagers.network;
import java.util.function.Supplier;

import com.pla.epicfight_annoyingvillagers.event.KickOnKeyPressedEvent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.network.NetworkEvent;

@EventBusSubscriber(bus = Bus.MOD)
public class KickMessage {
    private final byte strafe;

    public KickMessage(int strafe) {
        this.strafe = (byte) Math.max(-1, Math.min(1, strafe));
    }

    public KickMessage(FriendlyByteBuf buf) {
        this.strafe = buf.readByte();
    }

    public static void buffer(KickMessage msg, FriendlyByteBuf buf) {
        buf.writeByte(msg.strafe);
    }

    public static void handle(KickMessage msg, Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context ctx = supplier.get();
        ServerPlayer sender = ctx.getSender();
        if (sender == null) {
            ctx.setPacketHandled(true);
            return;
        }

        ctx.enqueueWork(() -> {
            KickOnKeyPressedEvent.execute(sender, msg.strafe);
        });

        ctx.setPacketHandled(true);
    }
}
