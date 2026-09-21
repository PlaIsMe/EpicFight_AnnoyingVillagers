package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.HerobrineGregEntity;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = HerobrineGregEntity.class, remap = false)
public abstract class HerobrineGregEntityMixin {
    @Inject(method = {"summonHerobrines", "summonHerobrinesAndEscape"}, at = @At("HEAD"))
    private void playPortalSummonAnimation(CallbackInfo ci) {
        this.epicFightAnnoyingVillagers$playAnimation(AVAnimations.PORTAL_SUMMON);
    }

    @Inject(method = "playPortalSupportAnimation", at = @At("HEAD"))
    private void playPortalSupportAnimation(RigAnimationId animationId, LivingEntity lookTarget, CallbackInfo ci) {
        if (animationId == RigAnimationId.PORTAL_SUMMON) {
            this.epicFightAnnoyingVillagers$playAnimation(AVAnimations.PORTAL_SUMMON);
        } else if (animationId == RigAnimationId.POINT_LEFT_HAND_TOWARD) {
            this.epicFightAnnoyingVillagers$playAnimation(AVAnimations.POINT_LEFT_HAND_TOWARD);
        }
    }

    @Unique
    private void epicFightAnnoyingVillagers$playAnimation(AssetAccessor<? extends StaticAnimation> animation) {
        HerobrineGregEntity self = (HerobrineGregEntity) (Object) this;
        if (!self.level().isClientSide()) {
            LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
            if (patch != null) {
                patch.playAnimationSynchronized(animation, 0.0F);
            }
        }
    }
}
