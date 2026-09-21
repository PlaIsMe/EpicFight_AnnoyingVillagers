package com.pla.epicfight_annoyingvillagers.gameasset;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationManager.AnimationBuilder;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.registry.entries.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.registry.entries.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

// Unfinished epicfight awaken cloning
// Check original epicfight awaken code at reference epicfight_awaken-1.2.7.3-forge-1.20.1
// Please clone using animations from epicfight awaken, remove unsuded from AVWeaponCapabilityPresets and AVSkills
// Remember copying animations from animodel of EpicFightAwaken mod into this epicfight_annoyingvillagers animodel
// Try to keep whole logic from epicfight awaken mod while cloning animation,
// this class removed many thing from original clone I suggest remove all and replace with new one
// Remember adding new line follow the format in this file like new line at
// .addEvents( AnimationEvent.InTimeEvent .addProperty .newTimePair
public class AnimsEpicFightAwaken {
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_AUTO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_AUTO_4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_HEAVY_AUTO_1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_HEAVY_AUTO_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_HEAVY_AUTO_3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_HEAVY_AUTO_4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_DASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_NIGHT_FALL;
    public static AnimationManager.AnimationAccessor<AttackAnimation> DP_DUSK_REAVER_2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> HOOK_SLASH_GROUND;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_AUTO4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_AUTO5;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_HEAVY_AUTO1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_HEAVY_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_HEAVY_AUTO4;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_HEAVY_AUTO5;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_DUAL_AUTO1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_DUAL_AUTO2;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_DUAL_AUTO3;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_AIR_SLASH_LIGHT;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_DASH_HEAVY;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_DUAL_DASH_LIGHT;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_DODGE_SLASH1;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_DODGE_PURSUIT;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_DUAL_DODGE_SLASH;
    public static AnimationManager.AnimationAccessor<AttackAnimation> STRAIGHTSWORD_DUAL_DODGE_PURSUIT;

    public static void build(AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        DP_AUTO_1 = builder.nextAccessor("biped/epicfight_awaken/darknight_pursuiters/dp_auto_1",
                (animationaccessor) -> (new AttackAnimation(0.15F, animationaccessor, Armatures.BIPED, new AttackAnimation.Phase(0.0F, 0.33F, 0.33F, 0.43F, 0.53F, 0.53F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new AttackAnimation.Phase(0.53F, 0.53F, 0.53F, 0.63F, 0.8F, 0.8F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new AttackAnimation.Phase(0.8F, 0.8F, 0.8F, 0.93F, 1.5F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 1.16F)
                        .addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false)
                        .newTimePair(0.43F, 10.0F));
        DP_AUTO_2 = builder.nextAccessor("biped/epicfight_awaken/darknight_pursuiters/dp_auto_2",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.23F, 0.23F, 0.38F, 0.38F, 0.38F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null),
                        new AttackAnimation.Phase(0.38F, 0.38F, 0.38F, 0.5F, 1.33F, Float.MAX_VALUE, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.56F).addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 0.83F).addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false)
                        .newTimePair(0.38F, 10.0F));
        DP_AUTO_3 = builder.nextAccessor("biped/epicfight_awaken/darknight_pursuiters/dp_auto_3",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.3F, 0.3F, 0.4F, 0.5F, 0.5F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                        new AttackAnimation.Phase(0.5F, 0.5F, 0.5F, 0.67F, 1.43F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.7F).addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));
        DP_AUTO_4 = builder.nextAccessor("biped/epicfight_awaken/darknight_pursuiters/dp_auto_4",
                (animationaccessor) -> (new AttackAnimation(0.2F, animationaccessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.3F, 0.3F, 0.46F, 0.7F, 0.7F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null),
                        new AttackAnimation.Phase(0.7F, 0.7F, 0.7F, 0.8F, 0.8F, 0.8F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.25F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.25F)),
                        new AttackAnimation.Phase(0.8F, 0.8F, 0.8F, 0.9F, 2.06F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 1.0F).addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));
        DP_HEAVY_AUTO_1 = builder.nextAccessor("biped/epicfight_awaken/darknight_pursuiters/dp_heavy_auto_1",
                (animationaccessor) -> (new AttackAnimation(0.15F, animationaccessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.6F, 0.6F, 0.7F, 0.7F, 0.7F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.65F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F)),
                        new AttackAnimation.Phase(0.7F, 0.7F, 0.7F, 0.8F, 1.16F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.65F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.8F).addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 0.9F).addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));
        DP_HEAVY_AUTO_2 = builder.nextAccessor("biped/epicfight_awaken/darknight_pursuiters/dp_heavy_auto_2",
                (animationaccessor) -> (new AttackAnimation(0.1F, animationaccessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.4F, 0.4F, 0.56F, 0.56F, 0.56F, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.85F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F)),
                        new AttackAnimation.Phase(0.56F, 0.8F, 0.8F, 1.0F, 1.67F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.85F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(2.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 1.08F).addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 1.16F).addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));
        DP_HEAVY_AUTO_3 = builder.nextAccessor("biped/epicfight_awaken/darknight_pursuiters/dp_heavy_auto_3",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.5F, 0.5F, 0.63F, 1.5F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(3.8F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(4.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(60.0F))))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.66F).addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 0.76F).addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));
        DP_HEAVY_AUTO_4 = builder.nextAccessor("biped/epicfight_awaken/darknight_pursuiters/dp_heavy_auto_4",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.5F, 0.5F, 0.67F, 1.3F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolR, null), AttackAnimation.JointColliderPair.of(Armatures.BIPED.get().toolL, null))
                                .addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(4.5F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(5.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(80.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                                .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL)))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .newTimePair(0.0F, 0.73F).addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .newTimePair(0.0F, 0.83F).addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));
        DP_DASH = builder.nextAccessor("biped/epicfight_awaken/darknight_pursuiters/dp_dash",
                (animationaccessor) -> (new AttackAnimation(0.05F, animationaccessor, Armatures.BIPED,
                        new AttackAnimation.Phase(0.0F, 0.4F, 0.4F, 0.56F, 0.7F, 0.7F, InteractionHand.MAIN_HAND, Armatures.BIPED.get().toolR, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(1.8F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F)),
                        new AttackAnimation.Phase(0.7F, 0.7F, 0.7F, 0.76F, 2.26F, Float.MAX_VALUE, InteractionHand.OFF_HAND, Armatures.BIPED.get().toolL, null)
                                .addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(2.0F))
                                .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(1.8F))))
                        .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.2F)
                        .addProperty(AnimationProperty.AttackAnimationProperty.REACH, 0.3F)
                        .newTimePair(0.0F, 0.8F).addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                        .addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false));

        STRAIGHTSWORD_AUTO3 = single(builder, "straightsword/straightsword_auto3", 0.15F, 0.0F, 0.23F, 0.4F, 0.77F, 1.1F, 1.0F, 0.56F, 0.67F, false);
        STRAIGHTSWORD_AUTO4 = single(builder, "straightsword/straightsword_auto4", 0.15F, 0.0F, 0.2F, 0.33F, 0.73F, 1.15F, 1.0F, 0.36F, 0.63F, false);
        STRAIGHTSWORD_AUTO5 = single(builder, "straightsword/straightsword_auto5", 0.15F, 0.0F, 0.13F, 0.4F, 0.7F, 1.2F, 1.0F, 0.45F, 0.6F, true);
        STRAIGHTSWORD_HEAVY_AUTO1 = single(builder, "straightsword/straightsword_heavy_auto1", 0.15F, 0.0F, 0.23F, 0.4F, 0.75F, 1.0F, 1.0F, 0.65F, 0.75F, false);
        STRAIGHTSWORD_HEAVY_AUTO3 = single(builder, "straightsword/straightsword_heavy_auto3", 0.15F, 0.0F, 0.13F, 0.33F, 0.8F, 1.2F, 1.0F, 0.5F, 0.67F, false);
        STRAIGHTSWORD_HEAVY_AUTO4 = single(builder, "straightsword/straightsword_heavy_auto4", 0.15F, 0.0F, 0.26F, 0.53F, 0.85F, 1.3F, 1.0F, 0.6F, 0.7F, false);
        STRAIGHTSWORD_HEAVY_AUTO5 = single(builder, "straightsword/straightsword_heavy_auto5", 0.15F, 0.0F, 0.36F, 0.5F, 0.85F, 1.4F, 1.0F, 0.6F, 0.7F, true);
        STRAIGHTSWORD_AIR_SLASH_LIGHT = single(builder, "straightsword/straightsword_airslash_light", 0.1F, 0.0F, 0.1F, 0.26F, 0.66F, 1.25F, 1.6F, 0.5F, 0.56F, false);
        STRAIGHTSWORD_DASH_HEAVY = single(builder, "straightsword/straightsword_dash_heavy", 0.1F, 0.5F, 0.6F, 0.73F, 1.4F, 1.35F, 1.75F, 1.16F, 1.3F, true);
        STRAIGHTSWORD_DODGE_SLASH1 = single(builder, "straightsword/straightsword_dodgeslash_1", 0.1F, 0.0F, 0.13F, 0.33F, 1.16F, 0.5F, 1.0F, 0.83F, 1.0F, false);
        STRAIGHTSWORD_DODGE_PURSUIT = single(builder, "straightsword/straightsword_dodgepursuit", 0.1F, 0.5F, 0.53F, 0.67F, 1.3F, 1.1F, 2.0F, 1.16F, 1.2F, true);

        STRAIGHTSWORD_DUAL_AUTO1 = dual(builder, "straightsword/straightsword_dual_auto1", 0.15F, 0.8F, 1.0F, 0.7F, 1.0F,
                new AttackAnimation.Phase(0.0F, 0.33F, 0.46F, 0.46F, 0.46F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null),
                new AttackAnimation.Phase(0.46F, 0.53F, 0.7F, 1.33F, 1.33F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null));
        STRAIGHTSWORD_DUAL_AUTO2 = dual(builder, "straightsword/straightsword_dual_auto2", 0.05F, 0.9F, 1.0F, 0.83F, 1.0F,
                new AttackAnimation.Phase(0.0F, 0.0F, 0.56F, 0.7F, 1.33F, 1.33F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD),
                new AttackAnimation.Phase(0.0F, 0.0F, 0.56F, 0.7F, 1.33F, 1.33F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.HOLD));
        STRAIGHTSWORD_DUAL_AUTO3 = dual(builder, "straightsword/straightsword_dual_auto3", 0.15F, 1.0F, 1.0F, 0.83F, 1.0F,
                new AttackAnimation.Phase(0.0F, 0.0F, 0.46F, 0.63F, 1.33F, 1.33F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null),
                new AttackAnimation.Phase(0.0F, 0.0F, 0.46F, 0.63F, 1.33F, 1.33F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null));
        STRAIGHTSWORD_DUAL_DASH_LIGHT = dual(builder, "straightsword/straightsword_dual_dash_light", 0.1F, 1.15F, 1.5F, 1.23F, 1.33F,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.43F, 0.63F, 0.63F, 0.63F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null),
                new AttackAnimation.Phase(0.63F, 0.1F, 0.7F, 0.9F, 1.5F, 1.5F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null));
        STRAIGHTSWORD_DUAL_DODGE_SLASH = dual(builder, "straightsword/straightsword_dual_dodgeslash", 0.1F, 0.5F, 1.0F, 0.8F, 1.0F,
                new AttackAnimation.Phase(0.0F, 0.0F, 0.36F, 0.5F, 1.33F, 1.33F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null),
                new AttackAnimation.Phase(0.0F, 0.0F, 0.36F, 0.5F, 1.33F, 1.33F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null));
        STRAIGHTSWORD_DUAL_DODGE_PURSUIT = dualFinisher(builder, "straightsword/straightsword_dual_dodgepursuit", 0.1F, 1.1F, 2.0F, 1.16F, 0.5F,
                new AttackAnimation.Phase(0.0F, 0.0F, 0.5F, 0.7F, 1.33F, 1.33F, InteractionHand.MAIN_HAND, humanoidArmature.get().toolR, null),
                new AttackAnimation.Phase(0.0F, 0.0F, 0.5F, 0.7F, 1.33F, 1.33F, InteractionHand.OFF_HAND, humanoidArmature.get().toolL, null));

        DP_NIGHT_FALL = dualFinisher(builder, "darknight_pursuiters/dp_night_fall", 0.05F, 1.8F, 1.5F, 0.75F, 0.75F,
                new AttackAnimation.Phase(0.0F, 0.1F, 0.1F, 0.18F, 0.28F, 0.28F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolR, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolL, null)),
                new AttackAnimation.Phase(0.28F, 0.43F, 0.43F, 0.567F, 1.3F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolR, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolL, null)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG));
        DP_DUSK_REAVER_2 = dualFinisher(builder, "darknight_pursuiters/dp_dusk_reaver_2", 0.05F, 2.4F, 2.4F, 0.67F, 0.67F,
                new AttackAnimation.Phase(0.0F, 0.33F, 0.23F, 0.33F, 2.0F, Float.MAX_VALUE, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolR, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolL, null)).addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG));
        HOOK_SLASH_GROUND = dual(builder, "darknight_pursuiters/hook_slash_ground", 0.15F, 1.25F, 1.0F, 0.66F, 1.33F,
                new AttackAnimation.Phase(0.0F, 0.0F, 0.1F, 0.2F, 0.2F, 0.2F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolR, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolL, null)),
                new AttackAnimation.Phase(0.2F, 0.0F, 0.2F, 0.3F, 0.3F, 0.3F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolR, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolL, null)),
                new AttackAnimation.Phase(0.3F, 0.0F, 0.3F, 0.36F, 0.36F, 0.36F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolR, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolL, null)),
                new AttackAnimation.Phase(0.36F, 0.0F, 0.46F, 0.56F, 1.33F, 1.33F, InteractionHand.MAIN_HAND, AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolR, null), AttackAnimation.JointColliderPair.of(humanoidArmature.get().toolL, null)));
    }

    private static AnimationManager.AnimationAccessor<AttackAnimation> single(AnimationBuilder builder, String path,
            float convertTime, float antic, float preDelay, float contact, float recovery, float damage, float impact,
            float comboLock, float skillLock, boolean finisher) {
        return builder.nextAccessor("biped/epicfight_awaken/" + path, accessor -> {
            AttackAnimation animation = new AttackAnimation(convertTime, antic, preDelay, contact, recovery, null,
                    Armatures.BIPED.get().toolR, accessor, Armatures.BIPED);
            animation.addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);
            animation.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(damage));
            animation.addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(impact));
            if (finisher) {
                animation.addProperty(AnimationProperty.AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                        .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL);
            }
            return lockActions(animation, comboLock, skillLock);
        });
    }

    private static AnimationManager.AnimationAccessor<AttackAnimation> dual(AnimationBuilder builder, String path,
            float convertTime, float damage, float impact, float comboLock, float skillLock, AttackAnimation.Phase... phases) {
        return builder.nextAccessor("biped/epicfight_awaken/" + path, accessor -> {
            for (AttackAnimation.Phase phase : phases) {
                phase.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(damage))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(impact));
            }
            AttackAnimation animation = new AttackAnimation(convertTime, accessor, Armatures.BIPED, phases)
                    .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 1.6F);
            return lockActions(animation, comboLock, skillLock);
        });
    }

    private static AnimationManager.AnimationAccessor<AttackAnimation> dualFinisher(AnimationBuilder builder, String path,
            float convertTime, float damage, float impact, float comboLock, float skillLock, AttackAnimation.Phase... phases) {
        return builder.nextAccessor("biped/epicfight_awaken/" + path, accessor -> {
            for (AttackAnimation.Phase phase : phases) {
                phase.addProperty(AnimationProperty.AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.multiplier(damage))
                        .addProperty(AnimationProperty.AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(impact))
                        .addProperty(AnimationProperty.AttackPhaseProperty.ARMOR_NEGATION_MODIFIER, ValueModifier.adder(50.0F));
            }
            phases[phases.length - 1]
                    .addProperty(AnimationProperty.AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLADE_RUSH_FINISHER.get())
                    .addProperty(AnimationProperty.AttackPhaseProperty.PARTICLE, EpicFightParticles.BLADE_RUSH_SKILL);
            AttackAnimation animation = new AttackAnimation(convertTime, accessor, Armatures.BIPED, phases)
                    .addProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED, 2.0F);
            return lockActions(animation, comboLock, skillLock);
        });
    }

    private static AttackAnimation lockActions(AttackAnimation animation, float comboLock, float skillLock) {
        return animation.newTimePair(0.0F, comboLock)
                .addStateRemoveOld(EntityState.COMBO_ATTACKS_DOABLE, false)
                .newTimePair(0.0F, skillLock)
                .addStateRemoveOld(EntityState.SKILL_EXECUTABLE, false);
    }
}
