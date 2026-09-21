package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.SteveEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.registry.entries.EpicFightAttributes;

@Mixin(value = SteveEntity.class, remap = false)
public abstract class SteveEntityMixin {
    @Inject(method = "addEpicFightAttributes", at = @At("HEAD"))
    private static void addEpicFightAttributes(Builder builder, CallbackInfoReturnable<Builder> cir) {
        builder.add(EpicFightAttributes.IMPACT, 2.0D)
                .add(EpicFightAttributes.ARMOR_NEGATION, 5.0D)
                .add(EpicFightAttributes.STUN_ARMOR, 20.0D)
                .add(EpicFightAttributes.MAX_STRIKES, 50.0D)
                .add(EpicFightAttributes.MAX_STAMINA, 30.0D)
                .add(EpicFightAttributes.STAMINA_REGEN, 1.5D);
    }
}
