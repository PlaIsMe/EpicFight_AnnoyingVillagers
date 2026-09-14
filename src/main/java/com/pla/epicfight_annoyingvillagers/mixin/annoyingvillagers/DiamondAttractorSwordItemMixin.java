package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.item.DiamondAttractorSwordItem;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import java.util.Objects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = DiamondAttractorSwordItem.class, remap = false)
public abstract class DiamondAttractorSwordItemMixin {
    @Inject(method = "knockDownFromPulling", at = @At("HEAD"), cancellable = true)
    private static void knockDownFromPulling(LivingEntity target, CallbackInfo ci) {
        LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (targetPatch != null) {
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(targetPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (!EpicfightUtil.isLongHitAnimation(dynamicAnimation, targetPatch)) {
                targetPatch.playAnimationSynchronized(AVAnimations.KNOCKDOWN_FORWARD, 0.0F);
            }
        }
        ci.cancel();
    }
}
