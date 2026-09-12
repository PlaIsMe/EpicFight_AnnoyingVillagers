package com.pla.epicfight_annoyingvillagers.init;

import com.pla.epicfight_annoyingvillagers.compat.epicfight.AvNpcPatchRegistration;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;

/** The core owns native rig combat; this companion installs the shared AvNpc Epic Fight engine. */
@Mod.EventBusSubscriber(modid = "epicfight_annoyingvillagers", bus = Mod.EventBusSubscriber.Bus.MOD)
public final class EpicFightAnnoyingVillagersModPatchEntities {
    @SubscribeEvent
    public static void setPatch(EntityPatchRegistryEvent event) {
        AvNpcPatchRegistration.register(event);
    }
}
