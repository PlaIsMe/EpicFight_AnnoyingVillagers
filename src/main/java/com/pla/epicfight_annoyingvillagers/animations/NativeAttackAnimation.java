package com.pla.epicfight_annoyingvillagers.animations;

import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.ComboAttackAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

/**
 * Native Epic Fight replacement for the small subset of Avalon's attack
 * animation API previously used by this mod.
 */
public class NativeAttackAnimation extends ComboAttackAnimation {
    private final float speedMultiplier;

    public NativeAttackAnimation(float convertTime,
                                 AnimationManager.AnimationAccessor<? extends ComboAttackAnimation> accessor,
                                 AssetAccessor<? extends Armature> armature,
                                 float speedMultiplier,
                                 float damageMultiplier,
                                 NativePhase... phases) {
        super(convertTime, accessor, armature, phases);
        this.speedMultiplier = speedMultiplier;

        for (NativePhase phase : phases) {
            phase.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER,
                    ValueModifier.multiplier(phase.damageMultiplier * damageMultiplier));
            phase.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER,
                    ValueModifier.multiplier(phase.impactMultiplier));
        }

        addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false);
    }

    @Override
    public float getPlaySpeed(LivingEntityPatch<?> entityPatch,
                              yesman.epicfight.api.animation.types.DynamicAnimation animation) {
        return super.getPlaySpeed(entityPatch, animation) * this.speedMultiplier;
    }

    public static class NativePhase extends AttackAnimation.Phase {
        private final float damageMultiplier;
        private final float impactMultiplier;

        public NativePhase(float start, float antic, float preDelay, float contact,
                           float recovery, float end, InteractionHand hand,
                           float damageMultiplier, float impactMultiplier,
                           Joint joint, Collider collider) {
            super(start, antic, preDelay, contact, recovery, end, hand, joint, collider);
            this.damageMultiplier = damageMultiplier;
            this.impactMultiplier = impactMultiplier;
        }
    }
}
