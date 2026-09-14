package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.clazz.NullWeapon;
import com.pla.annoyingvillagers.item.NullWeaponItem;
import com.pla.annoyingvillagers.task.DelayedTask;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import reascer.wom.gameasset.animations.weapons.AnimsAgony;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

@Mixin(value = NullWeapon.class, remap = false)
public abstract class NullWeaponMixin {
    @Inject(method = "isAllowedHeldCategory", at = @At("HEAD"), cancellable = true)
    private static void isAllowedHeldCategory(Player p, CallbackInfoReturnable<Boolean> cir) {
        ItemStack main = p.getMainHandItem();

        if (main.getItem() instanceof NullWeaponItem) {
            cir.setReturnValue(true);
            return;
        }

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

    @Inject(method = "spinfor5seconds", at = @At("HEAD"), cancellable = true)
    private void spinfor5seconds(CallbackInfo ci) {
        NullWeapon self = (NullWeapon) (Object) this;
        final LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (livingEntityPatch != null) {
            livingEntityPatch.playAnimationSynchronized(AnimsAgony.AGONY_GUARD, 0.0F);
            new DelayedTask(100) {
                @Override
                public void run() {
                    livingEntityPatch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
                }
            };
        }
        ci.cancel();
    }
}
