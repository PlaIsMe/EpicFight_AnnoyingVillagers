package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.rig.RigStunController;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = RigStunController.class, remap = false)
public abstract class RigStunControllerMixin {
    @Inject(method = "supports", at = @At("HEAD"), cancellable = true)
    private static void useEpicFightStuns(Mob mob, CallbackInfoReturnable<Boolean> cir) {
        // EF owns hurt animations and recovery. Rig stuns would also set NoAI.
        // Leave unpatched entities on AV's normal stun backend.
        if (mob != null && EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class) != null) {
            cir.setReturnValue(false);
        }
    }
}
