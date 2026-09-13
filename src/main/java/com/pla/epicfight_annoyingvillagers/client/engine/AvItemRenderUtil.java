package com.pla.epicfight_annoyingvillagers.client.engine;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

final class AvItemRenderUtil {
    private AvItemRenderUtil() {
    }

    /** Draw after applying the Epic Fight correction matrix, with entity and hand context. */
    static void renderItem(ItemStack stack, LivingEntityPatch<?> patch, InteractionHand hand,
                           PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        boolean leftHand = hand == InteractionHand.OFF_HAND;
        Minecraft.getInstance().gameRenderer.itemInHandRenderer.renderItem(patch.getOriginal(), stack,
                leftHand ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                leftHand, poseStack, buffer, packedLight);
    }
}
