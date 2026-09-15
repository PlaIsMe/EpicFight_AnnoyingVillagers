package com.pla.epicfight_annoyingvillagers.event;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.network.BreakEmoteMessage;
import net.minecraft.client.player.Input;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientMovementInputEvents {
    private static boolean wasMovePressed;
    private static boolean wasJumpPressed;
    private static boolean wasSneakPressed;

    @SubscribeEvent
    public static void onMovementInput(MovementInputUpdateEvent event) {
        Input input = event.getInput();

        boolean movePressed = input.up || input.down || input.left || input.right;
        boolean jumpPressed = input.jumping;
        boolean sneakPressed = input.shiftKeyDown;

        boolean shouldBreak =
                (movePressed && !wasMovePressed)
                        || (jumpPressed && !wasJumpPressed)
                        || (sneakPressed && !wasSneakPressed);

        if (shouldBreak) {
            EpicFightAnnoyingVillagers.PACKET_HANDLER.sendToServer(new BreakEmoteMessage());
        }

        wasMovePressed = movePressed;
        wasJumpPressed = jumpPressed;
        wasSneakPressed = sneakPressed;
    }
}
