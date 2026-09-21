package com.pla.epicfight_annoyingvillagers.network;

import com.pla.epicfight_annoyingvillagers.client.engine.EpicFightAnnoyingVillagersClientPacketHandlers;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ClientboundEpicFightCameraFx(int action, float fovModifier, int fovTicks, float blurStrength, int blurTicks)
        implements CustomPacketPayload {
    public static final Type<ClientboundEpicFightCameraFx> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(
            EpicFightAnnoyingVillagers.MODID, "camera_fx"));
    public static final StreamCodec<ByteBuf, ClientboundEpicFightCameraFx> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, ClientboundEpicFightCameraFx::action,
            ByteBufCodecs.FLOAT, ClientboundEpicFightCameraFx::fovModifier,
            ByteBufCodecs.VAR_INT, ClientboundEpicFightCameraFx::fovTicks,
            ByteBufCodecs.FLOAT, ClientboundEpicFightCameraFx::blurStrength,
            ByteBufCodecs.VAR_INT, ClientboundEpicFightCameraFx::blurTicks,
            ClientboundEpicFightCameraFx::new);
    public static final int ACTION_ZOOM_IN = 0;
    public static final int ACTION_RESET_ZOOM_AND_BLUR = 1;
    public static final int ACTION_BLUR = 2;

    public static ClientboundEpicFightCameraFx zoomIn(float fovModifier, int fovTicks) {
        return new ClientboundEpicFightCameraFx(ACTION_ZOOM_IN, fovModifier, fovTicks, 0.0F, 0);
    }

    public static ClientboundEpicFightCameraFx resetZoomAndBlur(float blurStrength, int blurTicks) {
        return new ClientboundEpicFightCameraFx(ACTION_RESET_ZOOM_AND_BLUR, 0.0F, 0, blurStrength, blurTicks);
    }

    public static ClientboundEpicFightCameraFx blur(float blurStrength, int blurTicks) {
        return new ClientboundEpicFightCameraFx(ACTION_BLUR, 0.0F, 0, blurStrength, blurTicks);
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> EpicFightAnnoyingVillagersClientPacketHandlers.handleEpicFightCameraFx(this));
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
