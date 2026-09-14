package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkills;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Mixin(value = EnderAegisItem.class, remap = false)
public abstract class EnderAegisItemMixin {
    @Inject(method = "inventoryTick", remap = true, at = @At("TAIL"))
    private void inventoryTick(ItemStack itemstack, Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (level.isClientSide() || !(entity instanceof Player player)) return;

        boolean secondForm = false;
        if (selected) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_AEGIS);
                secondForm = skillContainer != null && skillContainer.getStack() >= 1;
            }
        }

        EnderAegisItem.setSecondForm(itemstack, secondForm);
        if (secondForm) {
            HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
        }
    }
}
