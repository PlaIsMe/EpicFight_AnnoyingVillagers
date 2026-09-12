package com.pla.epicfight_annoyingvillagers.compat.epicfight;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModEntities;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.forgeevent.EntityPatchRegistryEvent;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import java.util.List;

@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class AvNpcPatchRegistration {
    private static List<EntityType<? extends LivingEntity>> types() {
        return List.of(AnnoyingVillagersModEntities.ALEX.get(), AnnoyingVillagersModEntities.JEV.get(),
                AnnoyingVillagersModEntities.STEVE.get(), AnnoyingVillagersModEntities.ANGRY_STEVE.get(),
                AnnoyingVillagersModEntities.CHRIS.get(), AnnoyingVillagersModEntities.VILLAGER_SCOUT.get(),
                AnnoyingVillagersModEntities.VILLAGER_SCOUT_CAPTAIN.get(),
                AnnoyingVillagersModEntities.RED_VILLAGER_KNIGHT.get(), AnnoyingVillagersModEntities.BLUE_VILLAGER_KNIGHT.get(),
                AnnoyingVillagersModEntities.GREEN_VILLAGER_KNIGHT.get(), AnnoyingVillagersModEntities.PURPLE_VILLAGER_KNIGHT.get());
    }

    public static void register(EntityPatchRegistryEvent event) {
        for (var type : types()) event.getTypeEntry().put(type, entity -> AdvancedAvNpcPatch::new);
    }

    @SubscribeEvent
    public static void attributes(EntityAttributeModificationEvent event) {
        for (var type : types()) {
            event.add(type, EpicFightAttributes.WEIGHT.get());
            event.add(type, EpicFightAttributes.ARMOR_NEGATION.get());
            event.add(type, EpicFightAttributes.IMPACT.get());
            event.add(type, EpicFightAttributes.MAX_STRIKES.get());
            event.add(type, EpicFightAttributes.STUN_ARMOR.get());
            event.add(type, EpicFightAttributes.OFFHAND_ATTACK_SPEED.get());
            event.add(type, EpicFightAttributes.OFFHAND_MAX_STRIKES.get());
            event.add(type, EpicFightAttributes.OFFHAND_ARMOR_NEGATION.get());
            event.add(type, EpicFightAttributes.OFFHAND_IMPACT.get());
            event.add(type, EpicFightAttributes.MAX_STAMINA.get());
            event.add(type, EpicFightAttributes.STAMINA_REGEN.get());
        }
    }
}
