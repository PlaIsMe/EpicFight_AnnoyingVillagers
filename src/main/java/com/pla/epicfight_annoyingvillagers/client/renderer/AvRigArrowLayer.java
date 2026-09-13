package com.pla.epicfight_annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.layer.PatchedArrowLayer;
import yesman.epicfight.client.renderer.patched.layer.PatchedLayer;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

/** The rig arrow layers are RenderLayers, not vanilla ArrowLayers. */
public class AvRigArrowLayer<E extends LivingEntity, T extends LivingEntityPatch<E>, M extends EntityModel<E>>
        extends PatchedLayer<E, T, M, RenderLayer<E, M>> {
    private final PatchedArrowLayer<E, T, PlayerModel<E>> arrows;

    public AvRigArrowLayer(EntityRendererProvider.Context context) {
        this.arrows = new PatchedArrowLayer<>(context);
    }

    @Override
    protected void renderLayer(T patch, E entity, RenderLayer<E, M> originalLayer, PoseStack poseStack,
                               MultiBufferSource buffer, int packedLight, OpenMatrix4f[] poses,
                               float bob, float yRot, float xRot, float partialTicks) {
        // PatchedArrowLayer only needs the entity and Epic Fight joints. Passing null
        // avoids its bridge casting a rig layer to vanilla StuckInBodyLayer.
        this.arrows.renderLayer(entity, patch, null, poseStack, buffer, packedLight, poses,
                bob, yRot, xRot, partialTicks);
    }
}
