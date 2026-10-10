package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.epicfight_annoyingvillagers.skill.EnderAegisSkill;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EnderAegisItem.class, remap = false)
public abstract class EnderAegisItemMixin {
    @Inject(method = "inventoryTick", remap = true, at = @At("TAIL"))
    private void inventoryTick(ItemStack itemstack, Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (level.isClientSide() || !(entity instanceof Player player)) return;

        boolean secondForm = selected && EnderAegisSkill.isPerformingInnate(player);

        EnderAegisItem.setSecondForm(itemstack, secondForm);
        if (itemstack.hasTag() && itemstack.getTag() != null) {
            itemstack.getTag().remove("EpicFightEnderAegisAwakenUntil");
        }
        if (secondForm && selected) {
            HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
        }
    }
}
