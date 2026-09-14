package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.ShockWaveBlockEntity;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsLegendarySword;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import java.util.Objects;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = ShockWaveBlockEntity.class, remap = false)
public abstract class ShockWaveBlockEntityMixin {
    @Inject(method = "playTriedAnimation", at = @At("HEAD"), cancellable = true)
    private static void playTriedAnimation(Entity target, CallbackInfo ci) {
        LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (targetPatch != null) {
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(targetPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (dynamicAnimation != null && !EpicfightUtil.isLongHitAnimation(dynamicAnimation, targetPatch)) {
                targetPatch.playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_KNOCKDOWN, 0.0F);
            }
        }
        ci.cancel();
    }
}
