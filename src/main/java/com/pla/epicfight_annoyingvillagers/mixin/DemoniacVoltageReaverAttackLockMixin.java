package com.pla.epicfight_annoyingvillagers.mixin;

import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

@Mixin(value = DemoniacVoltageReaverItem.class, remap = false)
public abstract class DemoniacVoltageReaverAttackLockMixin {
    @Inject(method = "acquireSnakeProfileAttackLock", at = @At("RETURN"))
    private static void acquireSnakeAttackLock(LivingEntity livingEntity, CallbackInfo ci) {
        AdvancedMobPatch<?> patch = EpicFightCapabilities.getEntityPatch(livingEntity, AdvancedMobPatch.class);
        if (patch != null) patch.lockCombatActions(DemoniacVoltageReaverItem.class);
    }

    @Inject(method = "releaseSnakeProfileAttackLock", at = @At("HEAD"))
    private static void releaseSnakeAttackLock(LivingEntity livingEntity, CallbackInfo ci) {
        AdvancedMobPatch<?> patch = EpicFightCapabilities.getEntityPatch(livingEntity, AdvancedMobPatch.class);
        if (patch != null) patch.unlockCombatActions(DemoniacVoltageReaverItem.class);
    }
}
