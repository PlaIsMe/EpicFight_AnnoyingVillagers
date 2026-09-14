package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.ChrisEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

@Mixin(value = ChrisEntity.class, remap = false)
public abstract class ChrisEntityMixin {
    @Inject(method = "addEpicFightAttributes", at = @At("HEAD"))
    private static void addEpicFightAttributes(Builder builder, CallbackInfoReturnable<Builder> cir) {
        builder.add(EpicFightAttributes.IMPACT.get(), 2.0D)
                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 5.0D)
                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 50.0D)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 30.0D)
                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);
    }
}
