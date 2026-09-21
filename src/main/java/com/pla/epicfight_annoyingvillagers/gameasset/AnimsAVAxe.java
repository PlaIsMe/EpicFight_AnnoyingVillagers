package com.pla.epicfight_annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.item.EarthAxeItem;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.animations.AwakenAttackAnimation;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.api.utils.HitEntityList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.registry.entries.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.registry.entries.EpicFightParticles;
import yesman.epicfight.world.damagesource.EpicFightDamageTypeTags;
import yesman.epicfight.world.damagesource.StunType;

import java.util.Set;

public class AnimsAVAxe {
    public static AnimationManager.AnimationAccessor<StaticAnimation> AV_AXE_IDLE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> AV_AXE_WALK;
    public static AnimationManager.AnimationAccessor<MovementAnimation> AV_AXE_RUN;
    public static AnimationManager.AnimationAccessor<AwakenAttackAnimation> AV_AXE_AUTO1;
    public static AnimationManager.AnimationAccessor<AwakenAttackAnimation> AV_AXE_AUTO2;
    public static AnimationManager.AnimationAccessor<AwakenAttackAnimation> AV_AXE_AUTO3;
    public static AnimationManager.AnimationAccessor<AwakenAttackAnimation> AV_AXE_AUTO4;
    public static AnimationManager.AnimationAccessor<AwakenAttackAnimation> AV_AXE_AUTO5;
    public static AnimationManager.AnimationAccessor<AwakenAttackAnimation> AV_AXE_DASH;
    public static AnimationManager.AnimationAccessor<AwakenAttackAnimation> AV_AXE_AIRSLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> AV_AXE_INNATE;
    public static AnimationManager.AnimationAccessor<AttackAnimation> EARTH_AXE_INNATE;
    public static AnimationManager.AnimationAccessor<ComboAttackAnimation> EARTH_AXE_SPECIAL;
    public static AnimationManager.AnimationAccessor<ComboAttackAnimation> AV_AXE_DUAL_INNATE;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        AV_AXE_IDLE = builder.nextAccessor("biped/av_axe/av_axe_idle",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));

        AV_AXE_WALK = builder.nextAccessor("biped/av_axe/av_axe_walk",
                accessor -> new MovementAnimation(0.1f, true, accessor, Armatures.BIPED));

        AV_AXE_RUN = builder.nextAccessor("biped/av_axe/av_axe_run",
                accessor -> new MovementAnimation(0.2f, true, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.8F));

        AV_AXE_AUTO1 = builder.nextAccessor("biped/av_axe/av_axe_auto1",
                (accessor) -> (AwakenAttackAnimation) (new AwakenAttackAnimation(
                        0.15F, 0.0F, 0.23F, 0.4F, 0.77F, null,
                        Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.05F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .newTimePair(0.0F, 0.03F)
                        .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.23F, Float.MAX_VALUE)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.0F, 0.46F)
                        .addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 0.57F)
                        .addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));

        AV_AXE_AUTO2 = builder.nextAccessor("biped/av_axe/av_axe_auto2",
                (accessor) -> (AwakenAttackAnimation) (new AwakenAttackAnimation(
                        0.15F, 0.0F, 0.28F, 0.38F, 0.68F, null,
                        Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .newTimePair(0.0F, 0.08F)
                        .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.28F, Float.MAX_VALUE)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.0F, 0.48F)
                        .addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 0.58F)
                        .addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));

        AV_AXE_AUTO3 = builder.nextAccessor("biped/av_axe/av_axe_auto3",
                (accessor) -> (AwakenAttackAnimation) (new AwakenAttackAnimation(
                        0.15F, 0.0F, 0.26F, 0.53F, 0.85F, null,
                        Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .newTimePair(0.0F, 0.06F)
                        .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.26F, Float.MAX_VALUE)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.0F, 0.6F)
                        .addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 0.7F)
                        .addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));

        AV_AXE_AUTO4 = builder.nextAccessor("biped/av_axe/av_axe_auto4",
                (accessor) -> (AwakenAttackAnimation) (new AwakenAttackAnimation(
                        0.15F, 0.0F, 0.23F, 0.4F, 0.75F, null,
                        Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .newTimePair(0.0F, 0.03F)
                        .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.23F, Float.MAX_VALUE)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.0F, 0.65F)
                        .addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 0.75F)
                        .addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));

        AV_AXE_AUTO5 = builder.nextAccessor("biped/av_axe/av_axe_auto5",
                (accessor) -> (AwakenAttackAnimation) (new AwakenAttackAnimation(
                        0.15F, 0.0F, 0.23F, 0.4F, 0.77F, null,
                        Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.1F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .newTimePair(0.0F, 0.03F)
                        .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.24F, Float.MAX_VALUE)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .newTimePair(0.0F, 0.56F)
                        .addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 0.67F)
                        .addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));

        AV_AXE_DASH = builder.nextAccessor("biped/av_axe/av_axe_dash",
                (accessor) -> (AwakenAttackAnimation) (new AwakenAttackAnimation(
                        0.1F, 0.63F, 0.56F, 0.73F, 1.4F, null,
                        Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.15F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.RESET_PLAYER_COMBO_COUNTER, true)
                        .newTimePair(0.0F, 1.16F)
                        .addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 1.3F)
                        .addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));

        AV_AXE_AIRSLASH = builder.nextAccessor("biped/av_axe/av_axe_airslash",
                (accessor) -> (AwakenAttackAnimation) (new AwakenAttackAnimation(
                        0.1F, 0.0F, 0.2F, 0.4F, 1.36F, null,
                        Armatures.BIPED.get().toolR, accessor, Armatures.BIPED))
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.75F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.SOURCE_TAG, Set.of(EpicFightDamageTypeTags.FINISHER))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.RESET_PLAYER_COMBO_COUNTER, true)
                        .addProperty(AnimationProperty.AttackAnimationProperty.STOP_MOVEMENT, false)
                        .newTimePair(0.0F, 1.16F)
                        .addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 1.26F)
                        .addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));

        AV_AXE_INNATE = builder.nextAccessor("biped/av_axe/av_axe_innate",
                access -> new AttackAnimation(0.2f, 0.0f, 0.9f, 1.5f, 3f, null, Armatures.BIPED.get().toolR, access, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR, 0.25f));

        EARTH_AXE_INNATE = builder.nextAccessor("biped/av_axe/earth_axe_innate",
                accessor -> new AttackAnimation(0.0F, 0.0F, 0.0F, 0.0F, Float.MAX_VALUE, null, Armatures.BIPED.get().head, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.0F)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.2F, (livingEntityPatch, self, p) -> {
                                    if (livingEntityPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                                        EarthAxeItem.summonEarthWall(serverLevel, livingEntityPatch.getOriginal());
                                    }
                                }, AnimationEvent.Side.SERVER)
                        ));

        EARTH_AXE_SPECIAL = builder.nextAccessor("biped/av_axe/earth_axe_special",
                accessor -> new ComboAttackAnimation(0.1F, accessor, humanoidArmature,
                        new AttackAnimation.Phase(0.0F, 0.05F, 0.3F, 0.4F, 1.167F, 1.65F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null),
                        new AttackAnimation.Phase(0.1F, 0.1F, 0.4F, 0.6F, 0.6F, humanoidArmature.get().toolR, null))
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_PRIORITY, HitEntityList.Priority.TARGET)
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 0.5F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(1.0F, (livingEntityPatch, staticAnimation, object) -> {
                                    Vec3 bladePos = EpicfightUtil.getJointWithTranslation(livingEntityPatch.getOriginal(), new Vec3f(0, 0, 0),
                                            Armatures.BIPED.get().toolR, 0.5F, 0.0F);
                                    if (bladePos == null) return;
                                    LivingEntity livingEntity = livingEntityPatch.getOriginal();
                                    if (livingEntity.level() instanceof ServerLevel serverLevel) {
                                        BlockPos liftPos = EarthAxeItem.findLiftableBlockUnderPoint(serverLevel, bladePos, 6, 1);
                                        if (liftPos != null) {
                                            EarthAxeItem.liftBlockAt(serverLevel, liftPos, livingEntity);
                                        }
                                    }
                                }, AnimationEvent.Side.SERVER)
                        )
        );

        AV_AXE_DUAL_INNATE = builder.nextAccessor("biped/av_axe/av_axe_dual_innate",
                accessor -> new ComboAttackAnimation(0.05F, accessor, humanoidArmature, new AttackAnimation.Phase(0.0F, 0.15F, 0.25F, 0.25F, 0.25F, humanoidArmature.get().toolR, null).addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(0.25F, 0.25F, 0.4F, 0.5F, 0.5F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(0.5F, 0.5F, 0.6F, 0.6F, 0.6F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(0.6F, 0.6F, 0.75F, 0.75F, 0.75F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(0.75F, 0.75F, 0.8F, 0.9F, 0.9F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(0.9F, 0.9F, 1.0F, 1.0F, 1.0F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.0F, 1.0F, 1.1F, 1.1F, 1.1F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.1F, 1.1F, 1.22F, 1.22F, 1.22F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.22F, 1.22F, 1.35F, 1.35F, 1.35F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.35F, 1.35F, 1.42F, 1.42F, 1.42F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.42F, 1.42F, 1.5F, 1.5F, 1.5F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.5F, 1.5F, 1.6F, 1.6F, 1.6F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.6F, 1.6F, 1.7F, 1.7F, 1.7F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.7F, 1.7F, 1.8F, 1.8F, 1.8F, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.8F, 1.8F, 1.9F, 1.9F, 1.9F, humanoidArmature.get().toolR, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(0.6F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.setter(0.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F)), new AttackAnimation.Phase(1.9F, 2.0F, 2.2F, Float.MAX_VALUE, Float.MAX_VALUE, humanoidArmature.get().toolL, null)
                        .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.3F)).addProperty(AnimationProperty.AttackPhaseProperty.MAX_STRIKES_MODIFIER, ValueModifier.setter(8.0F))
                        .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG))
                        .addProperty(AnimationProperty.AttackAnimationProperty.FIXED_MOVE_DISTANCE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addState(EntityState.SKILL_EXECUTABLE, false)
                        .addState(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, false)
                        .addState(EntityState.LOOK_TARGET, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
    }
}
