package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.pla.annoyingvillagers.item.DemoniacVoltageReaverItem;
import com.pla.annoyingvillagers.util.HerobrineUtil;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkills;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsDemoniacVoltageReaver;
import com.pla.epicfight_annoyingvillagers.util.ItemStackData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.api.utils.math.Vec3f;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Mixin(value = DemoniacVoltageReaverItem.class, remap = false)
public abstract class DemoniacVoltageReaverItemMixin {
    @Inject(method = "inventoryTick", remap = true, at = @At("TAIL"))
    private void inventoryTick(ItemStack itemstack, Level level, Entity entity, int slot, boolean selected, CallbackInfo ci) {
        if (!selected || !(entity instanceof Player player)) return;

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
        if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
            SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.DEMONIAC_VOLTAGE_REAVER.get());
            if (skillContainer != null) {
                if (skillContainer.getStack() >= 1) {
                    HerobrineUtil.spawnEliteEffect(level, entity.getX(), entity.getY(), entity.getZ(), entity);
                    ItemStackData.putBoolean(itemstack, "SecondForm", true);
                } else if (ItemStackData.hasData(itemstack)) {
                    ItemStackData.remove(itemstack, "SecondForm");
                }
            }
        }
    }

    @Inject(method = "isPlayingSnakeBladeAnimation", at = @At("HEAD"), cancellable = true)
    private static void isPlayingSnakeBladeAnimation(LivingEntity livingEntity, CallbackInfoReturnable<Boolean> cir) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(livingEntity, LivingEntityPatch.class);
        if (patch == null || patch.getAnimator() == null) {
            cir.setReturnValue(false);
            return;
        }

        var animationPlayer = patch.getAnimator().getPlayerFor(null);
        if (animationPlayer == null) {
            cir.setReturnValue(false);
            return;
        }

        var dynamicAnimation = animationPlayer.getRealAnimation();
        cir.setReturnValue(dynamicAnimation == AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE
                || dynamicAnimation == AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE_SPECIAL);
        return;
    }

    @Inject(method = "getToolTipPos", at = @At("HEAD"), cancellable = true)
    private static void getToolTipPos(Entity ent, float partialTicks, float handToTip, CallbackInfoReturnable<Vec3> cir) {
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(ent, LivingEntityPatch.class);
        if (patch == null) {
            cir.setReturnValue(null);
            return;
        }

        OpenMatrix4f joint = patch.getArmature()
                .getBoundTransformFor(patch.getAnimator().getPose(partialTicks), Armatures.BIPED.get().toolR);

        OpenMatrix4f localOffset = new OpenMatrix4f().translate(new Vec3f(0.0F, 0.0F, -handToTip));
        OpenMatrix4f.mul(joint, localOffset, joint);

        float yawRad = (float) -Math.toRadians(((LivingEntity) ent).yBodyRotO + 180.0F);
        OpenMatrix4f worldYaw = new OpenMatrix4f().rotate(yawRad, new Vec3f(0.0F, 1.0F, 0.0F));
        OpenMatrix4f.mul(worldYaw, joint, joint);

        cir.setReturnValue(new Vec3(
                joint.m30 + ent.getX(),
                joint.m31 + (ent.getY() + (ent.getBbHeight() / 1.8F) - 1.0F),
                joint.m32 + ent.getZ()
        ));

        return;
    }
}
