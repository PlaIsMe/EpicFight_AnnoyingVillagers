package com.pla.epicfight_annoyingvillagers.compat.efkick;

import com.pla.efkick.animations.KickAttackAnimation;
import com.pla.efkick.config.EFKickConfig;
import com.pla.efkick.gameasset.EFKickAnimations;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.damagesource.DamageSource;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public final class EFKickCompat {
    private EFKickCompat() {
    }

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

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation> getEFKick1() {
        return EFKickAnimations.KICK_1;
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation> getEFKick2() {
        return EFKickAnimations.KICK_2;
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation> getEFKick3() {
        return EFKickAnimations.KICK_3;
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation> getEFKick4() {
        return EFKickAnimations.KICK_4;
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation> getEFKickH() {
        return EFKickAnimations.KICK_H;
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation> getEFKickC() {
        return EFKickAnimations.KICK_C;
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation> getEFKickRush() {
        return EFKickAnimations.KICK_RUSH;
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation> getEFKickCombo() {
        return EFKickAnimations.KICK_COMBO;
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] kickAnimations() {
        return animations(
                getEFKick1(), getEFKick2(), getEFKick3(), getEFKick4(),
                getEFKickH(), getEFKickC(), getEFKickRush(), getEFKickCombo()
        );
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] fistKickAnimations() {
        return kickAnimations();
    }

    public static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] basicKickAnimations() {
        return animations(getEFKick1(), getEFKick2(), getEFKick3(), getEFKick4());
    }

    @SafeVarargs
    private static AnimationManager.AnimationAccessor<? extends StaticAnimation>[] animations(
            AnimationManager.AnimationAccessor<? extends StaticAnimation>... animations
    ) {
        return animations;
    }
}
