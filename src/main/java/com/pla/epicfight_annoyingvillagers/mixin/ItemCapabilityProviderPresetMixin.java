package com.pla.epicfight_annoyingvillagers.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.pla.epicfight_annoyingvillagers.capabilities.WeaponCapabilityPresetTracking;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.provider.CommonItemCapabilityProvider;

import java.util.function.Function;

@Mixin(value = CommonItemCapabilityProvider.class, remap = false)
public abstract class ItemCapabilityProviderPresetMixin {
    @WrapOperation(
            method = "lambda$addDefaultItems$1",
            at = @At(value = "INVOKE", target = "Ljava/util/function/Function;apply(Ljava/lang/Object;)Ljava/lang/Object;"),
            require = 1
    )
    private static Object annoyingvillagers$rememberPreset(
            Function<Item, ? extends CapabilityItem.Builder<?>> preset, Object item, Operation<Object> original) {
        return WeaponCapabilityPresetTracking.recordBuilder(
                (CapabilityItem.Builder<?>) original.call(preset, item), preset);
    }
}
