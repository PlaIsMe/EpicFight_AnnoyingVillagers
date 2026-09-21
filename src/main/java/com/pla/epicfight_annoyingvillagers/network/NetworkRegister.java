package com.pla.epicfight_annoyingvillagers.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class NetworkRegister {
    private NetworkRegister() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(KickMessage.TYPE, KickMessage.STREAM_CODEC, KickMessage::handle);
        registrar.playToClient(ClientboundEpicFightCameraFx.TYPE, ClientboundEpicFightCameraFx.STREAM_CODEC,
                ClientboundEpicFightCameraFx::handle);
    }
}
