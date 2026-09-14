package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsBlueDemonTrident;
import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

@Mixin(value = BlueDemonEntity.class, remap = false)
public abstract class BlueDemonEntityMixin {
    @Inject(method = "playFinalDeathAnimation", at = @At("HEAD"), cancellable = true)
    private void playFinalDeathAnimation(CallbackInfo ci) {
        BlueDemonEntity self = (BlueDemonEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            if (self.getMainHandItem().getItem() instanceof BlueDemonTridentItem) {
                patch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_DIE, 0.0F);
            } else {
                patch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_DIE_LEGENDARY_SWORD_START, 0.0F);
            }
        }
        ci.cancel();
    }

    @Inject(method = "playDeathAnimation", at = @At("HEAD"), cancellable = true)
    private void playDeathAnimation(CallbackInfo ci) {
        BlueDemonEntity self = (BlueDemonEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null && (self.dieTick <= 180 && self.dieTick % 10 == 0)) {
            if (self.getMainHandItem().getItem() instanceof BlueDemonTridentItem) {
                patch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_STATE_TRANSFORM, 0.0F);
            } else {
                patch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_DIE_LEGENDARY_SWORD_TICK, 0.0F);
            }
        }
        ci.cancel();
    }

    @Inject(method = "playStateTransformAnimation", at = @At("HEAD"), cancellable = true)
    private void playStateTransformAnimation(CallbackInfo ci) {
        BlueDemonEntity self = (BlueDemonEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            patch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_STATE_TRANSFORM, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "playStateTransformEndAnimation", at = @At("HEAD"), cancellable = true)
    private void playStateTransformEndAnimation(CallbackInfo ci) {
        BlueDemonEntity self = (BlueDemonEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            patch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_STATE_TRANSFORM_END, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "playTridentFestivalAnimation", at = @At("HEAD"), cancellable = true)
    private void playTridentFestivalAnimation(CallbackInfo ci) {
        BlueDemonEntity self = (BlueDemonEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            patch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_FESTIVAL, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "addEpicFightAttributes", at = @At("HEAD"))
    private static void addEpicFightAttributes(Builder builder, CallbackInfoReturnable<Builder> cir) {
        builder.add(EpicFightAttributes.IMPACT.get(), 4.0D)
                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 10.0D)
                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 100.0D)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 60.0D)
                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);
    }

    @Inject(method = "conditionToAbsorbNearbyGroundedTridents", at = @At("HEAD"), cancellable = true)
    private void conditionToAbsorbNearbyGroundedTridents(CallbackInfoReturnable<Boolean> cir) {
        BlueDemonEntity self = (BlueDemonEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (dynamicAnimation != AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THUNDER_ATTACK && dynamicAnimation != AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_FESTIVAL) {
                cir.setReturnValue(true);
                return;
            }
        }
        cir.setReturnValue(false);
        return;
    }

    // hurt supplies the damage source omitted from the AV predicate signature.
    @Redirect(method = "hurt", remap = true,
            at = @At(value = "INVOKE", target = "Lcom/pla/annoyingvillagers/entity/BlueDemonEntity;ignoreDamageForSomeEpicFightAnimation()Z", remap = false))
    private boolean ignoreDamageForSomeEpicFightAnimation(BlueDemonEntity self, DamageSource damagesource, float amount) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (self.level() instanceof ServerLevel serverLevel && patch != null && self.dieTick <= 0) {
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(patch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THUNDER_ATTACK
                    || dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_ELECTRIC_FIELD
                    || dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_FESTIVAL
                    || dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_STATE_TRANSFORM
                    || dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_STATE_TRANSFORM_END) {
                EpicFightParticles.HIT_BLUNT.get().spawnParticleWithArgument(serverLevel, HitParticleType.FRONT_OF_EYES, HitParticleType.ZERO,
                        self, damagesource.getEntity());
                return false;
            }
        }
        return true;
    }
}
