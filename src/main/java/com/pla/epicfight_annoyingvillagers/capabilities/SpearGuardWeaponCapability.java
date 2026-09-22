package com.pla.epicfight_annoyingvillagers.capabilities;

import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

public final class SpearGuardWeaponCapability extends WeaponCapability {
    public SpearGuardWeaponCapability(Builder builder) {
        super(builder);
    }

    @Override
    public AnimationManager.AnimationAccessor<? extends StaticAnimation> getGuardMotion(
            GuardSkill guardSkill,
            GuardSkill.BlockType blockType,
            PlayerPatch<?> playerPatch) {
        AnimationManager.AnimationAccessor<? extends StaticAnimation> guardMotion =
                super.getGuardMotion(guardSkill, blockType, playerPatch);
        if (guardMotion != null) {
            return guardMotion;
        }

        return blockType == GuardSkill.BlockType.GUARD
                || blockType == GuardSkill.BlockType.ADVANCED_GUARD
                ? Animations.SPEAR_GUARD_HIT
                : null;
    }
}
