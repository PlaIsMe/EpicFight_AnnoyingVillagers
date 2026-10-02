package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.util.CommonUtil;
import com.pla.epicfight_annoyingvillagers.compat.combat_evolution.CombatEvolution;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.neoforged.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.registry.entries.EpicFightMobEffects;

@Mixin(value = CommonUtil.class, remap = false)
public abstract class CommonUtilMixin {
    @Inject(method = "stunImmunity", at = @At("HEAD"))
    private static void stunImmunity(Mob mob, int duration, int pAmplifier, CallbackInfo ci) {
        if (ModList.get().isLoaded("annoyingvillagers")) {
            CombatEvolution.addFullStunImmunity(mob, duration, pAmplifier);
        }
        mob.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY, duration, pAmplifier));
    }
}
