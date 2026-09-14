package com.pla.epicfight_annoyingvillagers.compat.epicfight;

import com.pla.annoyingvillagers.entity.NullEntity;
import com.pla.annoyingvillagers.entity.goal.RandomCombatJumpGoal;
import com.pla.annoyingvillagers.entity.goal.RigAnimatedMeleeAttackGoal;
import com.pla.annoyingvillagers.entity.goal.RigShieldGuardGoal;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsNullWeapon;
import java.util.Set;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedCombatBehaviors;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAnimationAttackGoal;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedChasingGoal;
import com.pla.annoyingvillagers.clazz.AVNpc;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.fml.ModList;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.entitypatch.Factions;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.List;
import java.util.EnumSet;

public class AdvancedAvNpcPatch<T extends PathfinderMob> extends AdvancedMobPatch<T> {
    @Override
    protected void selectGoalToRemove(Set<Goal> toRemove) {
        super.selectGoalToRemove(toRemove);
        for (WrappedGoal wrapped : this.getOriginal().goalSelector.getAvailableGoals()) {
            if (wrapped.getGoal() instanceof RigAnimatedMeleeAttackGoal
                    || wrapped.getGoal() instanceof RigShieldGuardGoal
                    || wrapped.getGoal() instanceof RandomCombatJumpGoal) {
                toRemove.add(wrapped.getGoal());
            }
        }
    }
    public AdvancedAvNpcPatch() {
        super(Factions.NEUTRAL);
        this.setChasingSpeed(1.0D);
    }

    @Override
    protected void configureCombatGoalControls(Goal attackGoal, Goal chasingGoal) {
        attackGoal.setFlags(EnumSet.of(Goal.Flag.LOOK));
        chasingGoal.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    protected int getAttackGoalPriority() {
        return 2;
    }

    @Override
    protected int getChasingGoalPriority() {
        return 2;
    }

    @Override
    protected boolean isCombatEnabled() {
        return true;
    }

    @Override
    protected boolean isUtilityActionActive() {
        // Some non-goal AV actions (such as Reaper's dragon summon) still own a rig action window.
        if (RigAnimationController.hasActiveAnimation(this.getOriginal())) return true;
        if (this.getOriginal() instanceof AVNpc npc
                && (npc.isLocked() || npc.isRecoveryActionActive() || npc.isHealing() || npc.isUsingItem() || npc.isRecoveryDigging() || npc.isSleeping()
                || RigAnimationController.hasActiveAnimation(npc))) {
            return true;
        }
        // Include the recovery frames of atomic item uses after their goal stops.
        var player = this.getAnimator().getPlayerFor(null);
        if (player != null && !player.isEmpty()
                && player.getRealAnimation().equals(EpicFightCloneAnimations.USE_MAINHAND)) {
            return true;
        }
        // Cheap selector state only; never call another goal's eligibility or paths here.
        for (WrappedGoal wrapped : this.getOriginal().goalSelector.getAvailableGoals()) {
            if (wrapped.isRunning()
                    && wrapped.getPriority() <= Math.max(getAttackGoalPriority(), getChasingGoalPriority())
                    && !(wrapped.getGoal() instanceof AdvancedAnimationAttackGoal<?>)
                    && !(wrapped.getGoal() instanceof AdvancedChasingGoal)
                    && (wrapped.getFlags().contains(Goal.Flag.MOVE) || wrapped.getFlags().contains(Goal.Flag.LOOK))) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void initAnimator(Animator animator) {
        super.initAnimator(animator);
        animator.addLivingAnimation(LivingMotions.IDLE, Animations.BIPED_IDLE);
        animator.addLivingAnimation(LivingMotions.WALK, Animations.BIPED_WALK);
        animator.addLivingAnimation(LivingMotions.RUN, Animations.BIPED_RUN);
        animator.addLivingAnimation(LivingMotions.CHASE, Animations.BIPED_RUN);
        animator.addLivingAnimation(LivingMotions.SNEAK, Animations.BIPED_SNEAK);
        animator.addLivingAnimation(LivingMotions.KNEEL, Animations.BIPED_KNEEL);
        animator.addLivingAnimation(LivingMotions.FALL, Animations.BIPED_FALL);
        animator.addLivingAnimation(LivingMotions.MOUNT, Animations.BIPED_MOUNT);
        animator.addLivingAnimation(LivingMotions.SLEEP, Animations.BIPED_SLEEPING);
        animator.addLivingAnimation(LivingMotions.DEATH, Animations.BIPED_DEATH);
        if (EpicFightCloneAnimations.DIG_MAINHAND != null) {
            animator.addLivingAnimation(LivingMotions.DIGGING, EpicFightCloneAnimations.DIG_MAINHAND);
        }
        if (EpicFightCloneAnimations.EAT_MAINHAND != null) {
            animator.addLivingAnimation(LivingMotions.EAT, EpicFightCloneAnimations.EAT_MAINHAND);
        }
    }

    @Override
    protected void addCustomBehaviorRoots(AdvancedCombatBehaviors.Builder<MobPatch<?>> builder,
                                          CapabilityItem mainHandCap,
                                          CapabilityItem offHandCap, Style style) {
        if (this.getOriginal() instanceof NullEntity) {
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
        }
        if (ModList.get().isLoaded("combat_evolution")) {
            CombatEvolutionBehaviorProvider.addTo(builder);
        }

        builder.newBehaviorRoot(
                        AdvancedCombatBehaviors.BehaviorRoot.builder()
                                .priority(1.0D)
                                .weight(10.0D)
                                .maxCooldown(80)
                                .waitForAnimationCompletion()
                                .addFirstBehavior(
                                        AdvancedCombatBehaviors.Behavior.builder()
                                                .withinDistance(0.0D, 5.0D)
                                                .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                )
                                .addFirstBehavior(
                                        AdvancedCombatBehaviors.Behavior.builder()
                                                .withinDistance(0.0D, 5.0D)
                                                .animationBehavior(Animations.BIPED_ROLL_BACKWARD, 0.0F)
                                )
                );

        if (ModList.get().isLoaded("efkick")) {
            EFKickBehaviorProvider.addTo(builder);
        }
    }

    @Override
    public boolean canGuard() {
        return true;
    }

    @Override
    public int getGuardChance() {
        return 12;
    }

    @Override
    protected List<AdditionalAttackGroup> getAdditionalAttackGroups(CapabilityItem mainHandCap, CapabilityItem offHandCap, Style style) {
        if (mainHandCap.getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD) {
            return style == CapabilityItem.Styles.TWO_HAND
                    ? List.of(AdditionalAttackGroup.random(0.25F, Animations.SWEEPING_EDGE))
                    : List.of(AdditionalAttackGroup.random(0.25F, Animations.DANCING_EDGE)
            );
        }
        if (mainHandCap.getWeaponCategory() == CapabilityItem.WeaponCategories.AXE) {
            return List.of(AdditionalAttackGroup.random(0.25F, Animations.THE_GUILLOTINE));
        }
        if (mainHandCap.getWeaponCategory() == CapabilityItem.WeaponCategories.SPEAR) {
            return style == CapabilityItem.Styles.TWO_HAND
                    ? List.of(AdditionalAttackGroup.random(0.25F, Animations.GRASPING_SPIRAL_FIRST, Animations.GRASPING_SPIRAL_SECOND))
                    : List.of(AdditionalAttackGroup.random(0.25F, Animations.HEARTPIERCER)
            );
        }
        if (mainHandCap.getWeaponCategory() == CapabilityItem.WeaponCategories.GREATSWORD) {
            return List.of(AdditionalAttackGroup.random(0.25F, Animations.STEEL_WHIRLWIND));
        }
        if (mainHandCap.getWeaponCategory() == CapabilityItem.WeaponCategories.UCHIGATANA) {
            return List.of(AdditionalAttackGroup.random(0.25F, Animations.BATTOJUTSU, Animations.BATTOJUTSU_DASH));
        }
        if (mainHandCap.getWeaponCategory() == CapabilityItem.WeaponCategories.LONGSWORD) {
            return List.of(AdditionalAttackGroup.random(0.25F, Animations.SHARP_STAB));
        }
        if (mainHandCap.getWeaponCategory() == CapabilityItem.WeaponCategories.DAGGER) {
            return style == CapabilityItem.Styles.TWO_HAND
                    ? List.of(AdditionalAttackGroup.random(0.25F, Animations.BLADE_RUSH_COMBO1, Animations.BLADE_RUSH_COMBO2, Animations.BLADE_RUSH_COMBO3))
                    : List.of(AdditionalAttackGroup.random(0.25F, Animations.EVISCERATE_FIRST, Animations.EVISCERATE_SECOND)
            );
        }
        if (mainHandCap.getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
            return List.of(AdditionalAttackGroup.random(0.25F, Animations.RELENTLESS_COMBO));
        }
        return super.getAdditionalAttackGroups(mainHandCap, offHandCap, style);
    }

    @Override
    public void updateMotion(boolean considerInaction) {
        super.updateMotion(considerInaction);
        if (this.getOriginal() instanceof AVNpc playerNpc) {
            boolean eating = !playerNpc.isSleeping() && playerNpc.isAlive()
                    && playerNpc.isHealing() && playerNpc.isUsingItem() && playerNpc.getUseItem().isEdible();
            EpicFight.updateClientEatingAnimation(this, eating);
            if (playerNpc.isSleeping()) {
                this.currentLivingMotion = LivingMotions.SLEEP;
                this.currentCompositeMotion = LivingMotions.SLEEP;
            } else if (eating) {
                this.currentCompositeMotion = LivingMotions.EAT;
            } else if (playerNpc.isRecoveryDigging()) {
                this.currentLivingMotion = LivingMotions.DIGGING;
                this.currentCompositeMotion = LivingMotions.DIGGING;
            } else if ((playerNpc.isShiftKeyDown() || playerNpc.isCrouching()) && canApplyCrouchMotion()) {
                this.currentLivingMotion = isMovingMotion() ? LivingMotions.SNEAK : LivingMotions.KNEEL;
                this.currentCompositeMotion = this.currentLivingMotion;
            } else if (playerNpc.isSprinting() && isMovingMotion()) {
                this.currentLivingMotion = LivingMotions.RUN;
                this.currentCompositeMotion = LivingMotions.RUN;
            } else if (playerNpc.isAggressive() && this.currentLivingMotion == LivingMotions.WALK) {
                // AdvancedChasingGoal sets aggression, not the vanilla sprint flag.
                this.currentLivingMotion = LivingMotions.CHASE;
                this.currentCompositeMotion = LivingMotions.CHASE;
            }
        }
    }

    private boolean canApplyCrouchMotion() {
        return this.currentLivingMotion == LivingMotions.IDLE
                || this.currentLivingMotion == LivingMotions.WALK
                || this.currentLivingMotion == LivingMotions.RUN
                || this.currentLivingMotion == LivingMotions.CHASE;
    }

    private boolean isMovingMotion() {
        return this.currentLivingMotion == LivingMotions.WALK
                || this.currentLivingMotion == LivingMotions.RUN
                || this.currentLivingMotion == LivingMotions.CHASE;
    }
}
