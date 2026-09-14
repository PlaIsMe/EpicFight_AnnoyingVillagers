package com.pla.epicfight_annoyingvillagers.mixin;

import com.pla.annoyingvillagers.client.layer.HumanoidMobVanillaLayer;
import com.pla.epicfight_annoyingvillagers.client.overlaylayer.HumanoidMobEpicFightOverlayLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.client.renderer.patched.entity.PHumanoidRenderer;
import yesman.epicfight.client.renderer.patched.entity.PatchedLivingEntityRenderer;

@Mixin(value = PHumanoidRenderer.class, remap = false)
public abstract class MixinPHumanoidRenderer {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addHerobrineEye(AssetAccessor<?> mesh,
                                 EntityRendererProvider.Context ctx,
                                 EntityType<?> type,
                                 CallbackInfo ci) {
        PatchedLivingEntityRenderer<?, ?, ?, ?, ?> self =
                (PatchedLivingEntityRenderer<?, ?, ?, ?, ?>)(Object)this;
        // AV already attaches HumanoidMobVanillaLayer to the original renderer.
        // Replace that layer with its skinned-mesh equivalent instead of adding a
        // second eye pass (which is especially visible on Null's white eyes).
        self.addPatchedLayer(
                HumanoidMobVanillaLayer.class,
                new HumanoidMobEpicFightOverlayLayer<>((AssetAccessor) mesh)
        );
    }
}
