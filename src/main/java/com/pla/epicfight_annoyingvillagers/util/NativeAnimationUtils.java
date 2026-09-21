package com.pla.epicfight_annoyingvillagers.util;

import com.pla.annoyingvillagers.util.ScreenShakeUtil;
import com.pla.epicfight_annoyingvillagers.animations.NativeAttackAnimation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;

/** Utilities for expressing legacy 60-FPS animation data through Epic Fight's native API. */
public final class NativeAnimationUtils {
    private static final float ANIMATION_FRAMES_PER_SECOND = 60.0F;

    private NativeAnimationUtils() {
    }

    public static NativeAttackAnimation.NativePhase createSimplePhase(
            int anticFrame, int contactFrame, int recoveryFrame,
            InteractionHand hand, float damageMultiplier, float impactMultiplier,
            Joint joint, Collider collider) {
        float antic = anticFrame / ANIMATION_FRAMES_PER_SECOND;
        float contact = contactFrame / ANIMATION_FRAMES_PER_SECOND;
        float recovery = recoveryFrame / ANIMATION_FRAMES_PER_SECOND;
        return new NativeAttackAnimation.NativePhase(
                0.0F, antic, antic, contact, recovery, Float.MAX_VALUE,
                hand, damageMultiplier, impactMultiplier, joint, collider);
    }

    public static AnimationEvent.InTimeEvent<?> simpleGroundSplit(
            int frame, double x, double y, double z, double angle, float radius, boolean unusedDamageFlag) {
        return AnimationEvent.InTimeEvent.create(frame / ANIMATION_FRAMES_PER_SECOND,
                        Animations.ReusableSources.FRACTURE_GROUND_SIMPLE, AnimationEvent.Side.SERVER)
                .params(new Vec3f((float) x, (float) y, (float) z),
                        Armatures.BIPED.get().rootJoint, (double) radius, (float) angle);
    }

    public static AnimationEvent.InTimeEvent<?> simpleCameraShake(
            int frame, int durationTicks, float intensity, float frequency, float radius) {
        return AnimationEvent.InTimeEvent.create(frame / ANIMATION_FRAMES_PER_SECOND,
                (entityPatch, animation, params) -> {
                    if (entityPatch.getOriginal().level() instanceof ServerLevel level) {
                        ScreenShakeUtil.applyScreenShake(level, entityPatch.getOriginal().position(),
                                intensity, durationTicks, Math.max(1, Math.round(radius)));
                    }
                }, AnimationEvent.Side.SERVER);
    }
}
