package com.pla.epicfight_annoyingvillagers.mobpatch;

import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedCombatBehaviors;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVSword;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsLegendarySword;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

public class AngryStevePatch extends FullDodgeAvNpcPatch<AngrySteveEntity> {
    @Override
    protected void addCustomBehaviorRoots(AdvancedCombatBehaviors.Builder<MobPatch<?>> builder,
                                          CapabilityItem mainHandCap,
                                          CapabilityItem offHandCap,
                                          Style style) {
        super.addCustomBehaviorRoots(builder, mainHandCap, offHandCap, style);
        builder.newBehaviorRoot(AdvancedCombatBehaviors.BehaviorRoot.builder()
                .priority(1.0D)
                .weight(1.0D)
                .maxCooldown(200)
                .waitForAnimationCompletion()
                .addFirstBehavior(AdvancedCombatBehaviors.Behavior.builder()
                        .withinDistance(0.0D, 6.0D)
                        .custom(patch -> patch.getOriginal() instanceof AngrySteveEntity steve
                                && steve.getMainHandItem().is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())
                                && !steve.isLegendaryAwakened())
                        .animationBehavior(AnimsLegendarySword.LEGENDARY_SWORD_INNATE_SPECIAL, 0.0F)));

        builder.newBehaviorRoot(AdvancedCombatBehaviors.BehaviorRoot.builder()
                .priority(1.0D)
                .weight(1.0D)
                .maxCooldown(80)
                .waitForAnimationCompletion()
                .addFirstBehavior(AdvancedCombatBehaviors.Behavior.builder()
                        .withinDistance(0.0D, 12.0D)
                        .custom(patch -> patch.getOriginal() instanceof AngrySteveEntity steve
                                && steve.isLegendaryAwakened()
                                && steve.getMainHandItem().is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())
                                && steve.getOffhandItem().is(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get()))
                        .animationBehavior(AnimsAVSword.WOOPIE_INNATE_SPECIAL_LEGENDARY, 0.0F)));
    }
}
