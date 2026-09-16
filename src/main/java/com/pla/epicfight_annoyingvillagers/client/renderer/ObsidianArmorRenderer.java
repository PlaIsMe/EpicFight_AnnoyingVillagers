package com.pla.epicfight_annoyingvillagers.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.pla.annoyingvillagers.client.renderer.ObsidianArmorExtensionRenderer;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

/** Animated tiles remain dynamic while Epic Fight owns the armor shell and body pose. */
@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ObsidianArmorRenderer {
    private ObsidianArmorRenderer() {}

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ObsidianArmorExtensionRenderer.setFirstPersonBackend(ObsidianArmorRenderer::renderFirstPerson));
    }

    public static void render(LivingEntityPatch<?> patch, PoseStack stack, MultiBufferSource buffer,
                              int light, OpenMatrix4f[] poses) {
        if (!(patch.getArmature() instanceof HumanoidArmature armature)
                || !ObsidianArmorExtensionRenderer.hasExtensions(patch.getOriginal())) return;
        ObsidianArmorExtensionRenderer.render(patch.getOriginal(), stack, buffer, light, null, (pose, anchor) -> {
            Joint joint = switch (anchor) {
                case HEAD -> armature.head;
                case RIGHT_ARM -> armature.armR;
                default -> armature.chest;
            };
            // Match mesh skinning: animated joint * inverse bind pose, then the
            // vanilla-model-to-Epic-Fight conversion used by VanillaModelTransformer.
            MathUtils.mulStack(pose, new OpenMatrix4f(poses[joint.getId()]).mulBack(joint.getToOrigin()));
            pose.translate(0.0D, 1.5D, 0.0D);
            pose.scale(-1.0F, -1.0F, 1.0F);
        });
    }

    private static boolean renderFirstPerson(LivingEntity wearer, PoseStack stack, MultiBufferSource buffer,
                                             int light, float partialTick) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(wearer, LivingEntityPatch.class);
        if (patch == null || !(patch.getArmature() instanceof HumanoidArmature)) return false;
        stack.pushPose();
        try {
            stack.mulPose(Axis.YP.rotationDegrees(180.0F));
            MathUtils.mulStack(stack, patch.getModelMatrix(partialTick));
            var poses = patch.getArmature().getPoseAsTransformMatrix(patch.getAnimator().getPose(partialTick), false);
            render(patch, stack, buffer, light, poses);
        } finally {
            stack.popPose();
        }
        return true;
    }
}
