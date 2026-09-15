package com.pla.epicfight_annoyingvillagers.util;

import com.pla.annoyingvillagers.clazz.IdleAnimation;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEmote;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;

public final class IdleAnimationUtil {
    private IdleAnimationUtil() {}

    public static AssetAccessor<? extends StaticAnimation> resolveIdleAnimation(IdleAnimation idle) {
        if (idle == null) return null;
        return switch (idle) {
            case PUSH_UP -> AnimsEmote.PUSH_UP_EMOTE;
            case LAY -> AnimsEmote.LAY_EMOTE;
            case SLEEP -> AnimsEmote.DEATH_EMOTE;
            case SIT -> AnimsEmote.SIT_EMOTE;
            case SLIGHT -> AnimsEmote.SLIGHT_EMOTE;
            case LAY_RELAX_EMOTE -> AnimsEmote.LAY_RELAX_EMOTE;
            case ONE_ARM_LAY_EMOTE -> AnimsEmote.ONE_ARM_LAY_EMOTE;
            case SIT_NO_WEAPON_EMOTE -> AnimsEmote.SIT_NO_WEAPON_EMOTE;
            case SORROW_EMOTE -> AnimsEmote.SORROW_EMOTE;
            case FUN_JUMP_EMOTE -> AnimsEmote.FUN_JUMP_EMOTE;
            case JUMP_EMOTE -> AnimsEmote.JUMP_EMOTE;
            case PRONE_EMOTE -> AnimsEmote.PRONE_EMOTE;
        };
    }
}
