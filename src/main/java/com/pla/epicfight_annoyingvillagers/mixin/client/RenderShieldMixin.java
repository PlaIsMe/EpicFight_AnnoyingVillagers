package com.pla.epicfight_annoyingvillagers.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.blaze3d.vertex.PoseStack;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderShield;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = RenderShield.class, remap = false)
public abstract class RenderShieldMixin {
    @ModifyExpressionValue(
            method = "renderItemInHand",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/ItemModelShaper;getItemModel(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/client/resources/model/BakedModel;",
                    remap = true)
    )
    private BakedModel resolveEnderAegisForm(BakedModel original, ItemStack stack, LivingEntityPatch<?> patch,
                                            InteractionHand hand, OpenMatrix4f[] poses, MultiBufferSource buffer,
                                            PoseStack poseStack, int packedLight, float partialTicks) {
        if (!(stack.getItem() instanceof EnderAegisItem)) {
            return original;
        }

        // RenderShield reads the base model directly, bypassing SecondForm and blocking overrides.
        return Minecraft.getInstance().getItemRenderer().getModel(
                stack, patch.getOriginal().level(), patch.getOriginal(), patch.getOriginal().getId());
    }
}
