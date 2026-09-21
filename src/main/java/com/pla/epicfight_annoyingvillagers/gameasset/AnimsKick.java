package com.pla.epicfight_annoyingvillagers.gameasset;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.animations.KickAttackAnimation;
import net.neoforged.bus.api.SubscribeEvent;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty.AttackPhaseProperty;
import yesman.epicfight.api.animation.property.AnimationProperty.StaticAnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation.Phase;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations.ReusableSources;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.registry.entries.EpicFightSounds;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.registry.entries.EpicFightParticles;
import yesman.epicfight.world.damagesource.StunType;

public class AnimsKick {
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_C;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_COMBO;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_H;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_1;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_2;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_3;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_4;
    public static AnimationManager.AnimationAccessor<KickAttackAnimation> KICK_RUSH;

    public static void build(AnimationManager.AnimationBuilder builder) {
        Armatures.ArmatureAccessor<HumanoidArmature> humanoidArmature = Armatures.BIPED;
        AnimsKick.KICK_C = builder.nextAccessor("biped/kick/kick_c",
                (accessor) -> (new KickAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.4F, 0.45F, 0.49F, 0.49F, humanoidArmature.get().legL, AVCollider.KICK), new Phase(0.49F, 0.5F, 0.55F, 0.59F, 0.59F, humanoidArmature.get().legL, AVCollider.KICK), new Phase(0.59F, 0.6F, 0.65F, 0.69F, 0.69F, humanoidArmature.get().legL, AVCollider.KICK), new Phase(0.69F, 0.7F, 0.75F, 0.79F, 0.79F, humanoidArmature.get().legL, AVCollider.KICK), new Phase(0.79F, 0.8F, 0.85F, 0.9F, Float.MAX_VALUE, humanoidArmature.get().legL, AVCollider.KICK)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F), 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F), 4)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 4)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 1)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 2)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 3)
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get(), 4)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AnimsKick.KICK_COMBO = builder.nextAccessor("biped/kick/kick_combo",
                (accessor) -> (new KickAttackAnimation(0.05F, accessor, humanoidArmature, new Phase(0.0F, 0.15F, 0.25F, 0.3F, 0.3F, humanoidArmature.get().legL, AVCollider.KICK), new Phase(0.3F, 0.35F, 0.45F, 0.5F, 0.5F, humanoidArmature.get().legR, AVCollider.KICK), new Phase(0.5F, 0.55F, 0.65F, 0.7F, 0.7F, humanoidArmature.get().legL, AVCollider.KICK), new Phase(0.7F, 0.75F, 0.85F, 0.9F, 0.9F, humanoidArmature.get().legR, AVCollider.KICK), new Phase(0.9F, 1.05F, 1.15F, 1.8F, Float.MAX_VALUE, humanoidArmature.get().legR, AVCollider.KICK)))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F))
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F), 1)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F), 2)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F), 3)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F), 4)
                        .addProperty(AttackPhaseProperty.IMPACT_MODIFIER, ValueModifier.multiplier(5.8F), 4)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 1)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 1)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 2)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 2)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get(), 3)
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT, 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 1)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 2)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.HOLD, 3)
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG, 4)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AnimsKick.KICK_H = builder.nextAccessor("biped/kick/kick_h",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.8F, AVCollider.KICK, humanoidArmature.get().legR, accessor, humanoidArmature)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AnimsKick.KICK_1 = builder.nextAccessor("biped/kick/kick_1",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.5F, AVCollider.KICK, humanoidArmature.get().legR, accessor, humanoidArmature)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F))
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AnimsKick.KICK_2 = builder.nextAccessor("biped/kick/kick_2",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.4F, AVCollider.KICK, humanoidArmature.get().legL, accessor, humanoidArmature)).addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get()).addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AnimsKick.KICK_3 = builder.nextAccessor("biped/kick/kick_3",
                (accessor) -> (new KickAttackAnimation(0.05F, 0.05F, 0.3F, 0.4F, 0.6F, AVCollider.KICK, humanoidArmature.get().legR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AnimsKick.KICK_4 = builder.nextAccessor("biped/kick/kick_4",
                (accessor) -> (new KickAttackAnimation(0.1F, 0.05F, 0.4F, 0.4F, 0.4F, AVCollider.KICK, humanoidArmature.get().legL, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.LONG)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
        AnimsKick.KICK_RUSH = builder.nextAccessor("biped/kick/kick_rush",
                (accessor) -> (new KickAttackAnimation(0.05F, 0.05F, 0.1F, 0.4F, 0.6F, AVCollider.KICK, humanoidArmature.get().legR, accessor, humanoidArmature))
                        .addProperty(AttackPhaseProperty.SWING_SOUND, EpicFightSounds.WHOOSH.get())
                        .addProperty(AttackPhaseProperty.PARTICLE, EpicFightParticles.HIT_BLUNT)
                        .addProperty(AttackPhaseProperty.HIT_SOUND, EpicFightSounds.BLUNT_HIT.get())
                        .addProperty(AttackPhaseProperty.DAMAGE_MODIFIER, ValueModifier.setter(3.0F))
                        .addProperty(AttackPhaseProperty.STUN_TYPE, StunType.KNOCKDOWN)
                        .addProperty(StaticAnimationProperty.PLAY_SPEED_MODIFIER, ReusableSources.CONSTANT_ONE));
    }
}
