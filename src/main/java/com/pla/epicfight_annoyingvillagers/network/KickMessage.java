package com.pla.epicfight_annoyingvillagers.network;

import com.pla.epicfight_annoyingvillagers.event.KickOnKeyPressedEvent;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record KickMessage(byte strafe) implements CustomPacketPayload {
    public static final Type<KickMessage> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(
            EpicFightAnnoyingVillagers.MODID, "kick"));
    public static final StreamCodec<ByteBuf, KickMessage> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE, KickMessage::strafe, KickMessage::new);

    public KickMessage(int strafe) {
        this((byte) Math.max(-1, Math.min(1, strafe)));
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> KickOnKeyPressedEvent.execute(context.player(), this.strafe));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
