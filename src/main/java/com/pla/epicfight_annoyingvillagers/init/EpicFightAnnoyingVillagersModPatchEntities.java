package com.pla.epicfight_annoyingvillagers.init;

import com.pla.annoyingvillagers.clazz.AVNpc;
import com.pla.annoyingvillagers.event.AVNpcRecoveryEvent;
import com.pla.annoyingvillagers.event.AVNpcRigAnimationEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.annoyingvillagers.rig.RigCombatProfiles;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAnimationAttackGoal;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAvNpcPatch;
import com.pla.epicfight_annoyingvillagers.util.AvNpcAnimationCompat;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.mob.SkeletonPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class EpicFightAnnoyingVillagersModPatchEntities {
    private EpicFightAnnoyingVillagersModPatchEntities() {
    }

    @SubscribeEvent
    public static void setPatch(EntityPatchRegistryEvent event) {
        for (EntityType<? extends LivingEntity> type : advancedNpcTypes()) {
            event.getTypeEntry().put(type, entity -> AdvancedAvNpcPatch::new);
        }
        event.getTypeEntry().put(AnnoyingVillagersModEntities.NULL_SKELETON.get(), entity -> SkeletonPatch::new);
    }

    @SubscribeEvent
    public static void addEpicFightAttributes(EntityAttributeModificationEvent event) {
        List<EntityType<? extends LivingEntity>> patchedTypes = new ArrayList<>(advancedNpcTypes());
        patchedTypes.add(AnnoyingVillagersModEntities.NULL_SKELETON.get());
        for (EntityType<? extends LivingEntity> type : patchedTypes) {
            event.add(type, EpicFightAttributes.WEIGHT.get());
            event.add(type, EpicFightAttributes.ARMOR_NEGATION.get());
            event.add(type, EpicFightAttributes.IMPACT.get());
            event.add(type, EpicFightAttributes.MAX_STRIKES.get());
            event.add(type, EpicFightAttributes.STUN_ARMOR.get());
            event.add(type, EpicFightAttributes.OFFHAND_ATTACK_SPEED.get());
            event.add(type, EpicFightAttributes.OFFHAND_MAX_STRIKES.get());
            event.add(type, EpicFightAttributes.OFFHAND_ARMOR_NEGATION.get());
            event.add(type, EpicFightAttributes.OFFHAND_IMPACT.get());
            event.add(type, EpicFightAttributes.MAX_STAMINA.get());
            event.add(type, EpicFightAttributes.STAMINA_REGEN.get());
        }
    }

    private static List<EntityType<? extends LivingEntity>> advancedNpcTypes() {
        return List.of(
                AnnoyingVillagersModEntities.ALEX.get(),
                AnnoyingVillagersModEntities.JEV.get(),
                AnnoyingVillagersModEntities.STEVE.get(),
                AnnoyingVillagersModEntities.ANGRY_STEVE.get(),
                AnnoyingVillagersModEntities.CHRIS.get(),
                AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(),
                AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(),
                AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(),
                AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(),
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

    @Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
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
