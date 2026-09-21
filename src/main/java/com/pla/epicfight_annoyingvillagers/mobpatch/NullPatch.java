package com.pla.epicfight_annoyingvillagers.mobpatch;

import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.entity.goal.NullSummonSkeletonGoal;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAvNpcPatch;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedCombatBehaviors;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsNullWeapon;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.List;

public class NullPatch extends AdvancedAvNpcPatch<NullEntity> {
    public NullPatch(NullEntity original) {
        super(original);
    }

    @Override
    protected void addCustomBehaviorRoots(AdvancedCombatBehaviors.Builder<MobPatch<?>> builder,
                                          CapabilityItem mainHandCap,
                                          CapabilityItem offHandCap,
                                          Style style) {
        super.addCustomBehaviorRoots(builder, mainHandCap, offHandCap, style);
        builder.newBehaviorRoot(AdvancedCombatBehaviors.BehaviorRoot.builder()
                .priority(1.0D)
                .weight(4.0D)
                .maxCooldown(80)
                .waitForAnimationCompletion()
                .addFirstBehavior(AdvancedCombatBehaviors.Behavior.builder()
                        .withinDistance(0.0D, 24.0D)
                        .custom(patch -> patch.getOriginal() instanceof NullEntity nullEntity
                                && !nullEntity.getAvailableNullWeapons().isEmpty())
                        .animationBehavior(AnimsNullWeapon.NULL_WEAPON_SPECIAL, 0.0F)));

        builder.newBehaviorRoot(AdvancedCombatBehaviors.BehaviorRoot.builder()
                .priority(1.0D)
                .weight(4.0D)
                .maxCooldown(80)
                .waitForAnimationCompletion()
                .addFirstBehavior(AdvancedCombatBehaviors.Behavior.builder()
                        .withinDistance(0.0D, 24.0D)
                        .custom(patch -> patch.getOriginal() instanceof NullEntity nullEntity
                                && NullSummonSkeletonGoal.canStartSummoning(nullEntity))
                        .animationBehavior(AnimsNullWeapon.NULL_WEAPON_INNATE_SPECIAL, 0.0F)));
    }

    @Override
    protected List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> getDodgeAnimations() {
        return List.of(
                WOMAnimations.SHADOWSTEP_FORWARD,
                WOMAnimations.SHADOWSTEP_BACKWARD,
                WOMAnimations.SHADOWSTEP_RIGHT,
                WOMAnimations.SHADOWSTEP_LEFT
        );
    }
}
