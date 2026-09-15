package com.pla.epicfight_annoyingvillagers.gameasset;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.epicfight_annoyingvillagers.util.GoalAnimationCompat;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.annoyingvillagers.entity.goal.HerobrineEscapeHoleGoal;
import net.minecraft.world.entity.Mob;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.particle.EpicFightParticles;

@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AVAnimations {
    public static AnimationManager.AnimationAccessor<StaticAnimation> ELITE_HOLD_WEAPON;
    public static AnimationManager.AnimationAccessor<MovementAnimation> ELITE_WALK_WEAPON;
    public static AnimationManager.AnimationAccessor<MovementAnimation> ELITE_RUN_WEAPON;
    public static AnimationManager.AnimationAccessor<StaticAnimation> FIST_GUARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> POINT_LEFT_HAND_TOWARD;
    public static AnimationManager.AnimationAccessor<StaticAnimation> POINT_LEFT_HAND_MIDDLE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> POINT_LEFT_HAND_UP;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> STUN_BACK;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> SUPER_KNOCK_BACK;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_LEFT;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_RIGHT;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> HIT_BACKWARD;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> KNOCKDOWN_FORWARD;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> KNOCKDOWN_RIGHT;
    public static AnimationManager.AnimationAccessor<KnockdownAnimation> KNOCKDOWN_LEFT;
    public static AnimationManager.AnimationAccessor<MovementAnimation> HOLD_ONEHAND_RUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HOOK_HAND_LEFT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HOOK_HAND_LEFT_TOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HOOK_HAND_RIGHT;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HOOK_HAND_RIGHT_TOP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> IDLE_BREAK;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PLACE_BLOCK;
    public static AnimationManager.AnimationAccessor<StaticAnimation> KNOCKED_ELITE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_1;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_2;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_3;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EATING_ELITE_4;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_ANIMATE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LOW_CLONE_ESCAPE;
    public static AnimationManager.AnimationAccessor<MovementAnimation> HEROBRINE_RUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> PLAYER_HEROBRINE_POSSESSION;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_SACRIFICING;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_ASSISTANCE;
    public static AnimationManager.AnimationAccessor<StaticAnimation> HEROBRINE_STAGE_CHANGE;
    public static AnimationManager.AnimationAccessor<ActionAnimation> PORTAL_SUMMON;
    public static AnimationManager.AnimationAccessor<StaticAnimation> LAYING_DEATH;
    public static AnimationManager.AnimationAccessor<LongHitAnimation> LAYING_DEATH_DEAD;
    public static AnimationManager.AnimationAccessor<ActionAnimation> HOOK_GUN;
    public static AnimationManager.AnimationAccessor<StaticAnimation> ZIPLINE;
    public static AnimationManager.AnimationAccessor<DodgeAnimation> FLY_UP;
    public static AnimationManager.AnimationAccessor<StaticAnimation> DIG_MAINHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> USE_MAINHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EAT_MAINHAND;
    public static AnimationManager.AnimationAccessor<StaticAnimation> EAT_OFFHAND;

    @SubscribeEvent
    public static void registerAnimations(AnimationManager.AnimationRegistryEvent event) {
        event.newBuilder(EpicFightAnnoyingVillagers.MODID, AVAnimations::build);
    }

    private static void build(AnimationManager.AnimationBuilder builder) {
        AnimsEnderGlaive.build(builder);
        AnimsEnderSlayerScythe.build(builder);
        AnimsDemoniacVoltageReaver.build(builder);
        AnimsObsidianSledgehammer.build(builder);
        AnimsNullWeapon.build(builder);
        AnimsObsidianWeapon.build(builder);
        AnimsLegendarySword.build(builder);
        AnimsBlueDemonTrident.build(builder);
        AnimsAVSword.build(builder);
        AnimsAVTachi.build(builder);
        AnimsAVLongsword.build(builder);
        AnimsAVGreatsword.build(builder);
        AnimsAVAxe.build(builder);
        AnimsAVSpear.build(builder);
        AnimsAVFist.build(builder);
        AnimsEnderAegis.build(builder);
        AnimsAVExecute.build(builder);
        AnimsKick.build(builder);
        AnimsEmote.build(builder);

        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        ELITE_HOLD_WEAPON = builder.nextAccessor("biped/living/elite_hold_weapon",
                accessor -> new StaticAnimation(true, accessor, Armatures.BIPED));
        ELITE_RUN_WEAPON = builder.nextAccessor("biped/living/elite_run_weapon",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        ELITE_WALK_WEAPON = builder.nextAccessor("biped/living/elite_walk_weapon",
                accessor -> new MovementAnimation(true, accessor, Armatures.BIPED));
        FIST_GUARD = builder.nextAccessor("biped/living/fist_guard",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        POINT_LEFT_HAND_TOWARD = builder.nextAccessor("biped/living/point_left_hand_toward",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.25F, (patch, self, params) ->
                                GoalAnimationCompat.castPointAction(patch.getOriginal(),
                                        RigAnimationId.POINT_LEFT_HAND_TOWARD), AnimationEvent.Side.SERVER)));
        POINT_LEFT_HAND_UP = builder.nextAccessor("biped/living/point_left_hand_up",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.25F, (patch, self, params) ->
                                GoalAnimationCompat.castPointAction(patch.getOriginal(),
                                        RigAnimationId.POINT_LEFT_HAND_UP), AnimationEvent.Side.SERVER)));
        POINT_LEFT_HAND_MIDDLE = builder.nextAccessor("biped/living/point_left_hand_middle",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addEvents(AnimationEvent.InTimeEvent.create(0.25F, (patch, self, params) ->
                                GoalAnimationCompat.castPointAction(patch.getOriginal(),
                                        RigAnimationId.POINT_LEFT_HAND_MIDDLE), AnimationEvent.Side.SERVER)));
        STUN_BACK = builder.nextAccessor("biped/living/stun_back",
                accessor -> new LongHitAnimation(0.05F, accessor, humanoidArmature));
        SUPER_KNOCK_BACK = builder.nextAccessor("biped/living/super_knock_back",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        HIT_LEFT = builder.nextAccessor("biped/living/hit_left",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature));
        HIT_RIGHT = builder.nextAccessor("biped/living/hit_right",
                accessor -> new LongHitAnimation(0.1F, accessor, humanoidArmature));
        HIT_BACKWARD = builder.nextAccessor("biped/living/hit_backward",
                accessor -> new LongHitAnimation(0.08F, accessor, humanoidArmature));
        KNOCKDOWN_FORWARD = builder.nextAccessor("biped/living/knockdown_forward",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        KNOCKDOWN_RIGHT = builder.nextAccessor("biped/living/knockdown_right",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        KNOCKDOWN_LEFT = builder.nextAccessor("biped/living/knockdown_left",
                accessor -> new KnockdownAnimation(0.1F, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, false)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.TURNING_LOCKED, true)
                        .addState(EntityState.LOCKON_ROTATE, true)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, Animations.ReusableSources.CONSTANT_ONE));
        HOLD_ONEHAND_RUN = builder.nextAccessor("biped/living/hold_onehand_run",
                accessor -> new MovementAnimation(true, accessor, humanoidArmature));
        HOOK_HAND_LEFT = builder.nextAccessor("biped/living/left_hand_hook",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HOOK_HAND_LEFT_TOP = builder.nextAccessor("biped/living/left_hand_hook_top",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HOOK_HAND_RIGHT = builder.nextAccessor("biped/living/right_hand_hook",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HOOK_HAND_RIGHT_TOP = builder.nextAccessor("biped/living/right_hand_hook_top",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        IDLE_BREAK = builder.nextAccessor("biped/living/idle_break",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        PLACE_BLOCK = builder.nextAccessor("biped/living/place_block",
                accessor -> new ActionAnimation(0.0F, accessor, humanoidArmature));
        HEROBRINE_RUN = builder.nextAccessor("biped/living/herobrine_run",
                accessor -> new MovementAnimation(0.1F, true, accessor, humanoidArmature));
        HEROBRINE_ANIMATE = builder.nextAccessor("biped/living/herobrine_animate",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        LOW_CLONE_ESCAPE = builder.nextAccessor("biped/living/low_clone_escape",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        KNOCKED_ELITE = builder.nextAccessor("biped/living/knocked_elite",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_1 = builder.nextAccessor("biped/living/eating_elite_1",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_2 = builder.nextAccessor("biped/living/eating_elite_2",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_3 = builder.nextAccessor("biped/living/eating_elite_3",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        EATING_ELITE_4 = builder.nextAccessor("biped/living/eating_elite_4",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        PLAYER_HEROBRINE_POSSESSION = builder.nextAccessor("biped/living/player_herobrine_possession",
                accessor -> new StaticAnimation(false, accessor, humanoidArmature));
        HEROBRINE_SACRIFICING = builder.nextAccessor("biped/living/herobrine_sacrificing",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HEROBRINE_ASSISTANCE = builder.nextAccessor("biped/living/herobrine_assistance",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        HEROBRINE_STAGE_CHANGE = builder.nextAccessor("biped/living/herobrine_stage_change",
                accessor -> new StaticAnimation(true, accessor, humanoidArmature));
        PORTAL_SUMMON = builder.nextAccessor("biped/living/portal_summon",
                accessor -> new ActionAnimation(0.05F, Float.MAX_VALUE, accessor, humanoidArmature)
                        .addState(EntityState.MOVEMENT_LOCKED, true)
                        .addState(EntityState.CAN_BASIC_ATTACK, false)
                        .addState(EntityState.CAN_SKILL_EXECUTION, false)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, false));
        LAYING_DEATH = builder.nextAccessor("biped/living/laying_death",
                (accessor) -> new StaticAnimation(true, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.ActionAnimationProperty.CANCELABLE_MOVE, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, true));
        LAYING_DEATH_DEAD = builder.nextAccessor("biped/living/laying_death_dead", (accessor) -> new LongHitAnimation(0.16F, accessor, Armatures.BIPED));
        HOOK_GUN = builder.nextAccessor("biped/living/hook_gun",
                accessor -> new ActionAnimation(0.0F, 1.85F, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        DIG_MAINHAND = builder.nextAccessor("biped/living/dig_mainhand",
                accessor -> new StaticAnimation(0.1F, true, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        USE_MAINHAND = builder.nextAccessor("biped/living/use_mainhand",
                accessor -> new StaticAnimation(0.1F, false, accessor, humanoidArmature)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        EAT_MAINHAND = builder.nextAccessor("biped/living/eat_mainhand",
                accessor -> new StaticAnimation(0.1F, true, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        EAT_OFFHAND = builder.nextAccessor("biped/living/eat_offhand",
                accessor -> new StaticAnimation(0.1F, true, accessor, humanoidArmature)
                        .addProperty(AnimationProperty.StaticAnimationProperty.FIXED_HEAD_ROTATION, true)
                        .addState(EntityState.CAN_BASIC_ATTACK, false));
        ZIPLINE = builder.nextAccessor("biped/living/zipline", (accessor) ->
                new StaticAnimation(true, accessor, Armatures.BIPED)
                        .addProperty(AnimationProperty.StaticAnimationProperty.ON_ITEM_CHANGE_EVENT, AnimationEvent.SimpleEvent.create(Animations.ReusableSources.SET_TOOLS_BACK_WHEN_ITEM_CHANGED, AnimationEvent.Side.CLIENT))
                        .addEvents(AnimationProperty.StaticAnimationProperty.ON_END_EVENTS, AnimationEvent.SimpleEvent.create(Animations.ReusableSources.REVERT_TO_HANDS, AnimationEvent.Side.CLIENT))
                        .newTimePair(0.0F, 10000.0F)
                        .addStateRemoveOld(EntityState.TURNING_LOCKED, true)
                        .addStateRemoveOld(EntityState.CAN_BASIC_ATTACK, false)
                        .addStateRemoveOld(EntityState.CAN_SKILL_EXECUTION, false)
                        .addStateRemoveOld(EntityState.INACTION, true));
        FLY_UP = builder.nextAccessor("biped/living/fly_up", accessor ->
                new DodgeAnimation(0.0f, 0.15f, accessor, 0.4f, 1.4f, Armatures.BIPED)
                        .addProperty(AnimationProperty.ActionAnimationProperty.MOVE_VERTICAL, true)
                        .addProperty(AnimationProperty.ActionAnimationProperty.STOP_MOVEMENT, false)
                        .addEvents(
                                AnimationEvent.InTimeEvent.create(0.05F, (patch, animation, parameters) -> {
                                    if (patch.getOriginal() instanceof Mob mob) HerobrineEscapeHoleGoal.placeFlyUpPillarBlock(mob, 0);
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.1F, (patch, animation, parameters) -> {
                                    if (patch.getOriginal() instanceof Mob mob) HerobrineEscapeHoleGoal.placeFlyUpPillarBlock(mob, 1);
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.15F, (patch, animation, parameters) -> {
                                    if (patch.getOriginal() instanceof Mob mob) HerobrineEscapeHoleGoal.placeFlyUpPillarBlock(mob, 2);
                                }, AnimationEvent.Side.SERVER),
                                AnimationEvent.InTimeEvent.create(0.2F, (patch, animation, parameters) -> {
                                    if (patch.getOriginal() instanceof Mob mob) HerobrineEscapeHoleGoal.placeFlyUpPillarBlock(mob, 3);
                                }, AnimationEvent.Side.SERVER))
                        .addEvents(AnimationEvent.InTimeEvent.create(0.05f, Animations.ReusableSources.PLAY_SOUND, AnimationEvent.Side.CLIENT)
                                .params(EpicFightSounds.ENTITY_MOVE.get()), AnimationEvent.InTimeEvent.create(
                                0.05f, (livingEntityPatch, assetAccessor, animationParameters) ->
                                {
                                    LivingEntity entity = livingEntityPatch.getOriginal();
                                    entity.level().addParticle(EpicFightParticles.WHITE_AFTERIMAGE.get(), entity.getX(), entity.getY(), entity.getZ(), Double.longBitsToDouble(entity.getId()), 0.0F, 0.0F);
                                }, AnimationEvent.Side.CLIENT
                        )));
    }
}
