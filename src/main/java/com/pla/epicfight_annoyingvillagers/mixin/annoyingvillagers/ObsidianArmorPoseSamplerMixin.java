package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.rig.armor.ObsidianArmorPoseSampler;
import com.pla.annoyingvillagers.rig.armor.ObsidianArmorSkeleton;
import com.pla.annoyingvillagers.rig.pose.RigPartTransform;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

/** Keep damage volumes attached to the same joints as the dynamic armor tiles. */
@Mixin(value = ObsidianArmorPoseSampler.class, remap = false)
public abstract class ObsidianArmorPoseSamplerMixin {
    @Inject(method = "baseTransform", at = @At("HEAD"), cancellable = true)
    private static void sampleEpicFightJoint(LivingEntity wearer, ObsidianArmorSkeleton.Bone top,
                                             CallbackInfoReturnable<RigPartTransform> cir) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(wearer, LivingEntityPatch.class);
        if (patch == null || !(patch.getArmature() instanceof HumanoidArmature armature)) return;
        Joint joint = switch (top.name()) {
            case "Head" -> armature.head;
            case "Body" -> armature.chest;
            case "RightArm" -> armature.armR;
            case "LeftArm" -> armature.armL;
            default -> null;
        };
        if (joint == null) return;

        OpenMatrix4f transform = OpenMatrix4f.createRotatorDeg(180.0F, Vec3f.Y_AXIS)
                .mulBack(patch.getModelMatrix(1.0F))
                .mulBack(armature.getBoundTransformFor(patch.getAnimator().getPose(1.0F), joint))
                .mulBack(joint.getToOrigin())
                .translate(0.0F, 1.5F, 0.0F)
                .scale(new Vec3f(-1.0F, -1.0F, 1.0F))
                .translate(top.pivotX() / 16.0F, top.pivotY() / 16.0F, top.pivotZ() / 16.0F)
                .rotate(top.zRot(), Vec3f.Z_AXIS)
                .rotate(top.yRot(), Vec3f.Y_AXIS)
                .rotate(top.xRot(), Vec3f.X_AXIS);
        Vec3 origin = OpenMatrix4f.transform(transform, Vec3.ZERO);
        Vec3 x = OpenMatrix4f.transform(transform, new Vec3(1, 0, 0)).subtract(origin).normalize();
        Vec3 y = OpenMatrix4f.transform(transform, new Vec3(0, 1, 0)).subtract(origin).normalize();
        Vec3 z = OpenMatrix4f.transform(transform, new Vec3(0, 0, 1)).subtract(origin).normalize();
        cir.setReturnValue(new RigPartTransform(wearer.position().add(origin), x, y, z));
    }
}
