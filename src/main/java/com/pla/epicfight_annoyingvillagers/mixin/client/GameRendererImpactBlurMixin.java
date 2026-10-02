package com.pla.epicfight_annoyingvillagers.mixin.client;

import com.pla.epicfight_annoyingvillagers.client.shader.ImpactBlurShaderManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GameRenderer.class, priority = 1500)
public class GameRendererImpactBlurMixin {
    @Inject(method = "renderLevel(Lnet/minecraft/client/DeltaTracker;)V", at = @At("TAIL"), require = 1)
    private void renderImpactBlur(DeltaTracker deltaTracker, CallbackInfo ci) {
        if (!ImpactBlurShaderManager.isInitialized()) {
            ImpactBlurShaderManager.init();
        }

        ImpactBlurShaderManager.render(Minecraft.getInstance().levelRenderer.getTicks(),
                deltaTracker.getGameTimeDeltaPartialTick(true));
    }
}
