package com.pla.epicfight_annoyingvillagers.compat.epicfight;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

public final class EpicFightCloneAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> DIG_MAINHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> USE_MAINHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EAT_MAINHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EAT_OFFHAND;

    private EpicFightCloneAnimations() {
    }

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        DIG_MAINHAND = builder.nextAccessor("biped/living/dig_mainhand",
                accessor -> new StaticAnimation(0.1F, true, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        USE_MAINHAND = builder.nextAccessor("biped/living/use_mainhand",
                accessor -> new StaticAnimation(0.1F, false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        EAT_MAINHAND = builder.nextAccessor("biped/living/eat_mainhand",
                accessor -> new StaticAnimation(0.1F, true, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        EAT_OFFHAND = builder.nextAccessor("biped/living/eat_offhand",
                accessor -> new StaticAnimation(0.1F, true, accessor, humanoidArmature)
                        .addProperty(StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
    }
}
