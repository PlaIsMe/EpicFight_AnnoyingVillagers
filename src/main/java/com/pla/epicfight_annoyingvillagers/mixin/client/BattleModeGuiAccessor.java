package com.pla.epicfight_annoyingvillagers.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import yesman.epicfight.client.gui.BattleModeGui;

@Mixin(value = BattleModeGui.class, remap = false)
public interface BattleModeGuiAccessor {
    @Invoker("getSliding")
    float epicFightAnnoyingVillagers$getSliding(float partialTick);
}
