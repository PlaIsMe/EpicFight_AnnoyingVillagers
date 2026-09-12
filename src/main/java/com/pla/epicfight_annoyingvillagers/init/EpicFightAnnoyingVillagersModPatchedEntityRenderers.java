package com.pla.epicfight_annoyingvillagers.init;

import com.pla.annoyingvillagers.client.renderer.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import yesman.epicfight.api.client.forgeevent.PatchedRenderersEvent;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.client.renderer.patched.entity.PIllagerRenderer;

@EventBusSubscriber(bus = Bus.MOD, value = {Dist.CLIENT})
public class EpicFightAnnoyingVillagersModPatchedEntityRenderers {
    @SubscribeEvent
    public static void onPatchedRenderer(PatchedRenderersEvent.Add add) {
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.STEVE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.ANGRY_STEVE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.ALEX.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.ALEX, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.JEV.get(),
                (entitytype) -> (new PIllagerRenderer<>(add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.CHRIS.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.LOW_HEROBRINE_CLONE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.LOW_SHADOW_HEROBRINE_CLONE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.AEGIS_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.SWORDSMAN_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.GLAIVE_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.SLEDGEHAMMER_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.REAPER_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_SWORD.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_AXE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_PICKAXE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_SHOVEL.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_HOE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL_SKELETON.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.SKELETON, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.NULL.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_CLONE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.SHADOW_HEROBRINE_CLONE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.TRANSPORTER_HEROBRINE_CLONE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.ARMORED_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_7.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.SHADOW_HEROBRINE.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.BLUE_DEMON.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.HEROBRINE_GREG.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.INFECTED_PLAYER_NPC.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.INFECTED_THEMOSTMOISTBURRIT0.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.INFECTED_CHRIS.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
        add.addPatchedEntityRenderer(AnnoyingVillagersModEntities.ELITE_HEROBRINE_KNOCKED.get(),
                (entitytype) -> (new PHumanoidRenderer<>(Meshes.BIPED, add.getContext(), entitytype))
                        .initLayerLast(add.getContext(), entitytype));
    }
}
