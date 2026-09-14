package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.util.BurstProtectionUtil;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = BurstProtectionUtil.class, remap = false)
public abstract class BurstProtectionUtilMixin {
    @Inject(method = "shouldIgnoreBurstProtection", at = @At("HEAD"), cancellable = true)
    private static void shouldIgnoreBurstProtection(LivingEntity self, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch == null) {
            cir.setReturnValue(false);
            return;
        }

        var player = patch.getAnimator().getPlayerFor(null);
        if (player == null) {
            cir.setReturnValue(false);
            return;
        }

        AssetAccessor<? extends StaticAnimation> anim = player.getRealAnimation();
        cir.setReturnValue(EpicfightUtil.isDamagableHitAnimation(anim, patch));
    }
}
