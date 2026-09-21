package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.SledgehammerHerobrineEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.registry.entries.EpicFightAttributes;

@Mixin(value = SledgehammerHerobrineEntity.class, remap = false)
public abstract class SledgehammerHerobrineEntityMixin {
    @Inject(method = "addEpicFightAttributes", at = @At("HEAD"))
    private static void addEpicFightAttributes(Builder builder, CallbackInfoReturnable<Builder> cir) {
        builder.add(EpicFightAttributes.IMPACT, 4.0D)
                .add(EpicFightAttributes.ARMOR_NEGATION, 10.0D)
                .add(EpicFightAttributes.STUN_ARMOR, 20.0D)
                .add(EpicFightAttributes.MAX_STRIKES, 100.0D)
                .add(EpicFightAttributes.MAX_STAMINA, 60.0D)
                .add(EpicFightAttributes.STAMINA_REGEN, 1.5D);
    }
}
