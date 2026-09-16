package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.entity.ReaperHerobrineEntity;
import com.pla.annoyingvillagers.rig.RigAnimationController;
import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedMobPatch;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEnderSlayerScythe;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.epicfight_annoyingvillagers.util.EscapeAnimationCompat;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

@Mixin(value = ReaperHerobrineEntity.class, remap = false)
public abstract class ReaperHerobrineEntityMixin {
    @Unique private boolean av_efm$ownsDragonSummonAnimation;

    @Inject(method = "isDragonSummonAnimationBusy", at = @At("HEAD"), cancellable = true, require = 1)
    private void isDragonSummonAnimationBusy(CallbackInfoReturnable<Boolean> cir) {
        ReaperHerobrineEntity self = (ReaperHerobrineEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            cir.setReturnValue(this.av_efm$ownsDragonSummonAnimation
                    || RigAnimationController.hasActiveAnimation(self) || EpicfightUtil.isStunned(self)
                    || patch.getEntityState().inaction() || !patch.getEntityState().canBasicAttack()
                    || patch instanceof AdvancedMobPatch<?> advanced && advanced.isCombatActionLocked());
        }
    }

    @Inject(method = "playDragonSummonAnimation", at = @At("HEAD"), cancellable = true, require = 1)
    private void playDragonSummonAnimation(CallbackInfo ci) {
        ReaperHerobrineEntity self = (ReaperHerobrineEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch == null) return;
        if (!self.level().isClientSide()) {
            this.av_efm$ownsDragonSummonAnimation = true;
            if (patch instanceof AdvancedMobPatch<?> advanced) advanced.lockCombatActions(self);
            patch.playAnimationSynchronized(AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_INNATE, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "isDragonSummonAnimationPlaying", at = @At("HEAD"), cancellable = true, require = 1)
    private void isDragonSummonAnimationPlaying(CallbackInfoReturnable<Boolean> cir) {
        ReaperHerobrineEntity self = (ReaperHerobrineEntity) (Object) this;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch != null) {
            var animation = AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_INNATE;
            var player = patch.getAnimator().getPlayerFor(animation);
            cir.setReturnValue(this.av_efm$ownsDragonSummonAnimation
                    && player != null && !player.isEmpty() && !player.isEnd()
                    && animation.equals(player.getRealAnimation()));
        }
    }

    @Inject(method = "finishDragonSummonAnimation", at = @At("HEAD"), require = 1)
    private void finishDragonSummonAnimation(CallbackInfo ci) {
        if (!this.av_efm$ownsDragonSummonAnimation) return;
        ReaperHerobrineEntity self = (ReaperHerobrineEntity) (Object) this;
        EscapeAnimationCompat.stop(self, AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_INNATE);
        this.av_efm$ownsDragonSummonAnimation = false;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(self, LivingEntityPatch.class);
        if (patch instanceof AdvancedMobPatch<?> advanced) advanced.unlockCombatActions(self);
    }

    @Inject(method = "addEpicFightAttributes", at = @At("HEAD"))
    private static void addEpicFightAttributes(Builder builder, CallbackInfoReturnable<Builder> cir) {
        builder.add(EpicFightAttributes.IMPACT.get(), 4.0D)
                .add(EpicFightAttributes.ARMOR_NEGATION.get(), 10.0D)
                .add(EpicFightAttributes.STUN_ARMOR.get(), 20.0D)
                .add(EpicFightAttributes.MAX_STRIKES.get(), 100.0D)
                .add(EpicFightAttributes.MAX_STAMINA.get(), 60.0D)
                .add(EpicFightAttributes.STAMINA_REGEN.get(), 1.5D);
    }
}
