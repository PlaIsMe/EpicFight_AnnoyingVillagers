package com.pla.epicfight_annoyingvillagers.gameasset;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

public class AnimsEmote {
    // Animation from Pugilist Steve
    public static AnimationManager.AnimationAccessor<EmoteAnimation> LAY_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> PUSH_UP_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> SIT_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> SLIGHT_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DEATH_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> FUNNY_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> ATTENTION_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> FLAPPING_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> FUN_JUMP_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> JUMP_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> PRONE_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> SALUTE_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_1;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_2;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_3;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_4;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_5;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_6;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_7;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_8;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_9;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_10;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_11;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> DANCE_12;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> NL_BOW_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> NL_BOWING_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> NL_GOAD_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> NL_LOL_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> NL_WAVE_EMOTE;

    // Animation from Epic Emotes
    public static AnimationManager.AnimationAccessor<EmoteAnimation> LAY_RELAX_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> ONE_ARM_LAY_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> SALUTE_LEFT_HAND_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> SIT_NO_WEAPON_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> SORROW_EMOTE;
    public static AnimationManager.AnimationAccessor<EmoteAnimation> SURRENDER_EMOTE;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        // Animation from Epic Emotes
        AnimsEmote.LAY_RELAX_EMOTE = builder.nextAccessor("biped/emote/lay_relax_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.ONE_ARM_LAY_EMOTE = builder.nextAccessor("biped/emote/one_arm_lay_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SALUTE_LEFT_HAND_EMOTE = builder.nextAccessor("biped/emote/salute_left_hand_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SIT_NO_WEAPON_EMOTE = builder.nextAccessor("biped/emote/sit_no_weapon_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SORROW_EMOTE = builder.nextAccessor("biped/emote/sorrow_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SURRENDER_EMOTE = builder.nextAccessor("biped/emote/surrender_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));

        // Animation from Pugilist Steve
        AnimsEmote.LAY_EMOTE = builder.nextAccessor("biped/emote/lay_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.PUSH_UP_EMOTE = builder.nextAccessor("biped/emote/push_up_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SIT_EMOTE = builder.nextAccessor("biped/emote/sit_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SLIGHT_EMOTE = builder.nextAccessor("biped/emote/slight_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DEATH_EMOTE = builder.nextAccessor("biped/emote/death_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.FUNNY_EMOTE = builder.nextAccessor("biped/emote/funny_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.ATTENTION_EMOTE = builder.nextAccessor("biped/emote/attention_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.FLAPPING_EMOTE = builder.nextAccessor("biped/emote/flapping_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.FUN_JUMP_EMOTE = builder.nextAccessor("biped/emote/fun_jump_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.JUMP_EMOTE = builder.nextAccessor("biped/emote/jump_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.PRONE_EMOTE = builder.nextAccessor("biped/emote/prone_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SALUTE_EMOTE = builder.nextAccessor("biped/emote/salute_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_1 = builder.nextAccessor("biped/emote/dance_1",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_2 = builder.nextAccessor("biped/emote/dance_2",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_3 = builder.nextAccessor("biped/emote/dance_3",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_4 = builder.nextAccessor("biped/emote/dance_4",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_5 = builder.nextAccessor("biped/emote/dance_5",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_6 = builder.nextAccessor("biped/emote/dance_6",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_7 = builder.nextAccessor("biped/emote/dance_7",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_8 = builder.nextAccessor("biped/emote/dance_8",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_9 = builder.nextAccessor("biped/emote/dance_9",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_10 = builder.nextAccessor("biped/emote/dance_10",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_11 = builder.nextAccessor("biped/emote/dance_11",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_12 = builder.nextAccessor("biped/emote/piglin_celebrate_emote",
                (accessor) -> new EmoteAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.NL_BOW_EMOTE = builder.nextAccessor("biped/emote/nl_bow_emote",
                (accessor) -> new EmoteAnimation(false, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.NL_BOWING_EMOTE = builder.nextAccessor("biped/emote/nl_bowing_emote",
                (accessor) -> new EmoteAnimation(false, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.NL_GOAD_EMOTE = builder.nextAccessor("biped/emote/nl_goad_emote",
                (accessor) -> new EmoteAnimation(false, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.NL_LOL_EMOTE = builder.nextAccessor("biped/emote/nl_lol_emote",
                (accessor) -> new EmoteAnimation(false, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.NL_WAVE_EMOTE = builder.nextAccessor("biped/emote/nl_wave_emote",
                (accessor) -> new EmoteAnimation(false, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
    }
}
