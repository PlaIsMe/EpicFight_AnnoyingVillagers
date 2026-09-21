package com.pla.epicfight_annoyingvillagers.event;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID)
public class NbtCooldownEvent {
    private static final String NBT_KICK_CD = "KickAttackCooldown";
    private static final String NBT_STUN_ESCAPE_CD = "StunEscapeCooldown";

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (player.level().isClientSide()) return;
        if (!(player instanceof ServerPlayer)) return;

        CompoundTag data = player.getPersistentData();
        if (player.tickCount % 20 == 0 && data.contains(NBT_KICK_CD)) {
            int coolDownValue = data.getInt(NBT_KICK_CD);
            if (coolDownValue > 0) {
                data.putInt(NBT_KICK_CD, coolDownValue - 1);
            } else {
                data.remove(NBT_KICK_CD);
            }
        }

        if (player.tickCount % 20 == 0 && data.contains(NBT_STUN_ESCAPE_CD)) {
            int coolDownValue = data.getInt(NBT_STUN_ESCAPE_CD);
            if (coolDownValue > 0) {
                data.putInt(NBT_STUN_ESCAPE_CD, coolDownValue - 1);
            } else {
                data.remove(NBT_STUN_ESCAPE_CD);
            }
        }
    }
}
