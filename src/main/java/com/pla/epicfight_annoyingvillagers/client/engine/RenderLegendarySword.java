package com.pla.epicfight_annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsLegendarySword;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.component.CustomModelData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RenderLegendarySword extends RenderItemBase {

    public RenderLegendarySword(JsonElement json) {
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

            if (dynamicAnimation == AnimsLegendarySword.LEGENDARY_SWORD_INNATE) {
                itemstack = new ItemStack(AnnoyingVillagersModItems.HEAVY_ATTACK_LEGENDARY_SWORD.get());
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                AvItemRenderUtil.renderItem(itemstack, livingEntityPatch, hand, poseStack, buffer, packedLight);
                poseStack.popPose();
            } else {
                itemstack = stack;
                if (livingEntityPatch.getOriginal() instanceof AngrySteveEntity steve && steve.isLegendaryAwakened()) {
                    itemstack = stack.copy();
                    itemstack.set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1));
                }
                poseStack.pushPose();
                MathUtils.mulStack(poseStack, openmatrix4f);
                AvItemRenderUtil.renderItem(itemstack, livingEntityPatch, hand, poseStack, buffer, packedLight);
                poseStack.popPose();
            }
        }
    }
}
