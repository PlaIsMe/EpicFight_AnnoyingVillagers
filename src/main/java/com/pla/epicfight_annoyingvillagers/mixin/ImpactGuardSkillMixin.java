package com.pla.epicfight_annoyingvillagers.mixin;

import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.epicfight_annoyingvillagers.skill.EnderAegisSkill;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.skill.guard.ImpactGuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.api.event.types.entity.TakeDamageEvent;

@Mixin(value = {ImpactGuardSkill.class}, remap = false)
public abstract class ImpactGuardSkillMixin {
    private static boolean isAdvancedBlockableDamageSource(DamageSource damageSource) {
        return damageSource.is(DamageTypeTags.IS_EXPLOSION) || damageSource.is(DamageTypes.MAGIC) || damageSource.is(DamageTypeTags.IS_FIRE) || damageSource.is(DamageTypeTags.IS_PROJECTILE) || damageSource.is(DamageTypeTags.BYPASSES_ARMOR);
    }

    @Inject(method = {"dealEvent"}, at = {@At("HEAD")}, cancellable = true)
    private void playerOnGuard(PlayerPatch<?> playerpatch, TakeDamageEvent.Income event, boolean advanced, CallbackInfo ci) {
        boolean isSpecialSource = isAdvancedBlockableDamageSource(event.getDamageSource());
        if (!isSpecialSource) {
            Player player = playerpatch.getOriginal();
            if (!(player instanceof ServerPlayer serverPlayer)) return;

            ItemStack main = serverPlayer.getMainHandItem();
            if (main.getItem() instanceof EnderAegisItem) {
                if (main.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getBoolean("SecondForm")) {
                    EnderAegisItem.shieldShoot(serverPlayer.level(), serverPlayer);
                } else {
                    EnderAegisSkill.onParry((ServerPlayerPatch) playerpatch);
                }
            }
        }
    }
}
