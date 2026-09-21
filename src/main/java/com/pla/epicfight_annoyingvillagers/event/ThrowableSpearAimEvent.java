package com.pla.epicfight_annoyingvillagers.event;

import com.pla.annoyingvillagers.clazz.ThrowableSpearItem;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.client.event.EpicFightClientEventHooks;
import yesman.epicfight.api.client.event.types.entity.ModifyPlayerLivingMotionEvent;

public final class ThrowableSpearAimEvent {
    private ThrowableSpearAimEvent() {
    }

    public static void register() {
        EpicFightClientEventHooks.Entity.MODIFY_PLAYER_LIVING_MOTION_COMPOSITE.registerEvent(
                ThrowableSpearAimEvent::onCompositeMotion, "epicfight_annoyingvillagers:throwable_spear_aim");
    }

    public static void onCompositeMotion(ModifyPlayerLivingMotionEvent.CompositeLayer event) {
        LivingEntity livingEntity = event.getPlayerPatch().getOriginal();

        if (livingEntity.isUsingItem() && livingEntity.getUseItem().getItem() instanceof ThrowableSpearItem) {
            event.setMotion(LivingMotions.AIM);
        }
    }
}
