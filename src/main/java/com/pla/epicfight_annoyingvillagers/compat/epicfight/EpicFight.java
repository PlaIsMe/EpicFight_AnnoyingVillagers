package com.pla.epicfight_annoyingvillagers.compat.epicfight;

import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import com.pla.annoyingvillagers.clazz.AVNpc;

import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public final class EpicFight {
    private static boolean warnedMissingDigAnimation;
    private static boolean warnedMissingUseAnimation;
    private static boolean warnedMissingEatAnimation;
    private static boolean warnedMissingSleepAnimation;

    private EpicFight() {
    }

    public static void keepDiggingState(AVNpc playerNpc) {
        if (playerNpc == null) {
            return;
        }

        playerNpc.setRecoveryDigging(true);
        LivingEntityPatch<?> patch = getPatch(playerNpc);
        if (patch != null) {
            patch.currentLivingMotion = LivingMotions.DIGGING;
            patch.currentCompositeMotion = LivingMotions.DIGGING;
        }
    }

    public static void playDiggingAnimation(AVNpc playerNpc) {
        if (!canAnimate(playerNpc)) {
            return;
        }

        playerNpc.setRecoveryDigging(true);
        LivingEntityPatch<?> patch = getPatch(playerNpc);
        AssetAccessor<? extends StaticAnimation> animation = diggingAnimation();
        if (patch != null && animation != null) {
            cancelUtilityGuard(patch);
            patch.currentLivingMotion = LivingMotions.DIGGING;
            patch.currentCompositeMotion = LivingMotions.DIGGING;
            patch.playAnimationSynchronized(animation, 0.0F);
        }
    }

    public static boolean playMainHandUseAnimation(AVNpc playerNpc) {
        if (!canAnimate(playerNpc)) {
            return false;
        }

        LivingEntityPatch<?> patch = getPatch(playerNpc);
        AssetAccessor<? extends StaticAnimation> animation = mainHandUseAnimation();
        if (patch != null && animation != null) {
            cancelUtilityGuard(patch);
            patch.playAnimationSynchronized(animation, 0.0F);
            return true;
        }
        return false;
    }

    public static void playEatingAnimation(AVNpc playerNpc) {
        if (!canAnimate(playerNpc)) {
            return;
        }
        LivingEntityPatch<?> patch = getPatch(playerNpc);
        AssetAccessor<? extends StaticAnimation> animation = eatingAnimation(playerNpc);
        if (patch != null && animation != null) {
            cancelUtilityGuard(patch);
            patch.currentCompositeMotion = LivingMotions.EAT;
            patch.playAnimationSynchronized(animation, 0.0F);
        }
    }

    public static void keepEatingAnimation(AVNpc playerNpc) {
        if (!canAnimate(playerNpc) || !playerNpc.isHealing() || !playerNpc.isUsingItem()) {
            return;
        }
        LivingEntityPatch<?> patch = getPatch(playerNpc);
        AssetAccessor<? extends StaticAnimation> animation = eatingAnimation(playerNpc);
        if (patch == null || animation == null) {
            return;
        }
        patch.currentCompositeMotion = LivingMotions.EAT;
        var player = patch.getAnimator().getPlayerFor(animation);
        if (player == null || player.isEmpty() || !animation.equals(player.getRealAnimation())) {
            patch.playAnimationSynchronized(animation, 0.0F);
        }
    }

    public static void stopEatingAnimation(AVNpc playerNpc) {
        if (playerNpc == null) {
            return;
        }
        LivingEntityPatch<?> patch = getPatch(playerNpc);
        AssetAccessor<? extends StaticAnimation> animation = eatingAnimation();
        if (patch != null && animation != null) {
            patch.stopPlaying(animation);
            if (EpicFightCloneAnimations.EAT_OFFHAND != null) patch.stopPlaying(EpicFightCloneAnimations.EAT_OFFHAND);
            patch.currentCompositeMotion = LivingMotions.IDLE;
        }
    }

    /** Called by the patch's client motion update using synchronized healing state. */
    static void updateClientEatingAnimation(LivingEntityPatch<?> patch, boolean eating) {
        if (!patch.getOriginal().level().isClientSide()) {
            return;
        }
        AssetAccessor<? extends StaticAnimation> animation = eatingAnimation((AVNpc) patch.getOriginal());
        if (animation == null || !(patch.getAnimator() instanceof ClientAnimator animator)) {
            return;
        }
        if (!eating) {
            stopClientEatingClip(animator, EpicFightCloneAnimations.EAT_MAINHAND);
            stopClientEatingClip(animator, EpicFightCloneAnimations.EAT_OFFHAND);
            return;
        }
        // ClientAnimator separates composite motions from getLivingAnimations(). Equipment
        // preset resets can therefore lose/replace EAT despite the server retaining its loop.
        // Repair the actual composite binding, then inspect its layer rather than the base
        // player's animation. Do not restart a healthy loop or its transition every tick.
        if (!animation.equals(animator.getCompositeLivingMotion(LivingMotions.EAT))) {
            animator.addLivingAnimation(LivingMotions.EAT, animation);
        }
        patch.currentCompositeMotion = LivingMotions.EAT;
        var player = animator.getPlayerFor(animation);
        if (player == null || player.isEmpty() || !animation.equals(player.getRealAnimation())) {
            animator.playAnimation(animation, 0.0F);
        }
    }

    private static void cancelUtilityGuard(LivingEntityPatch<?> patch) {
        if (patch instanceof AdvancedMobPatch<?> advanced) {
            advanced.cancelGuard();
        }
    }

    public static void stopDiggingAnimation(AVNpc playerNpc) {
        if (playerNpc == null) {
            return;
        }

        playerNpc.setRecoveryDigging(false);
        LivingEntityPatch<?> patch = getPatch(playerNpc);
        if (patch != null) {
            patch.currentLivingMotion = LivingMotions.IDLE;
            patch.currentCompositeMotion = LivingMotions.IDLE;
            stopIfPresent(patch, diggingAnimation());
        }
    }

    public static void keepSleepingState(AVNpc playerNpc) {
        if (playerNpc == null || !playerNpc.isSleeping()) {
            return;
        }

        LivingEntityPatch<?> patch = getPatch(playerNpc);
        if (patch != null) {
            patch.currentLivingMotion = LivingMotions.SLEEP;
            patch.currentCompositeMotion = LivingMotions.SLEEP;
        }
    }

    public static void playSleepingAnimation(AVNpc playerNpc) {
        if (!canAnimate(playerNpc) || !playerNpc.isSleeping()) {
            return;
        }

        LivingEntityPatch<?> patch = getPatch(playerNpc);
        AssetAccessor<? extends StaticAnimation> animation = sleepingAnimation();
        if (patch != null && animation != null) {
            patch.currentLivingMotion = LivingMotions.SLEEP;
            patch.currentCompositeMotion = LivingMotions.SLEEP;
            patch.playAnimationSynchronized(animation, 0.0F);
        }
    }

    public static void stopSleepingAnimation(AVNpc playerNpc) {
        if (playerNpc == null) {
            return;
        }

        LivingEntityPatch<?> patch = getPatch(playerNpc);
        if (patch != null) {
            stopSleepingIfPresent(patch, sleepingAnimation());
            patch.currentLivingMotion = LivingMotions.IDLE;
            patch.currentCompositeMotion = LivingMotions.IDLE;
        }
    }

    static AssetAccessor<? extends StaticAnimation> diggingAnimation() {
        AssetAccessor<? extends StaticAnimation> animation = EpicFightCloneAnimations.DIG_MAINHAND;
        if (isUsable(animation)) {
            return animation;
        }

        if (!warnedMissingDigAnimation) {
            warnedMissingDigAnimation = true;
            EpicFightAnnoyingVillagers.LOGGER.warn("Smart NPC Epic Fight digging animation is unavailable; skipping mining swing sync.");
        }
        return null;
    }

    static AssetAccessor<? extends StaticAnimation> sleepingAnimation() {
        AssetAccessor<? extends StaticAnimation> animation = Animations.BIPED_SLEEPING;
        if (isSleepingAnimationUsable(animation)) {
            return animation;
        }

        if (!warnedMissingSleepAnimation) {
            warnedMissingSleepAnimation = true;
            EpicFightAnnoyingVillagers.LOGGER.warn("Smart NPC Epic Fight sleeping animation is unavailable; skipping sleep animation sync.");
        }
        return null;
    }

    static AssetAccessor<? extends StaticAnimation> mainHandUseAnimation() {
        AssetAccessor<? extends StaticAnimation> animation = EpicFightCloneAnimations.USE_MAINHAND;
        if (isMainHandUseAnimationUsable(animation)) {
            return animation;
        }

        if (!warnedMissingUseAnimation) {
            warnedMissingUseAnimation = true;
            EpicFightAnnoyingVillagers.LOGGER.warn("Smart NPC Epic Fight main-hand use animation is unavailable; using the vanilla swing instead.");
        }
        return null;
    }

    static AssetAccessor<? extends StaticAnimation> eatingAnimation() {
        AssetAccessor<? extends StaticAnimation> animation = EpicFightCloneAnimations.EAT_MAINHAND;
        if (isEatingAnimationUsable(animation)) {
            return animation;
        }
        if (!warnedMissingEatAnimation) {
            warnedMissingEatAnimation = true;
            EpicFightAnnoyingVillagers.LOGGER.warn("Smart NPC Epic Fight eating animation is unavailable.");
        }
        return null;
    }

    private static void stopClientEatingClip(ClientAnimator animator, AssetAccessor<? extends StaticAnimation> animation) {
        if (animation == null) return;
        var player = animator.getPlayerFor(animation);
        if (player != null && animation.equals(player.getRealAnimation())) animator.stopPlaying(animation);
    }

    private static AssetAccessor<? extends StaticAnimation> eatingAnimation(AVNpc npc) {
        return npc.isUsingItem() && npc.getUsedItemHand() == net.minecraft.world.InteractionHand.OFF_HAND
                && isEatingAnimationUsable(EpicFightCloneAnimations.EAT_OFFHAND)
                ? EpicFightCloneAnimations.EAT_OFFHAND : eatingAnimation();
    }

    private static LivingEntityPatch<?> getPatch(AVNpc playerNpc) {
        return EpicFightCapabilities.getEntityPatch(playerNpc, LivingEntityPatch.class);
    }

    private static boolean canAnimate(AVNpc playerNpc) {
        return playerNpc != null
                && !playerNpc.level().isClientSide
                && playerNpc.isAlive()
                && !playerNpc.isRemoved()
                && !playerNpc.isDeadOrDying();
    }

    private static boolean isUsable(AssetAccessor<? extends StaticAnimation> animation) {
        if (animation == null) {
            return false;
        }

        try {
            return animation.isPresent();
        } catch (RuntimeException exception) {
            if (!warnedMissingDigAnimation) {
                warnedMissingDigAnimation = true;
                EpicFightAnnoyingVillagers.LOGGER.warn("Smart NPC Epic Fight digging animation could not be resolved.", exception);
            }
            return false;
        }
    }

    private static boolean isSleepingAnimationUsable(AssetAccessor<? extends StaticAnimation> animation) {
        if (animation == null) {
            return false;
        }

        try {
            return animation.isPresent();
        } catch (RuntimeException exception) {
            if (!warnedMissingSleepAnimation) {
                warnedMissingSleepAnimation = true;
                EpicFightAnnoyingVillagers.LOGGER.warn("Smart NPC Epic Fight sleeping animation could not be resolved.", exception);
            }
            return false;
        }
    }

    private static boolean isMainHandUseAnimationUsable(AssetAccessor<? extends StaticAnimation> animation) {
        if (animation == null) {
            return false;
        }

        try {
            return animation.isPresent();
        } catch (RuntimeException exception) {
            if (!warnedMissingUseAnimation) {
                warnedMissingUseAnimation = true;
                EpicFightAnnoyingVillagers.LOGGER.warn("Smart NPC Epic Fight main-hand use animation could not be resolved; using the vanilla swing instead.", exception);
            }
            return false;
        }
    }

    private static boolean isEatingAnimationUsable(AssetAccessor<? extends StaticAnimation> animation) {
        if (animation == null) {
            return false;
        }

        try {
            return animation.isPresent();
        } catch (RuntimeException exception) {
            if (!warnedMissingEatAnimation) {
                warnedMissingEatAnimation = true;
                EpicFightAnnoyingVillagers.LOGGER.warn("Smart NPC Epic Fight eating animation could not be resolved.", exception);
            }
            return false;
        }
    }

    private static void stopIfPresent(LivingEntityPatch<?> patch, AssetAccessor<? extends StaticAnimation> animation) {
        if (isUsable(animation)) {
            patch.stopPlaying(animation);
        }
    }

    private static void stopSleepingIfPresent(LivingEntityPatch<?> patch, AssetAccessor<? extends StaticAnimation> animation) {
        if (isSleepingAnimationUsable(animation)) {
            patch.stopPlaying(animation);
        }
    }
}
