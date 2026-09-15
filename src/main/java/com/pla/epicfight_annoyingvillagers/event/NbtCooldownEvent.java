package com.pla.epicfight_annoyingvillagers.event;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class NbtCooldownEvent {
    private static final String NBT_KICK_CD = "KickAttackCooldown";
    private static final String NBT_STUN_ESCAPE_CD = "StunEscapeCooldown";

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (event.phase != TickEvent.Phase.END) return;
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
