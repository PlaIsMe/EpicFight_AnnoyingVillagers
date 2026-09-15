package com.pla.epicfight_annoyingvillagers.gameasset;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty.ActionAnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.model.armature.HumanoidArmature;

@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AnimsEmote {
    // Animation from Pugilist Steve
    public static AnimationManager.AnimationAccessor<StaticAnimation> LAY_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> PUSH_UP_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SIT_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SLIGHT_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DEATH_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FUNNY_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ATTENTION_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FLAPPING_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FUN_JUMP_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> JUMP_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> PRONE_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SALUTE_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_2;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_3;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_5;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_6;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_7;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_8;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_9;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_10;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_11;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DANCE_12;
    public static AnimationManager.AnimationAccessor<StaticAnimation> NL_BOW_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> NL_BOWING_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> NL_GOAD_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> NL_LOL_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> NL_WAVE_EMOTE;

    // Animation from Epic Emotes
    public static AnimationManager.AnimationAccessor<StaticAnimation> LAY_RELAX_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ONE_ARM_LAY_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SALUTE_LEFT_HAND_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SIT_NO_WEAPON_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SORROW_EMOTE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> SURRENDER_EMOTE;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;

        // Animation from Epic Emotes
        AnimsEmote.LAY_RELAX_EMOTE = builder.nextAccessor("biped/emote/lay_relax_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.ONE_ARM_LAY_EMOTE = builder.nextAccessor("biped/emote/one_arm_lay_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SALUTE_LEFT_HAND_EMOTE = builder.nextAccessor("biped/emote/salute_left_hand_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SIT_NO_WEAPON_EMOTE = builder.nextAccessor("biped/emote/sit_no_weapon_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SORROW_EMOTE = builder.nextAccessor("biped/emote/sorrow_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SURRENDER_EMOTE = builder.nextAccessor("biped/emote/surrender_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));

        // Animation from Pugilist Steve
        AnimsEmote.LAY_EMOTE = builder.nextAccessor("biped/emote/lay_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.PUSH_UP_EMOTE = builder.nextAccessor("biped/emote/push_up_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SIT_EMOTE = builder.nextAccessor("biped/emote/sit_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SLIGHT_EMOTE = builder.nextAccessor("biped/emote/slight_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DEATH_EMOTE = builder.nextAccessor("biped/emote/death_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.FUNNY_EMOTE = builder.nextAccessor("biped/emote/funny_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.ATTENTION_EMOTE = builder.nextAccessor("biped/emote/attention_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.FLAPPING_EMOTE = builder.nextAccessor("biped/emote/flapping_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.FUN_JUMP_EMOTE = builder.nextAccessor("biped/emote/fun_jump_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.JUMP_EMOTE = builder.nextAccessor("biped/emote/jump_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.PRONE_EMOTE = builder.nextAccessor("biped/emote/prone_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.SALUTE_EMOTE = builder.nextAccessor("biped/emote/salute_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_1 = builder.nextAccessor("biped/emote/dance_1",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_2 = builder.nextAccessor("biped/emote/dance_2",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_3 = builder.nextAccessor("biped/emote/dance_3",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_4 = builder.nextAccessor("biped/emote/dance_4",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_5 = builder.nextAccessor("biped/emote/dance_5",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_6 = builder.nextAccessor("biped/emote/dance_6",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_7 = builder.nextAccessor("biped/emote/dance_7",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_8 = builder.nextAccessor("biped/emote/dance_8",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_9 = builder.nextAccessor("biped/emote/dance_9",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_10 = builder.nextAccessor("biped/emote/dance_10",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_11 = builder.nextAccessor("biped/emote/dance_11",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.DANCE_12 = builder.nextAccessor("biped/emote/piglin_celebrate_emote",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.NL_BOW_EMOTE = builder.nextAccessor("biped/emote/nl_bow_emote",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.NL_BOWING_EMOTE = builder.nextAccessor("biped/emote/nl_bowing_emote",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.NL_GOAD_EMOTE = builder.nextAccessor("biped/emote/nl_goad_emote",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.NL_LOL_EMOTE = builder.nextAccessor("biped/emote/nl_lol_emote",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
        AnimsEmote.NL_WAVE_EMOTE = builder.nextAccessor("biped/emote/nl_wave_emote",
                (accessor) -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addProperty(ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(ActionAnimationProperty.STOP_MOVEMENT, true));
    }
}
