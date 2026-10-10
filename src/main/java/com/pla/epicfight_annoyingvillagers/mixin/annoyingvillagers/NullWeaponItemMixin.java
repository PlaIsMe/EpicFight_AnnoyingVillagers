package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.item.NullWeaponItem;
import com.pla.annoyingvillagers.util.VanillaWeaponAbilityUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NullWeaponItem.class, remap = false)
public abstract class NullWeaponItemMixin {
    @Inject(method = "inventoryTick", remap = true, at = @At("HEAD"), cancellable = true)
    private void keepEpicFightOwnedWeapons(ItemStack stack, Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (!level.isClientSide() && entity instanceof Player && !VanillaWeaponAbilityUtil.abilitiesEnabled()) {
            ci.cancel();
        }
    }
}
