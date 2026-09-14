package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.LowShadowHerobrineCloneEntity;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = LowShadowHerobrineCloneEntity.class, remap = false)
public abstract class LowShadowHerobrineCloneEntityMixin {
    @Inject(method = "playHerobrinePossessionAnimation", at = @At("HEAD"), cancellable = true)
    private void playHerobrinePossessionAnimation(CallbackInfo ci) {
        LowShadowHerobrineCloneEntity self = (LowShadowHerobrineCloneEntity) (Object) this;
        final LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null && !self.level().isClientSide()) {
            patch.playAnimationSynchronized(AVAnimations.PLAYER_HEROBRINE_POSSESSION, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "playAssistanceOrSacrificingAnimation", at = @At("HEAD"), cancellable = true)
    private void playAssistanceOrSacrificingAnimation(CallbackInfo ci) {
        LowShadowHerobrineCloneEntity self = (LowShadowHerobrineCloneEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch == null) return;

        if (!self.level().isClientSide() && !self.isDeadOrDying() && self.isAlive()) {
            if (self.isSacrificing()) {
                if (!EpicfightUtil.isPlaying(self, AVAnimations.HEROBRINE_ASSISTANCE)) {
                    patch.playAnimationSynchronized(AVAnimations.HEROBRINE_ASSISTANCE, 0.0F);
                }
            } else if (self.isHealing()) {
                if (!EpicfightUtil.isPlaying(self, AVAnimations.HEROBRINE_SACRIFICING)) {
                    patch.playAnimationSynchronized(AVAnimations.HEROBRINE_SACRIFICING, 0.0F);
                }
            }
        }
        ci.cancel();
    }

    @Inject(method = "playLowCloneEscapeAnimation", at = @At("HEAD"), cancellable = true)
    private void playLowCloneEscapeAnimation(CallbackInfo ci) {
        LowShadowHerobrineCloneEntity self = (LowShadowHerobrineCloneEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch == null) return;

        if (!self.level().isClientSide()
                && !EpicfightUtil.isPlaying(self, AVAnimations.LOW_CLONE_ESCAPE)) {
            patch.playAnimationSynchronized(AVAnimations.LOW_CLONE_ESCAPE, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "getSacrificingArmPosition", at = @At("HEAD"), cancellable = true)
    private static void getSacrificingArmPosition(Entity entity, Vec3 translation, HumanoidArm arm, CallbackInfoReturnable<Vec3> cir) {
        Joint joint = arm == HumanoidArm.RIGHT ? Armatures.BIPED.get().toolR : Armatures.BIPED.get().toolL;
        float handToTip = 0.6F;
        float yOffset = -0.6F;
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) {
            cir.setReturnValue(null);
            return;
        }

        float interpolation = 0.0F;
        OpenMatrix4f m = livingEntityPatch.getArmature()
                .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(new Vec3f((float) translation.x, (float) translation.y, (float) translation.z));
            OpenMatrix4f.mul(m, tLocal, m);
        }

        OpenMatrix4f tipOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
        OpenMatrix4f.mul(m, tipOffset, m);

        float yawRad = (float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = livingEntityPatch.getOriginal();
        cir.setReturnValue(new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0) + yOffset,
                m.m32 + base.getZ()
        ));
        return;
    }

    @Inject(method = "getHealingArmPosition", at = @At("HEAD"), cancellable = true)
    private static void getHealingArmPosition(Entity entity, Vec3 translation, HumanoidArm arm, CallbackInfoReturnable<Vec3> cir) {
        Joint joint = arm == HumanoidArm.RIGHT ? Armatures.BIPED.get().toolR : Armatures.BIPED.get().toolL;
        float handToTip = 1.2F;
        float yOffset = -1.0F;
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) {
            cir.setReturnValue(null);
            return;
        }

        float interpolation = 0.0F;
        OpenMatrix4f m = livingEntityPatch.getArmature()
                .getBoundTransformFor(livingEntityPatch.getAnimator().getPose(interpolation), joint);

        if (translation != null) {
            OpenMatrix4f tLocal = new OpenMatrix4f().translate(new Vec3f((float) translation.x, (float) translation.y, (float) translation.z));
            OpenMatrix4f.mul(m, tLocal, m);
        }

        OpenMatrix4f tipOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
        OpenMatrix4f.mul(m, tipOffset, m);

        float yawRad = (float) -Math.toRadians(livingEntityPatch.getOriginal().yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, m, m);

        LivingEntity base = livingEntityPatch.getOriginal();
        cir.setReturnValue(new Vec3(
                m.m30 + base.getX(),
                m.m31 + (base.getY() + (entity.getBbHeight() / 1.8) - 1.0) + yOffset,
                m.m32 + base.getZ()
        ));
        return;
    }
}
