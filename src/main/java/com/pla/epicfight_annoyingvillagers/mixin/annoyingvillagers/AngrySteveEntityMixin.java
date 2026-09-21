package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsLegendarySword;
import java.util.Objects;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.registry.entries.EpicFightAttributes;

@Mixin(value = AngrySteveEntity.class, remap = false)
public abstract class AngrySteveEntityMixin {
    @Inject(method = "playGuardBreakAttackAnimation", at = @At("HEAD"), cancellable = true)
    private void playGuardBreakAttackAnimation(CallbackInfo ci) {
        AngrySteveEntity self = (AngrySteveEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            patch.playAnimationSynchronized(AVAnimations.STUN_BACK, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "playTriedAnimation", at = @At("HEAD"), cancellable = true)
    private void playTriedAnimation(CallbackInfo ci) {
        AngrySteveEntity self = (AngrySteveEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        Objects.requireNonNull(patch).playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_KNOCKDOWN, 0.0F);
        ci.cancel();
    }

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
