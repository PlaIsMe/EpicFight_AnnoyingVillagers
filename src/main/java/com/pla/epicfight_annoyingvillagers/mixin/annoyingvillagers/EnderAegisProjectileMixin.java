package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.EnderAegisProjectile;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = EnderAegisProjectile.class, remap = false)
public abstract class EnderAegisProjectileMixin {
    @Inject(method = "playStunAnimation", at = @At("HEAD"), cancellable = true)
    private void playStunAnimation(Entity victim, CallbackInfo ci) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(victim, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                livingEntityPatch.playAnimationSynchronized(AVAnimations.SUPER_KNOCK_BACK, 0.0F);
            }
        ci.cancel();
    }
}
