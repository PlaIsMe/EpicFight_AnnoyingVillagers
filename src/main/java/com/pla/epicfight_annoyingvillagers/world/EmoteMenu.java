package com.pla.epicfight_annoyingvillagers.world;

import com.pla.epicfight_annoyingvillagers.init.EpicFightAnnoyingVillagersModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EmoteMenu extends AbstractContainerMenu {
    public final Level world;
    public final Player entity;

    public EmoteMenu(int containerId, Inventory inventory) {
        super(EpicFightAnnoyingVillagersModMenus.EMOTE.get(), containerId);
        this.world = inventory.player.level();
        this.entity = inventory.player;
    }

    public EmoteMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId, inventory);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}
