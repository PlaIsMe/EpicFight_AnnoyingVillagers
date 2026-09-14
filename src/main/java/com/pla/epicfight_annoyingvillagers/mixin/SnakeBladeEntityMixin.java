package com.pla.epicfight_annoyingvillagers.mixin;

import com.pla.annoyingvillagers.entity.SnakeBladeEntity;
import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsDemoniacVoltageReaver;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = SnakeBladeEntity.class, remap = false)
public abstract class SnakeBladeEntityMixin {
    @Inject(method = "dealStaminaDamage", at = @At("HEAD"), cancellable = true)
    private void dealStaminaDamage(LivingEntity target, DamageSource src, CallbackInfo ci) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (patch != null) EpicfightUtil.dealStaminaDamage(src, 1.0F, patch, false);
        ci.cancel();
    }

    @Inject(method = "knockBack", at = @At("TAIL"))
    private void knockBack(LivingEntity target, CallbackInfo ci) {
        SnakeBladeEntity self = (SnakeBladeEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (patch != null) patch.knockBackEntity(self.position(), 1.0F);
    }

    @Inject(method = "dealStaminaDamageByPercentage", at = @At("HEAD"), cancellable = true)
    private void dealStaminaDamageByPercentage(LivingEntity creator, Entity target, CallbackInfo ci) {
        SnakeBladeEntity self = (SnakeBladeEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (patch != null) {
            EpicfightUtil.dealStaminaDamageByPercentage(
                    self.level().damageSources().indirectMagic(self, creator), patch, 0.5D, true);
        }
        ci.cancel();
    }

    @Inject(method = "cancelAnimation", at = @At("HEAD"), cancellable = true)
    private void cancelAnimation(Entity creator, CallbackInfo ci) {
        LivingEntityPatch<?> patch = creator == null ? null : EpicFightCapabilities.getEntityPatch(creator, LivingEntityPatch.class);
        if (patch != null && patch.getAnimator().getPlayerFor(null) != null) {
            var animation = patch.getAnimator().getPlayerFor(null).getRealAnimation();
            if (animation == AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE || animation == AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE_SPECIAL) {
                patch.playAnimationSynchronized(AVAnimations.IDLE_BREAK, 0.0F);
            }
        }
        if (creator instanceof LivingEntity livingEntity) {
            DemoniacVoltageReaverItem.releaseSnakeProfileAttackLock(livingEntity);
        }
        ci.cancel();
    }
}
