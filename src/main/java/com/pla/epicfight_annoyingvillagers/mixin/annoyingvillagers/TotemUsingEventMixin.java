package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.event.TotemUsingEvent;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = TotemUsingEvent.class, remap = false)
public abstract class TotemUsingEventMixin {
    @Inject(method = "playGuardBreakAttackAnimation", at = @At("HEAD"), cancellable = true)
    private static void playGuardBreakAttackAnimation(LivingEntity entity, CallbackInfo ci) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (!entity.level().isClientSide() && entity.getServer() != null && livingEntityPatch != null) {
            livingEntityPatch.playAnimationSynchronized(AVAnimations.STUN_BACK, 0.0F);
        }
        ci.cancel();
    }
}
