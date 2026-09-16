package com.pla.epicfight_annoyingvillagers.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.pla.epicfight_annoyingvillagers.capabilities.WeaponCapabilityPresetTracking;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.api.data.reloader.ItemCapabilityReloadListener;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.function.Function;

@Mixin(value = ItemCapabilityReloadListener.class, remap = false)
public abstract class ItemCapabilityReloadListenerPresetMixin {
    @WrapOperation(
            method = "deserializeWeapon(Lnet/minecraft/world/item/Item;Lnet/minecraft/nbt/CompoundTag;Lyesman/epicfight/world/capabilities/provider/ExtraEntryProvider;)Lyesman/epicfight/world/capabilities/item/CapabilityItem;",
            at = @At(value = "INVOKE", target = "Ljava/util/function/Function;apply(Ljava/lang/Object;)Ljava/lang/Object;"),
            require = 2
    )
    private static Object annoyingvillagers$rememberPreset(
            Function<Item, CapabilityItem.Builder> preset, Object item, Operation<Object> original) {
        return WeaponCapabilityPresetTracking.recordBuilder(
                (CapabilityItem.Builder) original.call(preset, item), preset);
    }
}
