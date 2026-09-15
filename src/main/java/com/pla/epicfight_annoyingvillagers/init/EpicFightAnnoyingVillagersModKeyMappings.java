package com.pla.epicfight_annoyingvillagers.init;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.network.KickMessage;
import com.pla.epicfight_annoyingvillagers.network.OpenEmoteMenuMessage;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class EpicFightAnnoyingVillagersModKeyMappings {
    public static final KeyMapping OPEN_EMOTE_MENU = new KeyMapping(
            "key.epicfight_annoyingvillagers.open_emote_menu",
            KeyConflictContext.UNIVERSAL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_GRAVE_ACCENT,
            "key.categories.epicfight_annoyingvillagers"
    );
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
                EpicFightAnnoyingVillagers.PACKET_HANDLER.sendToServer(new KickMessage(strafe));
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
        event.register(OPEN_EMOTE_MENU);
    }

    @Mod.EventBusSubscriber(value = Dist.CLIENT)
    public static class KeyEventListener {
        @SubscribeEvent
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) {
                return;
            }

            Minecraft mc = Minecraft.getInstance();

            while (OPEN_EMOTE_MENU.consumeClick()) {
                if (mc.player != null && mc.screen == null) {
                    EpicFightAnnoyingVillagers.PACKET_HANDLER.sendToServer(new OpenEmoteMenuMessage());
                }
            }

            if (mc.screen == null) {
                KICK.consumeClick();
            }
        }
    }
}
