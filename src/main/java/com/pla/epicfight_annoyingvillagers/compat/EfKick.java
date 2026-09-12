package com.pla.epicfight_annoyingvillagers.compat;

import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.efkick.animations.KickAttackAnimation;
import com.pla.efkick.config.EFKickConfig;
import net.minecraft.world.damagesource.DamageSource;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class EfKick {
    public static void tryDealKickStaminaDamage(
            DamageSource damageSource,
            LivingEntityPatch<?> attackerLivingEntityPatch,
            AssetAccessor<? extends StaticAnimation> attackerDynamicAnimation
    ) {
        if (attackerDynamicAnimation != null && attackerDynamicAnimation.get() instanceof KickAttackAnimation) {
            EpicfightUtil.dealStaminaDamageByPercentage(
                    damageSource,
                    attackerLivingEntityPatch,
                    EFKickConfig.KICK_STAMINA_DECREASE_PERCENTAGE.get(),
                    true
            );
        }
    }
}
