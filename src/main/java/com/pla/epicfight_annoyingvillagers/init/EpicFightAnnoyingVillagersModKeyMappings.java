package com.pla.epicfight_annoyingvillagers.init;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.network.KickMessage;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(value = Dist.CLIENT)
public class EpicFightAnnoyingVillagersModKeyMappings {
    public static final KeyMapping KICK = new KeyMapping(
            "key.epicfight_annoyingvillagers.kick",
            GLFW.GLFW_KEY_X,
            "key.categories.epicfight_annoyingvillagers"
    ) {
        private boolean isDownOld = false;

        @Override
        public void setDown(boolean flag) {
            super.setDown(flag);

            if (this.isDownOld != flag && flag) {
                int strafe = readStrafeAD();
                PacketDistributor.sendToServer(new KickMessage(strafe));
            }

            this.isDownOld = flag;
        }
    };

    private static int readStrafeAD() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return 0;

        boolean left = mc.options.keyLeft.isDown();
        boolean right = mc.options.keyRight.isDown();

        if (left == right) return 0;
        return left ? -1 : 1;
    }

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(KICK);
    }

    @EventBusSubscriber(value = Dist.CLIENT)
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.screen == null) {
                KICK.consumeClick();
            }
        }
    }
}
