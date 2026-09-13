package com.pla.epicfight_annoyingvillagers.client.renderer;

import com.pla.annoyingvillagers.client.layer.RigArmorLayer;
import com.pla.annoyingvillagers.client.layer.RigArrowLayer;
import com.pla.annoyingvillagers.client.layer.RigItemInHandLayer;
import com.pla.annoyingvillagers.client.layer.VillagerRigArrowLayer;
import com.pla.annoyingvillagers.client.layer.VillagerRigItemInHandLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.client.renderer.patched.layer.PatchedItemInHandLayer;
import yesman.epicfight.client.renderer.patched.layer.WearableItemLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class AvHumanoidRenderer<E extends LivingEntity, T extends LivingEntityPatch<E>,
        M extends HumanoidModel<E>, R extends LivingEntityRenderer<E, M>, AM extends HumanoidMesh>
        extends PHumanoidRenderer<E, T, M, R, AM> {
    public AvHumanoidRenderer(AssetAccessor<AM> mesh, EntityRendererProvider.Context context, EntityType<?> type) {
        super(mesh, context, type);

        // Epic Fight looks up exact layer classes. Register these before initLayerLast
        // can wrap them in RenderOriginalModelLayer with the independent rig pose.
        this.addPatchedLayer(RigItemInHandLayer.class, new PatchedItemInHandLayer<>());
        this.addPatchedLayer(VillagerRigItemInHandLayer.class, new PatchedItemInHandLayer<>());
        this.addPatchedLayer(RigArmorLayer.class, new WearableItemLayer<>(mesh, false, context.getModelManager()));
        this.addPatchedLayer(RigArrowLayer.class, new AvRigArrowLayer<>(context));
        this.addPatchedLayer(VillagerRigArrowLayer.class, new AvRigArrowLayer<>(context));
    }
}
