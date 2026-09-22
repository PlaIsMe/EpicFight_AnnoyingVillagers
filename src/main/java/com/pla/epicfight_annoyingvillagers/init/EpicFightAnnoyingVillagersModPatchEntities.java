package com.pla.epicfight_annoyingvillagers.init;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.event.AVNpcRecoveryEvent;
import com.pla.annoyingvillagers.event.AVNpcRigAnimationEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.rig.RigCombatProfiles;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAnimationAttackGoal;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAvNpcPatch;
import com.pla.epicfight_annoyingvillagers.mobpatch.*;
import com.pla.epicfight_annoyingvillagers.util.AvNpcAnimationCompat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import yesman.epicfight.api.event.types.registry.EntityPatchRegistryEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.mob.SkeletonPatch;
import yesman.epicfight.registry.entries.EpicFightAttributes;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID)
public final class EpicFightAnnoyingVillagersModPatchEntities {
    private EpicFightAnnoyingVillagersModPatchEntities() {
    }

    public static void setPatch(EntityPatchRegistryEvent event) {
        for (EntityType<? extends PathfinderMob> type : advancedNpcTypes()) {
            registerAdvancedPatch(event, type);
        }

        for (EntityType<? extends PathfinderMob> type : stepDodgeNpcTypes()) {
            registerStepDodgePatch(event, type);
        }

        for (EntityType<? extends PathfinderMob> type : fullDodgeNpcTypes()) {
            registerFullDodgePatch(event, type);
        }

        event.registerEntityPatch(AnnoyingVillagersModEntities.NULL.get(), NullPatch::new);
        event.registerEntityPatch(AnnoyingVillagersModEntities.NULL_SKELETON.get(), SkeletonPatch::new);
        event.registerEntityPatch(AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(), AegisHerobrinePatch::new);
        event.registerEntityPatch(AnnoyingVillagersModEntities.ANGRY_STEVE.get(), AngryStevePatch::new);
        event.registerEntityPatch(AnnoyingVillagersModEntities.BLUE_DEMON.get(), BlueDemonPatch::new);
    }

    private static <T extends PathfinderMob> void registerAdvancedPatch(
            EntityPatchRegistryEvent event,
            EntityType<T> type
    ) {
        event.registerEntityPatch(type, AdvancedAvNpcPatch::new);
    }

    private static <T extends PathfinderMob> void registerStepDodgePatch(
            EntityPatchRegistryEvent event,
            EntityType<T> type
    ) {
        event.registerEntityPatch(type, StepDodgeAvNpcPatch::new);
    }

    private static <T extends PathfinderMob> void registerFullDodgePatch(
            EntityPatchRegistryEvent event,
            EntityType<T> type
    ) {
        event.registerEntityPatch(type, FullDodgeAvNpcPatch::new);
    }

    @SubscribeEvent
    public static void addEpicFightAttributes(EntityAttributeModificationEvent event) {
        List<EntityType<? extends LivingEntity>> patchedTypes = new ArrayList<>(advancedNpcTypes());
        patchedTypes.add(AnnoyingVillagersModEntities.NULL_SKELETON.get());
        for (EntityType<? extends LivingEntity> type : patchedTypes) {
            // Match the player base speed used by SmartNpc's 1.21.1 patch. Without
            // this attribute Epic Fight falls back to 1.0, stretching fist clips
            // (whose basis speed is 2.2-2.5) and holding their final keyframes.
            event.add(type, Attributes.ATTACK_SPEED, 4.0D);
            event.add(type, EpicFightAttributes.WEIGHT);
            event.add(type, EpicFightAttributes.ARMOR_NEGATION);
            event.add(type, EpicFightAttributes.IMPACT);
            event.add(type, EpicFightAttributes.MAX_STRIKES);
            event.add(type, EpicFightAttributes.STUN_ARMOR);
            event.add(type, EpicFightAttributes.OFFHAND_ATTACK_SPEED);
            event.add(type, EpicFightAttributes.OFFHAND_MAX_STRIKES);
            event.add(type, EpicFightAttributes.OFFHAND_ARMOR_NEGATION);
            event.add(type, EpicFightAttributes.OFFHAND_IMPACT);
            event.add(type, EpicFightAttributes.MAX_STAMINA);
            event.add(type, EpicFightAttributes.STAMINA_REGEN);
        }
    }

    private static List<EntityType<? extends PathfinderMob>> advancedNpcTypes() {
        return List.of(
                AnnoyingVillagersModEntities.ALEX.get(),
                AnnoyingVillagersModEntities.JEV.get(),
                AnnoyingVillagersModEntities.STEVE.get(),
                AnnoyingVillagersModEntities.CHRIS.get(),
                AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(),
                AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(),
                AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(),
                AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(),
                AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get(),
                AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(),
                AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(),
                AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(),
                AnnoyingVillagersModEntities.HEROBRINE_CLONE.get(),
                AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE.get(),
                AnnoyingVillagersModEntities.NULL.get(),
                AnnoyingVillagersModEntities.HEROBRINE_GREG.get(),
                AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE.get(),
                AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(),
                AnnoyingVillagersModEntities.HEROBRINE_7.get(),
                AnnoyingVillagersModEntities.BLUE_DEMON.get()
        );
    }

    private static List<EntityType<? extends PathfinderMob>> stepDodgeNpcTypes() {
        return List.of(
                AnnoyingVillagersModEntities.STEVE.get(),
                AnnoyingVillagersModEntities.ALEX.get(),
                AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT.get()
        );
    }

    private static List<EntityType<? extends PathfinderMob>> fullDodgeNpcTypes() {
        return List.of(
                AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(),
                AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get(),
                AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(),
                AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(),
                AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE.get(),
                AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(),
                AnnoyingVillagersModEntities.HEROBRINE_7.get(),
                AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE.get(),
                AnnoyingVillagersModEntities.HEROBRINE_CLONE.get(),
                AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get()
        );
    }

    private static AdvancedAvNpcPatch<?> getPatch(AVNpc npc) {
        return EpicFightCapabilities.getEntityPatch(npc, AdvancedAvNpcPatch.class);
    }

    private static boolean isCombatActionLocked(AVNpc npc) {
        AdvancedAvNpcPatch<?> patch = getPatch(npc);
        if (patch == null) return false;
        if (patch.isCombatActionLocked()) return true;
        if (!patch.getEntityState().inaction() || patch.isGuardingLocally()) return false;

        for (var wrapped : npc.goalSelector.getAvailableGoals()) {
            if (wrapped.isRunning()
                    && wrapped.getGoal() instanceof AdvancedAnimationAttackGoal<?> attack
                    && attack.ownsCurrentAnimation()) {
                return false;
            }
        }
        return true;
    }

    @EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID)
    public static final class ForgeEvents {
        private ForgeEvents() {
        }

        @SubscribeEvent
        public static void onRecovery(AVNpcRecoveryEvent event) {
            AVNpc npc = event.getNpc();
            AdvancedAvNpcPatch<?> patch = getPatch(npc);
            if (patch == null) return;

            switch (event.getAction()) {
                case CHECK_START -> {
                    if (isCombatActionLocked(npc)) event.setCanceled(true);
                }
                case START -> {
                    for (var wrapped : npc.goalSelector.getAvailableGoals()) {
                        if (wrapped.isRunning() && wrapped.getGoal() instanceof AdvancedAnimationAttackGoal<?>) {
                            wrapped.stop();
                        }
                    }
                    patch.cancelGuard();
                }
                case DIG -> {
                    AvNpcAnimationCompat.playDiggingAnimation(npc);
                    event.setCanceled(true);
                }
                case STOP_DIG -> AvNpcAnimationCompat.stopDiggingAnimation(npc);
                case USE -> {
                    if (AvNpcAnimationCompat.playMainHandUseAnimation(npc)) event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void onRigAnimation(AVNpcRigAnimationEvent event) {
            AVNpc npc = event.getNpc();
            AdvancedAvNpcPatch<?> patch = getPatch(npc);
            if (patch == null) return;

            var profile = RigCombatProfiles.getCombatProfile(npc);
            if (profile.containsAttack(event.getAnimationId())) {
                event.setCanceled(true);
                return;
            }

            for (var wrapped : npc.goalSelector.getAvailableGoals()) {
                if (wrapped.isRunning() && wrapped.getGoal() instanceof AdvancedAnimationAttackGoal<?>) {
                    wrapped.stop();
                }
            }
            patch.cancelGuard();

            switch (event.getAnimationId()) {
                case EAT_MAINHAND, EAT_OFFHAND -> {
                    AvNpcAnimationCompat.playEatingAnimation(npc);
                    event.setCanceled(true);
                }
                default -> {
                }
            }
        }
    }
}
