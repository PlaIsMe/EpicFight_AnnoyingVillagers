package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.clazz.ThrowableSpearItem;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = ThrowableSpearItem.class, remap = false)
public abstract class ThrowableSpearItemMixin {
    @Inject(method = "playEpicFightShotAnimation", at = @At("HEAD"))
    private static void playEpicFightShotAnimation(Player player, CallbackInfo ci) {
        LivingEntityPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, LivingEntityPatch.class);
        if (playerPatch != null) {
            playerPatch.playShootingAnimation();
        }
    }
}
