package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.event.SpecialAttackOnKeyHeldEvent;
import com.pla.annoyingvillagers.item.TransporterFragmentItem;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import java.util.Objects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = SpecialAttackOnKeyHeldEvent.class, remap = false)
public abstract class SpecialAttackOnKeyHeldEventMixin {
    @Inject(method = "efmConditionToExecute", at = @At("HEAD"), cancellable = true)
    private static void efmConditionToExecute(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        LivingEntityPatch<?> livingEntityPatch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (livingEntityPatch == null) {
            cir.setReturnValue(false);
            return;
        }
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        if (EpicfightUtil.isLongHitAnimation(dynamicAnimation, livingEntityPatch)) {
            cir.setReturnValue(false);
            return;
        }
        if (entity instanceof Player player
                && (player.getMainHandItem().getItem() instanceof TransporterFragmentItem
                || player.getOffhandItem().getItem() instanceof TransporterFragmentItem)) {
            cir.setReturnValue(true);
            return;
        }
        if (entity.level() instanceof ServerLevel) {
            if (dynamicAnimation != Animations.EMPTY_ANIMATION) {
                cir.setReturnValue(false);
                return;
            }
        }
        cir.setReturnValue(true);
        return;
    }

    // Capture the entity from execute because the AV animation hook has no arguments.
    @Redirect(method = "execute(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;)V",
            at = @At(value = "INVOKE", target = "Lcom/pla/annoyingvillagers/event/SpecialAttackOnKeyHeldEvent;playPortalSummonAnimation()V"))
    private static void playPortalSummonAnimation(LevelAccessor world, Entity entity, Vec3 crosshairTarget) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch != null && !entity.level().isClientSide()) {
            boolean offHandOnly = entity instanceof Player player
                    && player.getOffhandItem().getItem() instanceof TransporterFragmentItem
                    && !(player.getMainHandItem().getItem() instanceof TransporterFragmentItem);
            patch.playAnimationSynchronized(offHandOnly
                    ? AVAnimations.POINT_LEFT_HAND_TOWARD
                    : AVAnimations.PORTAL_SUMMON, 0.0F);
        }
    }

    // Capture the entity from execute because the AV animation hook has no arguments.
    @Redirect(method = "execute(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;)V",
            at = @At(value = "INVOKE", target = "Lcom/pla/annoyingvillagers/event/SpecialAttackOnKeyHeldEvent;playChestplateActivationAnimation()V"))
    private static void playChestplateActivationAnimation(LevelAccessor world, Entity entity, Vec3 crosshairTarget) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
        if (patch != null && !entity.level().isClientSide()) {
            patch.playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_MIDDLE, 0.0F);
        }
    }
}
