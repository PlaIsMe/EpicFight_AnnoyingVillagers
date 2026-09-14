package com.pla.epicfight_annoyingvillagers.util;

import com.pla.annoyingvillagers.rig.RigAnimationId;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public final class EscapeAnimationCompat {
    private EscapeAnimationCompat() {}

    // Rig IDs remain the goal's forward/backward selection tokens; only EF plays them.
    public static AssetAccessor<? extends StaticAnimation> roll(RigAnimationId id) {
        if (id == RigAnimationId.ROLL_FORWARD) return Animations.BIPED_ROLL_FORWARD;
        if (id == RigAnimationId.ROLL_BACKWARD) return Animations.BIPED_ROLL_BACKWARD;
        return null;
    }

    public static int durationTicks(AssetAccessor<? extends StaticAnimation> animation) {
        return animation == null ? 0 : (int) Math.ceil(animation.get().getTotalTime() * 20.0F);
    }

    public static void play(LivingEntity mob, AssetAccessor<? extends StaticAnimation> animation) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class);
        if (patch != null && animation != null) patch.playAnimationSynchronized(animation, 0.0F);
    }

    public static void stop(LivingEntity mob, AssetAccessor<? extends StaticAnimation> animation) {
        if (!EpicfightUtil.isPlaying(mob, animation)) return;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class);
        boolean linking = patch.getAnimator().getPlayerFor(null).getAnimation().get().isLinkAnimation();
        if (linking) {
            // EF's stopPlaying terminates the link but leaves its destination queued.
            // A neutral replacement clears that destination on both server and client.
            patch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
        } else {
            EpicfightUtil.stopAnimationSynchronized(mob, animation);
        }
    }
}
