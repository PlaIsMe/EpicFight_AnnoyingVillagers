package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.util.CommonUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.shelmarow.combat_evolution.effect.CEMobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.effect.EpicFightMobEffects;

@Mixin(value = CommonUtil.class, remap = false)
public abstract class CommonUtilMixin {
    @Inject(method = "stunImmunity", at = @At("HEAD"))
    private static void stunImmunity(Mob mob, int duration, int pAmplifier, CallbackInfo ci) {
        mob.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY.get(), duration, pAmplifier));
        mob.addEffect(new MobEffectInstance(CEMobEffects.FULL_STUN_IMMUNITY.get(), duration, pAmplifier));
    }
}
