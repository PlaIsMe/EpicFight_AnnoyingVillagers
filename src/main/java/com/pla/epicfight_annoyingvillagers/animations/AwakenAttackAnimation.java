package com.pla.epicfight_annoyingvillagers.animations;

import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.Nullable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.model.Armature;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Optional;

/**
 * Authorized local port of Epic Fight Awaken's EFAAttackAnimation. It preserves
 * the add-on's capped attack-speed scaling without requiring the unported mod.
 */
public class AwakenAttackAnimation extends AttackAnimation {
    public AwakenAttackAnimation(float convertTime, float antic, float preDelay, float contact, float recovery,
                                 @Nullable Collider collider, Joint joint,
                                 AnimationManager.AnimationAccessor<? extends AttackAnimation> accessor,
                                 AssetAccessor<? extends Armature> armature) {
        super(convertTime, antic, preDelay, contact, recovery, collider, joint, accessor, armature);
    }

    @Override
    public float getPlaySpeed(LivingEntityPatch<?> entityPatch, DynamicAnimation animation) {
        if (!(entityPatch instanceof PlayerPatch<?> playerPatch)) {
            return 1.0F;
        }

        float factor = this.getProperty(AnimationProperty.AttackAnimationProperty.ATTACK_SPEED_FACTOR).orElse(1.0F);
        Optional<Float> basisSpeed = this.getProperty(AnimationProperty.AttackAnimationProperty.BASIS_ATTACK_SPEED);
        float ratio = basisSpeed.map(speed -> playerPatch.getAttackSpeed(InteractionHand.MAIN_HAND) / speed).orElse(1.0F);
        ratio = Math.round(ratio * 1000.0F) / 1000.0F;
        return Mth.clamp(1.0F + (ratio - 1.0F) * factor, 0.1F, 1.5F);
    }
}
