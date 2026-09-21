package com.pla.epicfight_annoyingvillagers.client.engine;

import com.pla.epicfight_annoyingvillagers.network.ClientboundEpicFightCameraFx;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EpicFightAnnoyingVillagersClientPacketHandlers {
    public static void handleEpicFightCameraFx(ClientboundEpicFightCameraFx msg) {
        switch (msg.action()) {
            case ClientboundEpicFightCameraFx.ACTION_ZOOM_IN ->
                    EpicFightCameraFxClient.zoomIn(msg.fovModifier(), msg.fovTicks());
            case ClientboundEpicFightCameraFx.ACTION_RESET_ZOOM_AND_BLUR ->
                    EpicFightCameraFxClient.resetZoomAndBlur(msg.blurStrength(), msg.blurTicks());
            case ClientboundEpicFightCameraFx.ACTION_BLUR ->
                    EpicFightCameraFxClient.triggerBlur(msg.blurStrength(), msg.blurTicks());
            default -> {
            }
        }
    }
}
