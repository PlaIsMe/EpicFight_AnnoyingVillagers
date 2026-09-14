package com.pla.epicfight_annoyingvillagers.util;

import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsDemoniacVoltageReaver;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEnderAegis;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEnderGlaive;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsNullWeapon;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsObsidianSledgehammer;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsObsidianWeapon;
import com.pla.annoyingvillagers.entity.ReaperHerobrineEntity;
import com.pla.annoyingvillagers.entity.ShadowHerobrineEntity;
import com.pla.annoyingvillagers.entity.goal.EliteHerobrineSecondFormGoal;
import com.pla.annoyingvillagers.entity.goal.ShadowHerobrineShootDarkObGoal;
import com.pla.annoyingvillagers.entity.goal.ShadowHerobrineSummonDarkObGoal;
import com.pla.annoyingvillagers.rig.RigAnimationId;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.animation.types.StaticAnimation;

public final class GoalAnimationCompat {
    private GoalAnimationCompat() {}

    public static AssetAccessor<? extends StaticAnimation> animation(RigAnimationId id) {
        return switch (id) {
            case AEGIS_HEROBRINE_ULT -> AnimsEnderAegis.ENDER_AEGIS_INNATE;
            case GLAIVE_HEROBRINE_ULT -> AnimsEnderGlaive.ENDER_GLAIVE_INNATE;
            case GLAIVE_HEROBRINE_EXTRA_ULT -> AnimsEnderGlaive.ENDER_GLAIVE_INNATE_SPECIAL;
            case SLEDGEHAMMER_HEROBRINE_ULT -> AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_INNATE;
            case SLEDGEHAMMER_HEROBRINE_EXTRA_ULT -> AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_INNATE_SPECIAL;
            case SWORDSMAN_HEROBRINE_ULT -> AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE;
            case SWORDSMAN_HEROBRINE_EXTRA_ULT -> AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE_SPECIAL;
            case POINT_LEFT_HAND_TOWARD -> AVAnimations.POINT_LEFT_HAND_TOWARD;
            case POINT_LEFT_HAND_MIDDLE -> AVAnimations.POINT_LEFT_HAND_MIDDLE;
            case POINT_LEFT_HAND_UP -> AVAnimations.POINT_LEFT_HAND_UP;
            case OBSIDIAN_MACHINE_GUN -> AnimsObsidianWeapon.OBSIDIAN_MACHINE_GUN;
            case NULL_EXTRA_ULT -> AnimsNullWeapon.NULL_WEAPON_INNATE_SPECIAL;
            default -> throw new IllegalArgumentException("No Epic Fight goal animation for " + id);
        };
    }

    /** Hand gestures are also used by portal support; only combat goals cast spells. */
    public static void castPointAction(LivingEntity entity, RigAnimationId id) {
        if (!(entity instanceof Mob mob) || mob.level().isClientSide()) return;
        boolean combatGoalRunning = mob.goalSelector.getAvailableGoals().stream().anyMatch(wrapped ->
                wrapped.isRunning() && (wrapped.getGoal() instanceof EliteHerobrineSecondFormGoal<?>
                        || wrapped.getGoal() instanceof ShadowHerobrineShootDarkObGoal
                        || wrapped.getGoal() instanceof ShadowHerobrineSummonDarkObGoal));
        if (!combatGoalRunning) return;
        if (mob instanceof ReaperHerobrineEntity reaper) {
            switch (id) {
                case POINT_LEFT_HAND_TOWARD -> reaper.castThunderFromSecondForm();
                case POINT_LEFT_HAND_MIDDLE -> reaper.respawnHealingCrystalFromSecondForm();
                case POINT_LEFT_HAND_UP -> reaper.castMeteoriteFromSecondForm();
                default -> { }
            }
        } else if (mob instanceof ShadowHerobrineEntity shadow) {
            if (id == RigAnimationId.POINT_LEFT_HAND_TOWARD) shadow.shootDarkObsAtTarget(2.0D);
            else if (id == RigAnimationId.POINT_LEFT_HAND_MIDDLE) shadow.spawnDarkObEntities();
        }
    }
}
