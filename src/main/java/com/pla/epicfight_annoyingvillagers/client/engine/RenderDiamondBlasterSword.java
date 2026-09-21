package com.pla.epicfight_annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVSword;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RenderDiamondBlasterSword extends RenderItemBase {
    public RenderDiamondBlasterSword(JsonElement json) {
        super(json);
    }

    @Override
    public void renderItemInHand(ItemStack stack,
                                 LivingEntityPatch<?> livingEntityPatch,
                                 InteractionHand hand,
                                 OpenMatrix4f[] poses,
                                 MultiBufferSource buffer,
                                 PoseStack poseStack,
                                 int packedLight,
                                 float partialTicks) {
        if (livingEntityPatch != null) {
            OpenMatrix4f openmatrix4f = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, hand, poses));
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            ItemStack itemstack;

            if (dynamicAnimation != AnimsAVSword.DIAMOND_BLASTER_INNATE) {
                itemstack = stack;
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                AvItemRenderUtil.renderItem(itemstack, livingEntityPatch, hand, poseStack, buffer, packedLight);
                poseStack.popPose();
            } else {
                itemstack = new ItemStack(AnnoyingVillagersModItems.DIAMOND_BLASTER_SWORD_ABILITY.get());
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                AvItemRenderUtil.renderItem(itemstack, livingEntityPatch, hand, poseStack, buffer, packedLight);
                poseStack.popPose();
            }
        }
    }
}
