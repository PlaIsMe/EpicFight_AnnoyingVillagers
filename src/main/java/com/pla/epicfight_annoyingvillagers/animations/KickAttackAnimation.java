package com.pla.epicfight_annoyingvillagers.animations;

import java.util.*;
import javax.annotation.Nullable;

import com.pla.epicfight_annoyingvillagers.config.EpicFightAnnoyingVillagersConfig;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.Keyframe;
import yesman.epicfight.api.animation.TransformSheet;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackAnimationProperty;
import yesman.epicfight.api.animation.property.MoveCoordFunctions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.EntityState.StateFactor;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.Layer.Priority;
import yesman.epicfight.api.client.animation.property.ClientAnimationProperties;
import yesman.epicfight.api.client.animation.property.JointMask;
import yesman.epicfight.api.client.animation.property.JointMaskEntry;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.api.utils.datastruct.TypeFlexibleHashMap;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.gamerule.EpicFightGameRules;

public class KickAttackAnimation extends AttackAnimation {
    @Override
    protected void hurtCollidingEntities(LivingEntityPatch<?> attacker, float previousTime, float elapsedTime,
                                         EntityState previousState, EntityState state, Phase phase) {
        if (attacker.isLogicalClient()) {
            return;
        }

        Set<LivingEntity> previousContacts = new HashSet<>(attacker.getCurrentlyActuallyHitEntities());
        super.hurtCollidingEntities(attacker, previousTime, elapsedTime, previousState, state, phase);

        // Epic Fight counts SUCCESS and BLOCKED contacts, but excludes MISSED ones.
        // Its per-phase list prevents repeat damage on later collision ticks and resets
        // for the next phase. Run after the hit/block so its reaction cannot overwrite
        // the stamina break, and never also charge stamina from LivingHurtEvent.
        for (LivingEntity victim : new ArrayList<>(attacker.getCurrentlyActuallyHitEntities())) {
            if (previousContacts.contains(victim) || !victim.isAlive()) {
                continue;
            }
            LivingEntityPatch<?> victimPatch = EpicFightCapabilities.getEntityPatch(victim, LivingEntityPatch.class);
            EpicfightUtil.dealStaminaDamageByPercentage(
                    this.getEpicFightDamageSource(attacker, victim, phase), victimPatch,
                    EpicFightAnnoyingVillagersConfig.KICK_STAMINA_DECREASE_PERCENTAGE.get());
        }
    }

    private void init() {
        if (!this.properties.containsKey(AttackAnimationProperty.BASIS_ATTACK_SPEED)) {
            float mfloat = Float.parseFloat(String.format(Locale.US, "%.2f", 1.0F / this.getTotalTime()));
            this.addProperty(AttackAnimationProperty.BASIS_ATTACK_SPEED, mfloat);
        }

        if (FMLEnvironment.dist == Dist.CLIENT) {
            Set<JointMask> jointMaskSet = new HashSet<>();
            jointMaskSet.add(JointMask.of("Root", JointMask.KEEP_CHILD_LOCROT));
            jointMaskSet.add(JointMask.of("Torso"));
            jointMaskSet.add(JointMask.of("Chest"));
            jointMaskSet.add(JointMask.of("Head"));
            jointMaskSet.add(JointMask.of("Shoulder_R"));
            jointMaskSet.add(JointMask.of("Arm_R"));
            jointMaskSet.add(JointMask.of("Hand_R"));
            jointMaskSet.add(JointMask.of("Elbow_R"));
            jointMaskSet.add(JointMask.of("Tool_R"));
            jointMaskSet.add(JointMask.of("Shoulder_L"));
            jointMaskSet.add(JointMask.of("Arm_L"));
            jointMaskSet.add(JointMask.of("Hand_L"));
            jointMaskSet.add(JointMask.of("Elbow_L"));
            jointMaskSet.add(JointMask.of("Tool_L"));
            jointMaskSet.add(JointMask.of("Thigh_R"));
            jointMaskSet.add(JointMask.of("Leg_R"));
            jointMaskSet.add(JointMask.of("Knee_R"));
            jointMaskSet.add(JointMask.of("Thigh_L"));
            jointMaskSet.add(JointMask.of("Leg_L"));
            jointMaskSet.add(JointMask.of("Knee_L"));
            JointMaskEntry jointMaskEntry = JointMaskEntry.builder()
                    .defaultMask(JointMask.JointMaskSet.of(jointMaskSet))
                    .create();

            this.addProperty(ClientAnimationProperties.JOINT_MASK, jointMaskEntry);
        }
    }

    public KickAttackAnimation(float convertTime, float antic, float preDelay,
                               float contact, float recovery,
                               @Nullable Collider collider, Joint colliderJoint,
                               AnimationManager.AnimationAccessor<? extends KickAttackAnimation> accessor,
                               AssetAccessor<? extends Armature> armature) {
        this(convertTime, accessor, armature,
                new Phase(0.0F, antic, preDelay, contact, recovery, Float.MAX_VALUE, colliderJoint, collider));
    }

    public KickAttackAnimation(float convertTime,
                               AnimationManager.AnimationAccessor<? extends KickAttackAnimation> accessor,
                               AssetAccessor<? extends Armature> armature,
                               Phase... phases) {
        super(convertTime, accessor, armature, phases);
        init();
        this.newTimePair(0.0F, Float.MAX_VALUE);
        this.addStateRemoveOld(EntityState.TURNING_LOCKED, false);
        this.addProperty(ActionAnimationProperty.COORD_SET_BEGIN, MoveCoordFunctions.TRACE_TARGET_DISTANCE);
        this.addProperty(ActionAnimationProperty.COORD_SET_TICK, (dynamicanimation, livingentitypatch, transformsheet) -> {
            LivingEntity livingentity = livingentitypatch.getTarget();

            if (!(Boolean) dynamicanimation.getRealAnimation().get().getProperty(AttackAnimationProperty.FIXED_MOVE_DISTANCE).orElse(false) && livingentity != null) {
                TransformSheet transformsheet1 = dynamicanimation.getTransfroms().get("Root").copyAll();
                Keyframe[] akeyframe = transformsheet1.getKeyframes();
                byte b0 = 0;
                int i = transformsheet1.getKeyframes().length - 1;
                Vec3f vec3f = akeyframe[i].transform().translation();
                Vec3 vec3 = livingentitypatch.getOriginal().getEyePosition();
                Vec3 vec31 = livingentity.position();
                float f1 = Math.max((float) vec31.subtract(vec3).horizontalDistance() * 1.75F - (livingentity.getBbWidth() + livingentitypatch.getOriginal().getBbWidth()) * 0.75F, 0.0F);
                Vec3f vec3f1 = new Vec3f(vec3f.x, 0.0F, -f1);
                float f2 = Math.min(vec3f1.length() / vec3f.length(), 2.0F);

                for (int j = b0; j <= i; ++j) {
                    Vec3f vec3f2 = akeyframe[j].transform().translation();

                    vec3f2.z *= f2;
                }

                transformsheet.readFrom(transformsheet1);
            } else {
                transformsheet.readFrom(dynamicanimation.getTransfroms().get("Root"));
            }

        });
    }

    public void end(LivingEntityPatch<?> livingentitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation, boolean flag) {
        super.end(livingentitypatch, nextAnimation, flag);
        boolean flag1 = livingentitypatch.getOriginal().level().getGameRules().getRule(EpicFightGameRules.STIFF_COMBO_ATTACKS.getRuleKey()).get();

        if (!flag && !nextAnimation.get().isMainFrameAnimation() && livingentitypatch.isLogicalClient() && !flag1) {
            float f = 0.05F * this.getPlaySpeed(livingentitypatch, nextAnimation.get());

            livingentitypatch.getClientAnimator().baseLayer.copyLayerTo(livingentitypatch.getClientAnimator().baseLayer.getLayer(Priority.HIGHEST), f);
        }

    }

    public TypeFlexibleHashMap<StateFactor<?>> getStatesMap(LivingEntityPatch<?> livingentitypatch, float f) {
        TypeFlexibleHashMap<StateFactor<?>> typeflexiblehashmap = super.getStatesMap(livingentitypatch, f);

        if (!livingentitypatch.getOriginal().level().getGameRules().getRule(EpicFightGameRules.STIFF_COMBO_ATTACKS.getRuleKey()).get()) {
            typeflexiblehashmap.put((StateFactor<?>) EntityState.MOVEMENT_LOCKED, Boolean.FALSE);
        }

        return typeflexiblehashmap;
    }

    public Vec3 getCoordVector(LivingEntityPatch<?> livingentitypatch, AssetAccessor<? extends DynamicAnimation> nextAnimation) {
        Vec3 vec3 = super.getCoordVector(livingentitypatch, nextAnimation);

        if (livingentitypatch.shouldBlockMoving() && this.getProperty(ActionAnimationProperty.CANCELABLE_MOVE).orElse(false)) {
            vec3 = vec3.scale(0.0D);
        }

        return vec3;
    }

    public boolean isBasicAttackAnimation() {
        return true;
    }
}
