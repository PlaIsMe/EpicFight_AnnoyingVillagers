package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.annoyingvillagers.util.BlueDemonUtil;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkills;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
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

@Mixin(value = BlueDemonTridentItem.class, remap = false)
public abstract class BlueDemonTridentItemMixin {
    @Inject(method = "inventoryTick", remap = true, at = @At("TAIL"))
    private void inventoryTick(ItemStack itemstack, Level level, Entity entity, int i, boolean flag, CallbackInfo ci) {
        if (flag && entity instanceof Player player && entity.level() instanceof ServerLevel serverLevel) {
            PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
            if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.TRIDENT_FESTIVAL);
                if (skillContainer != null) {
                    if (skillContainer.getStack() >= 1) {
                        double d0 = entity.getX();
                        double d1 = entity.getY();
                        double d2 = entity.getZ();
                        if (Math.random() <= 0.1D) {
                            BlueDemonUtil.spawnBlueDemonEffect(serverLevel, entity);

                            if (serverLevel.random.nextDouble() <= 0.8D) {
                                float volume = (float) Mth.nextDouble(serverLevel.random, 0.05D, 0.5D);
                                float pitch = (float) Mth.nextDouble(serverLevel.random, 0.8D, 1.1D);

                                serverLevel.playSound(
                                        null,
                                        BlockPos.containing(d0, d1, d2),
                                        AnnoyingVillagersModSounds.ELECTRIFY.get(),
                                        SoundSource.NEUTRAL,
                                        volume,
                                        pitch
                                );
                            }
                        }
                    }
                }
            }
        }
    }
}
