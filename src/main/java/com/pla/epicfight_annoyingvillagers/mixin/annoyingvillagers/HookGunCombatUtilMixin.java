package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.util.HookGunCombatUtil;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = HookGunCombatUtil.class, remap = false)
public abstract class HookGunCombatUtilMixin {
    @Inject(method = "playHookGunAnimation", at = @At("HEAD"), cancellable = true)
    private static void playHookGunAnimation(LivingEntity entity, CallbackInfo ci) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch != null && !entity.level().isClientSide()) {
            patch.playAnimationSynchronized(AVAnimations.HOOK_GUN, 0.0F);
        }
        ci.cancel();
    }
}
