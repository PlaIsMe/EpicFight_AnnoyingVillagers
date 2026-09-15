package com.pla.epicfight_annoyingvillagers.util;

import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;

import java.util.Map;
import java.util.WeakHashMap;

/** Server-only cast identity; elapsed animation time is not a stable start tick. */
public final class ReactionAnimationTracker {
    private static final Map<LivingEntity, StartedAnimation> STARTS = new WeakHashMap<>();

    private ReactionAnimationTracker() {}

    public static void began(LivingEntity entity, AssetAccessor<? extends StaticAnimation> animation) {
        if (animation == null) {
            STARTS.remove(entity);
            return;
        }
        STARTS.put(entity, new StartedAnimation(animation, entity.tickCount));
    }

    public static int startTick(LivingEntity entity, AssetAccessor<? extends StaticAnimation> animation) {
        StartedAnimation started = STARTS.get(entity);
        return started != null && started.animation().equals(animation) ? started.tick() : -1;
    }

    private record StartedAnimation(AssetAccessor<? extends StaticAnimation> animation, int tick) {}
}
