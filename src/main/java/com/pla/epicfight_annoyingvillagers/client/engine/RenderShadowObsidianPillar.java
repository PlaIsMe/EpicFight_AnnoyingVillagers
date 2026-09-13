package com.pla.epicfight_annoyingvillagers.client.engine;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsObsidianWeapon;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class RenderShadowObsidianPillar extends RenderItemBase {

    public RenderShadowObsidianPillar(JsonElement json) {
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
            if (stack.is(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get())) {
                OpenMatrix4f openmatrix4fmainHand = new OpenMatrix4f(this.getCorrectionMatrix(livingEntityPatch, hand, poses));
                AnimationPlayer animationPlayer = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null));
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = animationPlayer.getRealAnimation();
                float elapsedTimeFloat = animationPlayer.getElapsedTime();
                EntityState entityState = (dynamicAnimation.get()).getState(livingEntityPatch, elapsedTimeFloat);
                ItemStack itemstack;

                if (dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_INNATE_SPECIAL
                        || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_TWOHAND_1
                        || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_3
                        || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_1
                        || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_TWOHAND_2
                        || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_DASH) {
                    itemstack = ItemStack.EMPTY;
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4fmainHand);
                    AvItemRenderUtil.renderItem(itemstack, livingEntityPatch, hand, poseStack, buffer, packedLight);
                    poseStack.popPose();
                } else if (((dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_4
                        || dynamicAnimation == AnimsObsidianWeapon.SHADOW_OBSIDIAN_PILLAR_SPECIAL
                        || dynamicAnimation.get() instanceof ExecutionAttackAnimation) && entityState.getLevel() > 1)
                        || dynamicAnimation == AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_DUAL_INNATE
                        || dynamicAnimation == AnimsObsidianWeapon.SHADOW_OBSIDIAN_PILLAR_DUAL_INNATE) {
                    itemstack = new ItemStack(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_BURST.get());
                    itemstack.getOrCreateTag().putBoolean("foil", stack.isEnchanted());
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4fmainHand);
                    AvItemRenderUtil.renderItem(itemstack, livingEntityPatch, hand, poseStack, buffer, packedLight);
                    poseStack.popPose();
                } else if ((dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_LEFT_3
                        || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_DASH
                        || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_1
                        || dynamicAnimation == AnimsObsidianWeapon.OBSIDIAN_WEAPON_RIGHT_3)
                        && entityState.getLevel() > 1) {
                    itemstack = stack;
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4fmainHand);
                    AvItemRenderUtil.renderItem(itemstack, livingEntityPatch, hand, poseStack, buffer, packedLight);
                    poseStack.popPose();

                } else {
                    itemstack = stack;
                    poseStack.pushPose();
                    MathUtils.mulStack(poseStack, openmatrix4fmainHand);
                    AvItemRenderUtil.renderItem(itemstack, livingEntityPatch, hand, poseStack, buffer, packedLight);
                    poseStack.popPose();

                }
            }
        }
    }
}
