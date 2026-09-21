package com.pla.epicfight_annoyingvillagers.skill;

import com.pla.epicfight_annoyingvillagers.gameasset.*;
import com.pla.annoyingvillagers.item.BlueDemonTridentItem;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.event.EntityEventListener;
import yesman.epicfight.api.event.EpicFightEventHooks;
import yesman.epicfight.api.utils.AttackResult;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class TridentFestivalSkill extends WeaponInnateSkill {
    private static final UUID EVENT_UUID = UUID.fromString("10cefa54-8fee-4627-a321-64a1a6388a25");

    public boolean isRangedMode(SkillContainer container) {
        return container.getDataManager().getDataValue(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE);
    }

    public boolean isMeleeMode(SkillContainer container) {
        return !this.isRangedMode(container);
    }

    public void toggleMode(SkillContainer container) {
        SkillDataManager data = container.getDataManager();
        boolean current = data.getDataValue(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE);
        data.setDataSync(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE, !current);
    }

    public TridentFestivalSkill(WeaponInnateSkill.Builder<?> builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(SkillContainer skillContainer, CompoundTag friendlyByteBuf) {
        if (!this.isActivated(skillContainer)) {
            super.executeOnServer(skillContainer, friendlyByteBuf);
            skillContainer.activate();
            if (this.isRangedMode(skillContainer)) {
                Player player = skillContainer.getExecutor().getOriginal();
                ItemStack mainHand = player.getMainHandItem();
                ItemStack offHand = player.getOffhandItem();
                boolean bothFullyCharged =
                        BlueDemonTridentItem.isBlueDemonTrident(mainHand)
                                && BlueDemonTridentItem.isBlueDemonTrident(offHand)
                                && BlueDemonTridentItem.isFullyCharged(mainHand)
                                && BlueDemonTridentItem.isFullyCharged(offHand);
                if (bothFullyCharged) {
                    skillContainer.getExecutor().playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_FESTIVAL, 0.0F);
                } else {
                    skillContainer.getExecutor().playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THUNDER_ATTACK, 0.0F);
                }
            } else {
                skillContainer.getExecutor().playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_ELECTRIC_FIELD, 0.0F);
            }
        }
    }

    @Override
    public void cancelOnServer(SkillContainer skillContainer, CompoundTag friendlyByteBuf) {
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
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);
        Player player = container.getExecutor().getOriginal();
        if (player.level() instanceof ServerLevel serverLevel && player.tickCount % 20 == 0) {
            SkillDataManager data = container.getDataManager();
            data.setDataSync(AVSkillDataKeys.TRIDENT_AMOUNT, BlueDemonTridentItem.getAllOwnerTridents(serverLevel, player).size());
        }
    }

    @Override
    public void onInitiate(SkillContainer container, EntityEventListener eventListener) {
        super.onInitiate(container, eventListener);
        container.getDataManager().setDataSync(AVSkillDataKeys.IS_TRIDENT_RANGED_MODE, false);
        eventListener.registerEvent(
                EpicFightEventHooks.Player.COMBO_ATTACK, event -> {
                    if (event.getPlayerPatch().isLogicalClient()) {
                        return;
                    }

                    SkillContainer skillContainer = event.getPlayerPatch().getSkill(this);
                    if (skillContainer == null) {
                        return;
                    }

                    if (this.isRangedMode(skillContainer)) {
                        event.cancel();

                        final PlayerPatch<?> playerPatch = event.getPlayerPatch();
                        AssetAccessor<? extends StaticAnimation> dynamicAnimation =
                                Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();

                        if (dynamicAnimation != null && dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_1) {
                            skillContainer.getExecutor().playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_2, 0.0F);
                        } else if (dynamicAnimation != null && dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_2) {
                            skillContainer.getExecutor().playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_3, 0.0F);
                        } else if (dynamicAnimation != null && dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_3) {
                            skillContainer.getExecutor().playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_4, 0.0F);
                        } else if (dynamicAnimation != null && dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_4) {
                            skillContainer.getExecutor().playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_5, 0.0F);
                        } else {
                            if (playerPatch.getOriginal().isSprinting()) {
                                skillContainer.getExecutor().playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_DASH, 0.0F);
                            } else if (!playerPatch.getOriginal().onGround() && !playerPatch.getOriginal().isInWater()) {
                                skillContainer.getExecutor().playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_AIRSLASH, 0.0F);
                            } else {
                                skillContainer.getExecutor().playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_1, 0.0F);
                            }
                        }
                    }
                }, this);
        eventListener.registerEvent(EpicFightEventHooks.Entity.TAKE_DAMAGE_INCOME, (pre) -> {
            if (pre.getEntityPatch().isLogicalClient()) return;

            final PlayerPatch<?> playerPatch = (PlayerPatch<?>) pre.getEntityPatch();
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(playerPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (dynamicAnimation == null) return;

            if (dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THUNDER_ATTACK || dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_ELECTRIC_FIELD || dynamicAnimation == AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_FESTIVAL) {
                pre.cancel();
                pre.setResult(AttackResult.ResultType.BLOCKED);
            }

            if (playerPatch.getOriginal().isSprinting() && pre.getDamageSource().getDirectEntity() instanceof Projectile projectile) {
                Vec3 entityPosition = projectile.position();
                Vec3 entityViewVector = playerPatch.getOriginal().getViewVector(1.0F);
                Vec3 entitySubtract = entityPosition.subtract(playerPatch.getOriginal().getEyePosition()).normalize();

                if (entitySubtract.dot(entityViewVector) > 0.0D) {
                    pre.cancel();
                    pre.setResult(AttackResult.ResultType.BLOCKED);
                    if (playerPatch.getOriginal().level() instanceof ServerLevel serverLevel) {
                        EpicfightUtil.damageBlocked(pre.getDamageSource(), playerPatch.getOriginal(), serverLevel);
                    }
                    if (new Random().nextBoolean()) {
                        playerPatch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_GUARD_HIT1, 0.0F);
                    } else {
                        playerPatch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_GUARD_HIT2, 0.0F);
                    }
                }
            }
        }, this);
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListenersBelongTo(this);
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return super.getSkillTexture();
    }
}
