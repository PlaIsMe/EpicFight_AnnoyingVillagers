package com.pla.epicfight_annoyingvillagers.skill;

import com.pla.annoyingvillagers.AnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.gameasset.*;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.epicfight_annoyingvillagers.util.ItemStackData;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import yesman.epicfight.api.client.animation.property.TrailInfo;
import yesman.epicfight.api.event.EntityEventListener;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.event.IdentifierProvider;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityDecorations;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.registry.entries.EpicFightMobEffects;
import yesman.epicfight.registry.entries.EpicFightAttributes;
import yesman.epicfight.api.event.types.entity.DealDamageEvent;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;

import javax.annotation.Nullable;
import java.util.UUID;

public class LegendarySwordSkill extends WeaponInnateSkill {
    public static final String AWAKENED_TAG = "LegendarySwordAwakened";
    public static final String AWAKEN_UNTIL_TAG = "LegendarySwordAwakenUntil";
    public static final int AWAKEN_DURATION_TICKS = 20 * 15;

    private static final int AWAKEN_EFFECT_AMPLIFIER = 2;
    private static final double AWAKEN_ATTACK_SPEED_MULTIPLIER = 0.5D;
    private static final float TRIED_CHANCE = 0.3F;
    private static final ResourceLocation AWAKEN_ATTACK_SPEED_MODIFIER = ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "legendary_sword_awaken_attack_speed");
    private static final IdentifierProvider AWAKEN_TRAIL_MODIFIER = IdentifierProvider.constant(
            ResourceLocation.fromNamespaceAndPath(AnnoyingVillagers.MODID, "legendary_sword_awaken_trail"));

    public LegendarySwordSkill(WeaponInnateSkill.Builder<?> builder) {
        super(builder);
    }

    public static boolean isAwakened(ItemStack stack, @Nullable Entity entity) {
        return isAwakened(stack, entity == null ? null : entity.level());
    }

    public static boolean isAwakened(ItemStack stack, @Nullable Level level) {
        if (!stack.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get()) || !ItemStackData.hasData(stack)) {
            return false;
        }

        CompoundTag tag = ItemStackData.copyTag(stack);
        if (!tag.getBoolean(AWAKENED_TAG)) {
            return false;
        }

        if (!tag.contains(AWAKEN_UNTIL_TAG, Tag.TAG_LONG)) {
            return false;
        }

        return level != null && level.getGameTime() < tag.getLong(AWAKEN_UNTIL_TAG);
    }

    public static boolean isAwakened(SkillContainer container) {
        if (container == null || container.getExecutor() == null) {
            return false;
        }

        Player player = container.getExecutor().getOriginal();
        return isAwakened(player.getMainHandItem(), player.level())
                && Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED));
    }

    public static void clearAwakeningTags(Player player) {
        Inventory inventory = player.getInventory();
        inventory.items.forEach(LegendarySwordSkill::clearAwakeningTag);
        inventory.offhand.forEach(LegendarySwordSkill::clearAwakeningTag);
        inventory.armor.forEach(LegendarySwordSkill::clearAwakeningTag);
    }

    public static void clearExpiredAwakeningTags(Player player) {
        Inventory inventory = player.getInventory();
        inventory.items.forEach(stack -> clearExpiredAwakeningTag(stack, player.level()));
        inventory.offhand.forEach(stack -> clearExpiredAwakeningTag(stack, player.level()));
        inventory.armor.forEach(stack -> clearExpiredAwakeningTag(stack, player.level()));
    }

    private static boolean hasAwakeningTag(ItemStack stack) {
        return stack.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())
                && ItemStackData.getBoolean(stack, AWAKENED_TAG);
    }

    private static void clearExpiredAwakeningTag(ItemStack stack, Level level) {
        if (hasAwakeningTag(stack) && !isAwakened(stack, level)) {
            clearAwakeningTag(stack);
        }
    }

    private static void clearAwakeningTag(ItemStack stack) {
        if (!stack.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get()) || !ItemStackData.hasData(stack)) {
            return;
        }

        CompoundTag tag = ItemStackData.copyTag(stack);

        tag.remove(AWAKENED_TAG);
        tag.remove(AWAKEN_UNTIL_TAG);

        ItemStackData.setTag(stack, tag);
    }

    public void startAwakening(SkillContainer container) {
        Player player = container.getExecutor().getOriginal();
        if (player.level().isClientSide()) {
            return;
        }

        ItemStack stack = player.getMainHandItem();
        if (!stack.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())) {
            return;
        }

        CompoundTag tag = ItemStackData.copyTag(stack);
        tag.putBoolean(AWAKENED_TAG, true);
        tag.putLong(AWAKEN_UNTIL_TAG, player.level().getGameTime() + AWAKEN_DURATION_TICKS);
        ItemStackData.setTag(stack, tag);

        container.getDataManager().setDataSync(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED, true);
        this.setMaxDurationSynchronize(container, AWAKEN_DURATION_TICKS);
        this.setDurationSynchronize(container, AWAKEN_DURATION_TICKS);
        applyAwakeningEffects(player);
    }

    private void clearAwakeningState(SkillContainer container) {
        if (container.getExecutor().isLogicalClient()) {
            return;
        }

        Player player = container.getExecutor().getOriginal();
        boolean hadAwakeningState = Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED))
                || hasAwakeningTag(player.getMainHandItem());

        removeAwakeningAttackSpeed(player);

        if (!hadAwakeningState) {
            return;
        }

        clearAwakeningTags(player);
        container.getDataManager().setDataSync(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED, false);
        this.setDurationSynchronize(container, 0);
        this.setMaxDurationSynchronize(container, this.maxDuration);
    }

    private static void applyAwakeningEffects(Player player) {
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.JUMP, 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        player.addEffect(new MobEffectInstance(EpicFightMobEffects.STUN_IMMUNITY, 25, AWAKEN_EFFECT_AMPLIFIER, false, false, true));
        applyAwakeningAttackSpeed(player);
    }

    private static void applyAwakeningAttackSpeed(Player player) {
        applyAwakeningAttackSpeedModifier(player, Attributes.ATTACK_SPEED);
        applyAwakeningAttackSpeedModifier(player, EpicFightAttributes.OFFHAND_ATTACK_SPEED);
    }

    private static void applyAwakeningAttackSpeedModifier(Player player, Holder<Attribute> attribute) {
        AttributeInstance attackSpeed = player.getAttribute(attribute);
        if (attackSpeed == null) {
            return;
        }

        attackSpeed.removeModifier(AWAKEN_ATTACK_SPEED_MODIFIER);
        attackSpeed.addTransientModifier(new AttributeModifier(
                AWAKEN_ATTACK_SPEED_MODIFIER,
                AWAKEN_ATTACK_SPEED_MULTIPLIER,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ));
    }

    private static void removeAwakeningAttackSpeed(Player player) {
        removeAwakeningAttackSpeedModifier(player, Attributes.ATTACK_SPEED);
        removeAwakeningAttackSpeedModifier(player, EpicFightAttributes.OFFHAND_ATTACK_SPEED);
    }

    private static void removeAwakeningAttackSpeedModifier(Player player, Holder<Attribute> attribute) {
        AttributeInstance attackSpeed = player.getAttribute(attribute);
        if (attackSpeed != null) {
            attackSpeed.removeModifier(AWAKEN_ATTACK_SPEED_MODIFIER);
        }
    }

    private static boolean shouldUseAwakenedTrail(SkillContainer container) {
        Player player = container.getExecutor().getOriginal();
        ItemStack mainHand = player.getMainHandItem();
        return mainHand.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())
                && (Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED))
                || isAwakened(mainHand, player.level()));
    }

    private boolean canExecuteAwakenedInnate(SkillContainer container) {
        Player player = container.getExecutor().getOriginal();
        ItemStack mainHand = player.getMainHandItem();
        return this.checkExecuteCondition(container)
                && isAwakened(container)
                && mainHand.is(AnnoyingVillagersModItems.LEGENDARY_SWORD.get())
                && EpicFightCapabilities.getItemStackCapability(mainHand).getInnateSkill(container.getExecutor(), mainHand) == this
                && player.getVehicle() == null;
    }

    @Override
    public boolean canExecute(SkillContainer container) {
        return super.canExecute(container) || canExecuteAwakenedInnate(container);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, CompoundTag friendlyByteBuf) {
        if (this.isActivated(skillContainer) && !isAwakened(skillContainer)) {
            return;
        }

        super.executeOnServer(skillContainer, friendlyByteBuf);
        skillContainer.activate();

        if (skillContainer.getExecutor().getOriginal().getOffhandItem().is(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get())) {
            skillContainer.getExecutor().playAnimationSynchronized(AnimsAVSword.WOOPIE_FLY, 0.0F);
        } else {
            skillContainer.getExecutor().playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_INNATE, 0.0F);
        }
    }

    @Override
    public void cancelOnServer(SkillContainer skillContainer, CompoundTag friendlyByteBuf) {
        clearAwakeningState(skillContainer);
        skillContainer.deactivate();
        super.cancelOnServer(skillContainer, friendlyByteBuf);
    }

    public void executeOnClient(SkillContainer container, CompoundTag args) {
        super.executeOnClient(container, args);
        container.activate();
    }

    public void cancelOnClient(SkillContainer container, CompoundTag args) {
        super.cancelOnClient(container, args);
        container.deactivate();
    }

    @Override
    public void onInitiate(SkillContainer container, EntityEventListener eventListener) {
        super.onInitiate(container, eventListener);
        eventListener.registerEvent(
                EpicFightEventHooks.Entity.DELIVER_DAMAGE_PRE,
                (DealDamageEvent.Pre event) -> {
                    if (event.getEntityPatch().isLogicalClient() || !isAwakened(container) || event.getModifiedDamage() <= 0.0F) {
                        return;
                    }

                    Player player = (Player) event.getEntityPatch().getOriginal();
                    if (player.getRandom().nextFloat() >= TRIED_CHANCE) {
                        return;
                    }

                    LivingEntity target = event.getTarget();
                    if (!target.isAlive()) {
                        return;
                    }

                    LivingEntityPatch<?> targetPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
                    if (targetPatch != null) {
                        targetPatch.playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_KNOCKDOWN, 0.0F);
                    }
                }, this,
                10
        );
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onInitiateClient(SkillContainer container) {
        container.getExecutor().getEntityDecorations().addTrailInfoModifier(AWAKEN_TRAIL_MODIFIER, (val, object) -> {
            if (val.hand() != InteractionHand.MAIN_HAND || !shouldUseAwakenedTrail(container)) {
                return val;
            }

            return val.unpackAsBuilder()
                    .r(253.0F / 255.0F)
                    .g(255.0F / 255.0F)
                    .b(118.0F / 255.0F)
                    .create();
        });
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void onRemoveClient(SkillContainer container) {
        container.getExecutor().getEntityDecorations().removeTrailInfoModifier(AWAKEN_TRAIL_MODIFIER);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListenersBelongTo(this);
        clearAwakeningState(container);
        super.onRemoved(container);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        if (container.getExecutor().isLogicalClient()) {
            if (Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED))) {
                int remainingDuration = container.getRemainDuration() - 1;
                container.setDuration(remainingDuration);

                if (remainingDuration <= 0) {
                    container.deactivate();
                }
            }

            return;
        }

        Player player = container.getExecutor().getOriginal();
        if (player.tickCount % 20 == 0) {
            clearExpiredAwakeningTags(player);
        }

        if (!Boolean.TRUE.equals(container.getDataManager().getDataValue(AVSkillDataKeys.LEGENDARY_SWORD_AWAKENED))) {
            removeAwakeningAttackSpeed(player);
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        if (!isAwakened(mainHand, player.level())) {
            this.cancelOnServer(container, null);
            return;
        }

        int remainingDuration = container.getRemainDuration() - 1;
        container.setDuration(remainingDuration);

        if (remainingDuration <= 0) {
            this.cancelOnServer(container, null);
            return;
        }

        if (player.tickCount % 10 == 0) {
            applyAwakeningEffects(player);
        }
    }
}
