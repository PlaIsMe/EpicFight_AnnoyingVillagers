package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

@Mixin(value = HerobrineDragonEntity.class, remap = false)
public abstract class HerobrineDragonEntityMixin {
    @Inject(method = "isAllowedEpicFightHeldCategory", at = @At("HEAD"), cancellable = true)
    private static void isAllowedEpicFightHeldCategory(Player p, CallbackInfoReturnable<Boolean> cir) {
        ItemStack main = p.getMainHandItem();
        CapabilityItem cap = EpicFightCapabilities.getItemStackCapability(main);
        if (!(cap instanceof WeaponCapability weaponCap)) {
            cir.setReturnValue(true);
            return;
        }

        var cat = weaponCap.getWeaponCategory();
        cir.setReturnValue(cat == CapabilityItem.WeaponCategories.BOW
                || cat == CapabilityItem.WeaponCategories.CROSSBOW
                || cat == CapabilityItem.WeaponCategories.NOT_WEAPON);
        return;
    }
}
