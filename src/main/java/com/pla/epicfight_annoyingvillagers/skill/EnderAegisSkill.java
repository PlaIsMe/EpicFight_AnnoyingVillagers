package com.pla.epicfight_annoyingvillagers.skill;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkills;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEnderAegis;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModSounds;
import com.pla.annoyingvillagers.item.EnderAegisItem;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.epicfight_annoyingvillagers.util.ItemStackData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.entity.living.LivingShieldBlockEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.EntityState;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.event.EntityEventListener;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.api.event.types.entity.TakeDamageEvent;

import java.util.Objects;
import java.util.UUID;

@EventBusSubscriber(modid = AnnoyingVillagers.MODID)
public class EnderAegisSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("348aa19d-7c78-4959-9639-00c467ed258d");
    private static final float RESOURCE_PER_PARRY = 5.0F;

    public EnderAegisSkill(WeaponInnateSkill.Builder<?> builder) {
        super(builder);
    }

    public static void onParry(ServerPlayerPatch serverPlayerPatch) {
        SkillContainer container = serverPlayerPatch.getSkill(AVSkills.ENDER_AEGIS.get());
        if (container == null
                || !(container.getSkill() instanceof EnderAegisSkill skill)
                || container.getStack() >= skill.getMaxStack()) {
            return;
        }

        float gainedResource = Math.min(RESOURCE_PER_PARRY, container.getNeededResource());
        if (gainedResource > 0.0F) {
            skill.setConsumptionSynchronize(container, container.getResource() + gainedResource);
        }
    }

    @SubscribeEvent
    public static void onShieldBlock(LivingShieldBlockEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)
                || event.getBlockedDamage() <= 0.0F
                || !(serverPlayer.getUseItem().getItem() instanceof EnderAegisItem)) {
            return;
        }

        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(serverPlayer, PlayerPatch.class);
        if (playerPatch instanceof ServerPlayerPatch serverPlayerPatch) {
            onParry(serverPlayerPatch);
        }
    }

    @Override
    public void executeOnServer(SkillContainer container, CompoundTag args) {
        if (!this.isActivated(container)) {
            super.executeOnServer(container, args);
            container.getExecutor().playAnimationSynchronized(AnimsEnderAegis.ENDER_AEGIS_INNATE, 0.0F);
        }
    }

    @Override
    public void cancelOnServer(SkillContainer container, CompoundTag args) {
        container.deactivate();
        super.cancelOnServer(container, args);
    }

    @Override
    public void cancelOnClient(SkillContainer container, CompoundTag args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    @Override
    public void onInitiate(SkillContainer container, EntityEventListener eventListener) {
        super.onInitiate(container, eventListener);
        eventListener.registerEvent(
                EpicFightEventHooks.Entity.TAKE_DAMAGE_INCOME,
                EnderAegisSkill::handleIncomingAttack,
                this
        );
    }

    private static void handleIncomingAttack(TakeDamageEvent.Income event) {
        if (!(event.getEntityPatch() instanceof ServerPlayerPatch serverPlayerPatch)) {
            return;
        }
        ItemStack itemStack = serverPlayerPatch.getOriginal().getMainHandItem();
        if (!(itemStack.getItem() instanceof EnderAegisItem)) {
            return;
        }

        tryBlockDuringInnate(event, serverPlayerPatch);
    }

    private static void tryBlockDuringInnate(
            TakeDamageEvent.Income event,
            ServerPlayerPatch serverPlayerPatch
    ) {
        DamageSource damageSource = event.getDamageSource();
        if (damageSource.is(DamageTypes.MAGIC)
                || damageSource.is(DamageTypes.EXPLOSION)
                || damageSource.is(DamageTypes.ON_FIRE)
                || damageSource.is(DamageTypes.IN_FIRE)
                || damageSource.is(DamageTypes.FALL)) {
            return;
        }

        PlayerPatch<?> playerPatch = (PlayerPatch<?>) event.getEntityPatch();
        AnimationPlayer animationPlayer = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null));
        AssetAccessor<? extends StaticAnimation> dynamicAnimation = animationPlayer.getRealAnimation();
        EntityState entityState = dynamicAnimation.get().getState(playerPatch, animationPlayer.getElapsedTime());
        if (dynamicAnimation != AnimsEnderAegis.ENDER_AEGIS_INNATE || entityState.getLevel() >= 3) {
            return;
        }

        Entity attacker = damageSource.getEntity();
        ServerPlayer serverPlayer = serverPlayerPatch.getOriginal();
        if (attacker == null || !isInFront(serverPlayer, attacker)) {
            return;
        }

        event.cancel();
        event.setResult(AttackResult.ResultType.BLOCKED);
        attacker.setDeltaMovement(new Vec3(attacker.getLookAngle().x * -0.2D, 0.0D, attacker.getLookAngle().z * -0.2D));
        serverPlayer.setDeltaMovement(new Vec3(serverPlayer.getLookAngle().x * -0.2D, 0.0D, serverPlayer.getLookAngle().z * -0.2D));
        if (serverPlayer.level() instanceof ServerLevel serverLevel) {
            EpicfightUtil.damageBlocked(damageSource, serverPlayer, serverLevel);
        }
        onParry(serverPlayerPatch);
    }

    private static boolean isInFront(ServerPlayer player, Entity attacker) {
        Vec3 toAttacker = attacker.position().subtract(player.getEyePosition()).normalize();
        return toAttacker.dot(player.getViewVector(1.0F)) > 0.0D;
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        Player player = container.getExecutor().getOriginal();
        ItemStack itemStack = player.getMainHandItem();
        if (!(itemStack.getItem() instanceof EnderAegisItem)) {
            return;
        }

        if (container.getStack() >= 1) {
            if (!ItemStackData.getBoolean(itemStack, EnderAegisItem.AWAKEN_SOUND_PLAYED_TAG)) {
                player.playSound(AnnoyingVillagersModSounds.ELITE_HEROBRINE_WEAPON_SCREAMING.get(), 0.5F, 1.0F);
                ItemStackData.putBoolean(itemStack, EnderAegisItem.AWAKEN_SOUND_PLAYED_TAG, true);
            }
        } else if (ItemStackData.hasData(itemStack)) {
            ItemStackData.remove(itemStack, EnderAegisItem.AWAKEN_SOUND_PLAYED_TAG);
        }
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListenersBelongTo(this);
        super.onRemoved(container);
    }
}
