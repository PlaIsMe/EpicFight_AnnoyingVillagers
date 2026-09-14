package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.clazz.HerobrineObsidianBlock;
import com.pla.annoyingvillagers.task.DelayedTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.damagesource.StunType;

@Mixin(value = HerobrineObsidianBlock.class, remap = false)
public abstract class HerobrineObsidianBlockMixin {
    @Inject(method = "applyEpicFightShortStun", at = @At("HEAD"), cancellable = true)
    private void applyEpicFightShortStun(Entity entity, CallbackInfo ci) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch != null && !livingEntityPatch.isStunned()) {
            livingEntityPatch.applyStun(StunType.SHORT, 1.0F);
        }
        ci.cancel();
    }

    @Inject(method = "applyEpicFightRandomStun", at = @At("HEAD"), cancellable = true)
    private void applyEpicFightRandomStun(Entity entity, CallbackInfo ci) {
        if (Math.random() <= 0.5D) {
            new DelayedTask(1) {
                @Override
                public void run() {
                    if (entity.level() instanceof ServerLevel && entity instanceof Mob mob) {
                        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                        if (livingEntityPatch != null && !livingEntityPatch.isStunned()) {
                            livingEntityPatch.applyStun(StunType.LONG, 10.0F);
                        }
                    }
                }
            };

            if (Math.random() <= 0.3D) {
                new DelayedTask(1) {
                    @Override
                    public void run() {
                        if (entity.level() instanceof ServerLevel && entity instanceof Mob mob) {
                            LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
                            if (livingEntityPatch != null && !livingEntityPatch.isStunned()) {
                                livingEntityPatch.applyStun(StunType.KNOCKDOWN, 10.0F);
                            }
                        }
                    }
                };
            }
        }
        ci.cancel();
    }
}
