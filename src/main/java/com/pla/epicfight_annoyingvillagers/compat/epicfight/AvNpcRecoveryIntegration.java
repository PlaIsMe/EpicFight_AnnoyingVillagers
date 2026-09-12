package com.pla.epicfight_annoyingvillagers.compat.epicfight;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.event.AVNpcRecoveryEvent;
import com.pla.annoyingvillagers.event.AVNpcRigAnimationEvent;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAnimationAttackGoal;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

/** Event boundary keeps the core independent from optional Epic Fight classes. */
@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID)
public final class AvNpcRecoveryIntegration {
    public static AdvancedAvNpcPatch<?> getPatch(AVNpc npc) {
        return EpicFightCapabilities.getEntityPatch(npc, AdvancedAvNpcPatch.class);
    }

    public static boolean isCombatActionLocked(AVNpc npc) {
        var patch = getPatch(npc);
        if (patch == null) return false;
        if (patch.isCombatActionLocked()) return true;
        if (!patch.getEntityState().inaction() || patch.isGuardingLocally()) return false;
        // Ordinary attacks yield to higher-priority recovery. Unowned inaction still
        // protects hurt/stun/scripted animations and external execution ownership.
        for (var wrapped : npc.goalSelector.getAvailableGoals()) {
            if (wrapped.isRunning() && wrapped.getGoal() instanceof AdvancedAnimationAttackGoal<?> attack
                    && attack.ownsCurrentAnimation()) return false;
        }
        return true;
    }

    @SubscribeEvent
    public static void recovery(AVNpcRecoveryEvent event) {
        var npc = event.getNpc();
        var patch = getPatch(npc);
        if (patch == null) return;
        switch (event.getAction()) {
            case CHECK_START -> {
                if (isCombatActionLocked(npc)) event.setCanceled(true);
            }
            case START -> {
                // Pillaring need not play a rig clip before its first jump. Release the
                // old combat pose now, not only when a DIG/USE animation arrives later.
                for (var wrapped : npc.goalSelector.getAvailableGoals()) {
                    if (wrapped.isRunning() && wrapped.getGoal() instanceof AdvancedAnimationAttackGoal<?>) {
                        wrapped.stop();
                    }
                }
                patch.cancelGuard();
            }
            case DIG -> {
                EpicFight.playDiggingAnimation(npc);
                event.setCanceled(true);
            }
            case STOP_DIG -> EpicFight.stopDiggingAnimation(npc);
            case USE -> {
                if (EpicFight.playMainHandUseAnimation(npc)) event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void rigAnimation(AVNpcRigAnimationEvent event) {
        var patch = getPatch(event.getNpc());
        if (patch == null) return;
        // Native profile damage/colliders must not run alongside the Epic Fight weapon engine.
        // Scripted non-damaging actions retain their timed hooks; the patch yields while the
        // native controller owns them, as it also does for core rig locks and recovery.
        var profile = com.pla.annoyingvillagers.rig.RigCombatProfiles.getCombatProfile(event.getNpc());
        if (profile.containsAttack(event.getAnimationId())) {
            event.setCanceled(true);
        } else {
            // Stop the currently owned ordinary attack before allowing a scripted native
            // action with timed gameplay hooks to start in this same tick.
            for (var wrapped : event.getNpc().goalSelector.getAvailableGoals()) {
                if (wrapped.isRunning() && wrapped.getGoal() instanceof
                        com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAnimationAttackGoal<?>) {
                    wrapped.stop();
                }
            }
            patch.cancelGuard();
            // The core eating goal owns item consumption/timing; use the mob-safe standalone
            // Epic Fight clip for its visible hand action. No Player-only mirror animation.
            switch (event.getAnimationId()) {
                case EAT_MAINHAND, EAT_OFFHAND -> {
                    EpicFight.playEatingAnimation(event.getNpc());
                    event.setCanceled(true);
                }
                default -> { }
            }
        }
    }
}
