package com.pla.epicfight_annoyingvillagers.mixin.compat.smartnpc;

import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAvNpcPatch;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsKick;
import com.pla.smart_npc.compat.epicfight.AdvancedPlayerNpcPatch;
import com.pla.smart_npc.compat.epicfight.WeaponCapabilityPresetTracking;
import com.pla.smart_npc.compat.epicfight.advancedmobpatch.AdvancedCombatBehaviors;
import com.pla.smart_npc.compat.epicfight.advancedmobpatch.AdvancedMobPatch.AdditionalAttackGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.List;

@Mixin(value = AdvancedPlayerNpcPatch.class, remap = false)
public abstract class AdvancedPlayerNpcPatchMixin {
    @Inject(method = "addCustomBehaviorRoots", at = @At("TAIL"))
    private void addAvKickBehaviorRoot(
            AdvancedCombatBehaviors.Builder<MobPatch<?>> builder,
            CapabilityItem mainHandCap,
            CapabilityItem offHandCap,
            Style style,
            CallbackInfo ci
    ) {
        builder.newBehaviorRoot(
                AdvancedCombatBehaviors.BehaviorRoot.<MobPatch<?>>builder()
                        .priority(1.0D)
                        .weight(5.0D)
                        .maxCooldown(200)
                        .waitForAnimationCompletion()
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.<MobPatch<?>>builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(AnimsKick.KICK_1, 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.<MobPatch<?>>builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(AnimsKick.KICK_2, 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.<MobPatch<?>>builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(AnimsKick.KICK_3, 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.<MobPatch<?>>builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(AnimsKick.KICK_4, 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.<MobPatch<?>>builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(AnimsKick.KICK_H, 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.<MobPatch<?>>builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(AnimsKick.KICK_C, 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.<MobPatch<?>>builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(AnimsKick.KICK_RUSH, 0.0F)
                        )
                        .addFirstBehavior(
                                AdvancedCombatBehaviors.Behavior.<MobPatch<?>>builder()
                                        .withinDistance(0.0D, 3.0D)
                                        .animationBehavior(AnimsKick.KICK_COMBO, 0.0F)
                        )
        );
    }

    @Inject(method = "getAdditionalAttackGroups", at = @At("RETURN"), cancellable = true)
    private void addAvModAttackGroups(
            CapabilityItem mainHandCap,
            CapabilityItem offHandCap,
            Style style,
            CallbackInfoReturnable<List<AdditionalAttackGroup>> cir
    ) {
        List<AdditionalAttackGroup> originalGroups = cir.getReturnValue();
        cir.setReturnValue(AdvancedAvNpcPatch.addAvModAttackGroups(
                item -> (CapabilityItem.Builder<?>) WeaponCapabilityPresetTracking.getPreset(mainHandCap).apply(item),
                style,
                (chance, animations) -> AdditionalAttackGroup.random(chance, animations),
                () -> originalGroups
        ));
    }
}
