package com.pla.epicfight_annoyingvillagers.compat.combat_evolution;

import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedCombatBehaviors;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

/**
 * Builds Combat Evolution behaviors only after Forge has confirmed that the mod is loaded.
 * Keeping these references out of AdvancedAvNpcPatch prevents the JVM from resolving
 * Combat Evolution classes while that optional mod is absent.
 */
public final class CombatEvolutionBehaviorProvider {
    private CombatEvolutionBehaviorProvider() {
    }

    public static void addTo(AdvancedCombatBehaviors.Builder<MobPatch<?>> builder) {
        builder.newBehaviorRoot(
                AdvancedCombatBehaviors.BehaviorRoot.builder()
                        .priority(4.0D)
                        .weight(1000.0D)
                        .maxCooldown(0)
                        .waitForAnimationCompletion()
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.builder()
                                        .custom(CombatEvolution::canExecute)
                                        .withinDistance(0.0D, 5.0D)
                                        .animationBehavior(Animations.BIPED_SNEAK, 0.0F)
                                        .addExBehavior(CombatEvolution::performExecute)
                        )
        );
    }
}
