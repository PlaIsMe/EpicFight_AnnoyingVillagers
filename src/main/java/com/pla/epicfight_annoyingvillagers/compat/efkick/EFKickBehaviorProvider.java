package com.pla.epicfight_annoyingvillagers.compat.efkick;

import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedCombatBehaviors;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

/** Builds EFKick behaviors only when EFKick is loaded. */
public final class EFKickBehaviorProvider {
    private EFKickBehaviorProvider() {
    }

    public static void addTo(AdvancedCombatBehaviors.Builder<MobPatch<?>> builder) {
        builder.newBehaviorRoot(
                AdvancedCombatBehaviors.BehaviorRoot.builder()
                        .priority(1.0D)
                        .weight(5.0D)
                        .maxCooldown(200)
                        .waitForAnimationCompletion()
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(EFKickCompat.getEFKick1(), 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(EFKickCompat.getEFKick2(), 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(EFKickCompat.getEFKick3(), 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(EFKickCompat.getEFKick4(), 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(EFKickCompat.getEFKickH(), 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(EFKickCompat.getEFKickC(), 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(EFKickCompat.getEFKickRush(), 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(EFKickCompat.getEFKickCombo(), 0.0F)
                        )
        );
    }
}
