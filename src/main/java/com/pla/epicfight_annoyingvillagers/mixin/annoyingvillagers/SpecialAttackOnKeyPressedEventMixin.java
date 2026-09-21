package com.pla.epicfight_annoyingvillagers.mixin.annoyingvillagers;

import com.hm.efn.gameasset.animations.EFNSwordAnimations;
import com.pla.annoyingvillagers.entity.BlackFireEntity;
import com.pla.annoyingvillagers.entity.ElectricPhaseEntity;
import com.pla.annoyingvillagers.entity.HerobrineDragonEntity;
import com.pla.annoyingvillagers.event.SpecialAttackOnKeyPressedEvent;
import com.pla.annoyingvillagers.init.AnnoyingVillagersModItems;
import com.pla.annoyingvillagers.item.HerobrineEnderEyeItem;
import com.pla.annoyingvillagers.item.HookGunItem;
import com.pla.annoyingvillagers.item.TransporterFragmentItem;
import com.pla.epicfight_annoyingvillagers.gameasset.AVAnimations;
import com.pla.epicfight_annoyingvillagers.gameasset.AVSkills;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVAxe;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVFist;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVGreatsword;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVSpear;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVSword;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsAVTachi;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsBlueDemonTrident;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsDemoniacVoltageReaver;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEnderAegis;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEnderGlaive;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEnderSlayerScythe;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsEpicFightAwaken;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsLegendarySword;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsNullWeapon;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsObsidianSledgehammer;
import com.pla.epicfight_annoyingvillagers.gameasset.AnimsObsidianWeapon;
import com.pla.epicfight_annoyingvillagers.skill.DemoniacVoltageReaverSkill;
import com.pla.epicfight_annoyingvillagers.skill.EnderGlaiveSkill;
import com.pla.epicfight_annoyingvillagers.skill.EnderSlayerScytheSkill;
import com.pla.epicfight_annoyingvillagers.skill.LegendarySwordSkill;
import com.pla.epicfight_annoyingvillagers.skill.NullWeaponSkill;
import com.pla.epicfight_annoyingvillagers.skill.ObsidianSledgeHammerSkill;
import com.pla.epicfight_annoyingvillagers.skill.ObsidianWeaponSkill;
import com.pla.epicfight_annoyingvillagers.skill.ShadowObsidianPillarSkill;
import com.pla.epicfight_annoyingvillagers.skill.TridentFestivalSkill;
import com.pla.epicfight_annoyingvillagers.skill.WoopieTheSwordSkill;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import com.pla.epicfight_annoyingvillagers.util.ItemStackData;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;

@Mixin(value = SpecialAttackOnKeyPressedEvent.class, remap = false)
public abstract class SpecialAttackOnKeyPressedEventMixin {
    @Inject(method = "efmConditionToExecute", at = @At("HEAD"), cancellable = true)
    private static void efmConditionToExecute(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        PlayerPatch<?> playerpatch = EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
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
        if (entity.level() instanceof ServerLevel) {
            if (dynamicAnimation != Animations.EMPTY_ANIMATION) {
                cir.setReturnValue(false);
                return;
            }
        }
        cir.setReturnValue(true);
        return;
    }

    @Inject(method = "playHookGunAnimation", at = @At("HEAD"), cancellable = true)
    private static void playHookGunAnimation(Player player, CallbackInfo ci) {
        LivingEntityPatch<?> freshPatch = EpicFightCapabilities.getEntityPatch(player, LivingEntityPatch.class);
        if (freshPatch != null) {
            freshPatch.playAnimationSynchronized(AVAnimations.HOOK_GUN, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "playPortalSummonAnimation", at = @At("HEAD"), cancellable = true)
    private static void playPortalSummonAnimation(Player player, CallbackInfo ci) {
        LivingEntityPatch<?> freshPatch = EpicFightCapabilities.getEntityPatch(player, LivingEntityPatch.class);
        if (freshPatch != null) {
            freshPatch.playAnimationSynchronized(AVAnimations.PORTAL_SUMMON, 0.0F);
        }
        ci.cancel();
    }

    @Inject(method = "playSinglePortalSummonAnimation", at = @At("HEAD"), cancellable = true)
    private static void playSinglePortalSummonAnimation(Player player, CallbackInfo ci) {
        LivingEntityPatch<?> freshPatch = EpicFightCapabilities.getEntityPatch(player, LivingEntityPatch.class);
        if (freshPatch != null) {
            freshPatch.playAnimationSynchronized(AVAnimations.POINT_LEFT_HAND_TOWARD, 0.0F);
        }
        ci.cancel();
    }

    // The AV hook has no crosshair argument; capture it at its execute call site.
    @Redirect(method = "execute", at = @At(value = "INVOKE", target = "Lcom/pla/annoyingvillagers/event/SpecialAttackOnKeyPressedEvent;addEfmSpecialAttackCompat(Lnet/minecraft/world/entity/Entity;)V"))
    private static void addEfmSpecialAttackCompat(Entity target, LevelAccessor world, Entity entity, Vec3 crosshairTarget) {
        epicfightAnnoyingVillagers$specialAttack(target, crosshairTarget);
    }

    @Unique
    private static void epicfightAnnoyingVillagers$specialAttack(Entity entity, Vec3 crosshairTarget) {
        if (entity != null) {
            PlayerPatch<?> playerpatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(entity, PlayerPatch.class);
            LivingEntityPatch<?> livingEntityPatch = (LivingEntityPatch)EpicFightCapabilities.getEntityPatch(entity, LivingEntityPatch.class);
            if (livingEntityPatch != null) {
                AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(livingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
                if (!EpicfightUtil.isLongHitAnimation(dynamicAnimation, livingEntityPatch)) {
                    if (entity instanceof Player) {
                        Player player = (Player)entity;
                        if (!player.level().isClientSide() && HookGunItem.tryBindFromSpecialAttack(player)) {
                            SpecialAttackOnKeyPressedEvent.playHookGunBindAnimationAfterHandRefresh(player);
                            return;
                        }
                    }

                    if (entity instanceof Player) {
                        Player player = (Player)entity;
                        if (!player.level().isClientSide()) {
                            TransporterFragmentItem.UseResult transporterUseResult = TransporterFragmentItem.tryUseSpecialAttack(player, crosshairTarget);
                            if (transporterUseResult.consumed()) {
                                if (transporterUseResult.activated()) {
                                    SpecialAttackOnKeyPressedEvent.playTransporterFragmentAnimation(player, transporterUseResult.mode());
                                }

                                return;
                            }
                        }
                    }

                    if (entity instanceof Player) {
                        Player player = (Player)entity;
                        ItemStack holdingItem = player.getMainHandItem();
                        ItemStack offHandItem = player.getOffhandItem();
                        if (holdingItem.getItem().equals(AnnoyingVillagersModItems.BLACK_FIRE_SWORD.get())) {
                            PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                            if (playerPatch instanceof ServerPlayerPatch) {
                                ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.BLACK_FIRE_SWORD.get());
                                if (skillContainer != null) {
                                    Level var13 = entity.level();
                                    if (var13 instanceof ServerLevel) {
                                        ServerLevel serverLevel = (ServerLevel)var13;
                                        if (skillContainer.getResource() >= 5.0F) {
                                            Skill.setSkillConsumptionSynchronize(skillContainer, skillContainer.getResource() - 5.0F);
                                            BlackFireEntity.spawnOnOwnerSword(serverLevel, player);
                                        }
                                    }
                                }
                            }
                        }

                        if (holdingItem.getItem().equals(AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.get())) {
                            PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                            if (playerPatch instanceof ServerPlayerPatch) {
                                ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.THUNDER_DIAMOND_BLADE.get());
                                if (skillContainer != null) {
                                    Level var81 = entity.level();
                                    if (var81 instanceof ServerLevel) {
                                        ServerLevel serverLevel = (ServerLevel)var81;
                                        if (skillContainer.getResource() >= 10.0F) {
                                            Skill.setSkillConsumptionSynchronize(skillContainer, skillContainer.getResource() - 10.0F);
                                            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, player);
                                            return;
                                        }
                                    }
                                }

                                skillContainer = serverPlayerPatch.getSkill(AVSkills.DUAL_THUNDER_DIAMOND_BLADE.get());
                                if (skillContainer != null) {
                                    Level var82 = entity.level();
                                    if (var82 instanceof ServerLevel) {
                                        ServerLevel serverLevel = (ServerLevel)var82;
                                        if (skillContainer.getResource() >= 10.0F) {
                                            Skill.setSkillConsumptionSynchronize(skillContainer, skillContainer.getResource() - 10.0F);
                                            ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, player);
                                            if (offHandItem.getItem().equals(AnnoyingVillagersModItems.THUNDER_DIAMOND_BLADE.get())) {
                                                ElectricPhaseEntity.spawnOnOwnerSword(serverLevel, player, true);
                                            }

                                            return;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (!(entity.level() instanceof ServerLevel) || dynamicAnimation == Animations.EMPTY_ANIMATION) {
                        if (entity instanceof Player) {
                            Player player = (Player)entity;
                            if (!player.level().isClientSide() && !player.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()) && !player.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                                player.getInventory().items.stream().filter((s) -> !s.isEmpty() && s.is((Item)AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())).findFirst().map((stack) -> {
                                    Item patt9050$temp = stack.getItem();
                                    if (patt9050$temp instanceof HerobrineEnderEyeItem herobrineEnderEyeItem) {
                                        ItemCooldowns cooldowns = player.getCooldowns();
                                        if (cooldowns.isOnCooldown(herobrineEnderEyeItem)) {
                                            return false;
                                        } else {
                                            HerobrineEnderEyeItem.spawnAndShootDarkObPillars((ServerLevel)player.level(), player, 10);
                                            player.getCooldowns().addCooldown(herobrineEnderEyeItem, 40);
                                            stack.hurtAndBreak(5, (ServerLevel) player.level(), player, item -> {
                                            });
                                            return true;
                                        }
                                    } else {
                                        return false;
                                    }
                                });
                            }
                        }

                        if (entity instanceof Player) {
                            Player player = (Player)entity;
                            ItemStack holdingItem = player.getMainHandItem();
                            ItemStack offHandItem = player.getOffhandItem();
                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get()) && entity.level() instanceof ServerLevel) {
                                if (offHandItem.getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get())) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_SPECIAL, 0.0F);
                                    PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                    if (playerPatch instanceof ServerPlayerPatch) {
                                        ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.TRIDENT_FESTIVAL.get());
                                        if (skillContainer != null) {
                                            Skill var93 = skillContainer.getSkill();
                                            if (var93 instanceof TridentFestivalSkill) {
                                                TridentFestivalSkill tridentFestivalSkill = (TridentFestivalSkill)var93;
                                                tridentFestivalSkill.toggleMode(skillContainer);
                                            }
                                        }
                                    }
                                } else {
                                    livingEntityPatch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THROW_3, 0.0F);
                                }

                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_AEGIS.get()) && entity.level() instanceof ServerLevel) {
                                livingEntityPatch.playAnimationSynchronized(AnimsEnderAegis.ENDER_AEGIS_SPECIAL, 0.0F);
                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.EARTH_AXE.get()) && entity.level() instanceof ServerLevel) {
                                livingEntityPatch.playAnimationSynchronized(AnimsAVAxe.EARTH_AXE_SPECIAL, 0.0F);
                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_GLAIVE.get()) && entity.level() instanceof ServerLevel) {
                                boolean success = false;
                                PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                if (playerPatch instanceof ServerPlayerPatch) {
                                    ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_GLAIVE.get());
                                    if (skillContainer != null) {
                                        Skill var101 = skillContainer.getSkill();
                                        if (var101 instanceof EnderGlaiveSkill) {
                                            EnderGlaiveSkill enderGlaiveSkill = (EnderGlaiveSkill)var101;
                                            if (skillContainer.getStack() >= 1) {
                                                livingEntityPatch.playAnimationSynchronized(AnimsEnderGlaive.ENDER_GLAIVE_INNATE_SPECIAL, 0.0F);
                                                enderGlaiveSkill.getResourceType().consumer.consume(skillContainer, serverPlayerPatch, enderGlaiveSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                                success = true;
                                            }
                                        }
                                    }
                                }

                                if (!success) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsEnderGlaive.ENDER_GLAIVE_SPECIAL, 0.0F);
                                }

                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.DEMONIAC_VOLTAGE_REAVER.get()) && entity.level() instanceof ServerLevel && !ItemStackData.getBoolean(holdingItem, "SnakeAnimation")) {
                                boolean success = false;
                                PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                if (playerPatch instanceof ServerPlayerPatch) {
                                    ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.DEMONIAC_VOLTAGE_REAVER.get());
                                    if (skillContainer != null) {
                                        Skill var100 = skillContainer.getSkill();
                                        if (var100 instanceof DemoniacVoltageReaverSkill) {
                                            DemoniacVoltageReaverSkill demoniacVoltageReaverSkill = (DemoniacVoltageReaverSkill)var100;
                                            if (skillContainer.getStack() >= 1) {
                                                livingEntityPatch.playAnimationSynchronized(AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE_SPECIAL, 0.0F);
                                                demoniacVoltageReaverSkill.getResourceType().consumer.consume(skillContainer, serverPlayerPatch, demoniacVoltageReaverSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                                success = true;
                                            }
                                        }
                                    }
                                }

                                if (!success) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_SPECIAL, 0.0F);
                                }

                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.OBSIDIAN_SLEDGEHAMMER.get()) && entity.level() instanceof ServerLevel) {
                                boolean success = false;
                                PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                if (playerPatch instanceof ServerPlayerPatch) {
                                    ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_SLEDGEHAMMER.get());
                                    if (skillContainer != null) {
                                        Skill var99 = skillContainer.getSkill();
                                        if (var99 instanceof ObsidianSledgeHammerSkill) {
                                            ObsidianSledgeHammerSkill obsidianSledgeHammerSkill = (ObsidianSledgeHammerSkill)var99;
                                            if (player.level() instanceof ServerLevel && skillContainer.getStack() >= 1) {
                                                livingEntityPatch.playAnimationSynchronized(AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_INNATE_SPECIAL, 0.0F);
                                                obsidianSledgeHammerSkill.getResourceType().consumer.consume(skillContainer, serverPlayerPatch, obsidianSledgeHammerSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                                success = true;
                                            }
                                        }
                                    }
                                }

                                if (!success) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_SPECIAL, 0.0F);
                                }

                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.ENDER_SLAYER_SCYTHE.get())) {
                                Level scytheLevel = entity.level();
                                if (scytheLevel instanceof ServerLevel) {
                                    ServerLevel serverLevel = (ServerLevel)scytheLevel;
                                    boolean usedInnateSpecial = false;
                                    PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                    if (playerPatch instanceof ServerPlayerPatch) {
                                        ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                        SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.ENDER_SLAYER_SCYTHE.get());
                                        if (skillContainer != null && skillContainer.getSkill() instanceof EnderSlayerScytheSkill && skillContainer.isActivated() && entity.getPersistentData().hasUUID("DragonUUID")) {
                                            Entity dragon = serverLevel.getEntity(player.getPersistentData().getUUID("DragonUUID"));
                                            if (dragon instanceof HerobrineDragonEntity) {
                                                HerobrineDragonEntity herobrineDragonEntity = (HerobrineDragonEntity)dragon;
                                                if (player.getVehicle() == herobrineDragonEntity) {
                                                    usedInnateSpecial = true;
                                                } else {
                                                    livingEntityPatch.playAnimationSynchronized(AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_SPECIAL_INNATE, 0.0F);
                                                    herobrineDragonEntity.recallAndLand(true);
                                                    usedInnateSpecial = true;
                                                }
                                            }
                                        }
                                    }

                                    if (!usedInnateSpecial) {
                                        livingEntityPatch.playAnimationSynchronized(AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_SPECIAL, 0.0F);
                                    }

                                    return;
                                }
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.NULL_WEAPON.get()) && entity.level() instanceof ServerLevel) {
                                PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                if (playerPatch instanceof ServerPlayerPatch) {
                                    ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.NULL_WEAPON.get());
                                    if (skillContainer != null && skillContainer.getSkill() instanceof NullWeaponSkill && !skillContainer.isActivated()) {
                                        livingEntityPatch.playAnimationSynchronized(AnimsNullWeapon.NULL_WEAPON_SPECIAL, 0.0F);
                                    } else {
                                        livingEntityPatch.playAnimationSynchronized(AnimsNullWeapon.NULL_WEAPON_INNATE_SPECIAL, 0.0F);
                                    }
                                }

                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.OBSIDIAN_WEAPON.get()) && entity.level() instanceof ServerLevel) {
                                boolean success = false;
                                PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                if (playerPatch instanceof ServerPlayerPatch) {
                                    ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_WEAPON.get());
                                    if (skillContainer != null && skillContainer.getStack() >= 1 && entity.level() instanceof ServerLevel) {
                                        Skill var97 = skillContainer.getSkill();
                                        if (var97 instanceof ObsidianWeaponSkill) {
                                            ObsidianWeaponSkill obsidianWeaponSkill = (ObsidianWeaponSkill)var97;
                                            success = true;
                                            obsidianWeaponSkill.getResourceType().consumer.consume(skillContainer, serverPlayerPatch, obsidianWeaponSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                        }
                                    }
                                }

                                if (success) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_INNATE_SPECIAL, 0.0F);
                                } else {
                                    livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_SPECIAL, 0.0F);
                                }

                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_WEAPON.get()) && entity.level() instanceof ServerLevel) {
                                boolean success = false;
                                PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                if (playerPatch instanceof ServerPlayerPatch) {
                                    ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.OBSIDIAN_WEAPON.get());
                                    if (skillContainer != null && skillContainer.getStack() >= 1 && entity.level() instanceof ServerLevel) {
                                        Skill var96 = skillContainer.getSkill();
                                        if (var96 instanceof ObsidianWeaponSkill) {
                                            ObsidianWeaponSkill obsidianWeaponSkill = (ObsidianWeaponSkill)var96;
                                            success = true;
                                            obsidianWeaponSkill.getResourceType().consumer.consume(skillContainer, serverPlayerPatch, obsidianWeaponSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                        }
                                    }
                                }

                                if (success) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_INNATE_SPECIAL, 0.0F);
                                } else {
                                    livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_SPECIAL, 0.0F);
                                }

                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_PILLAR.get()) && entity.level() instanceof ServerLevel) {
                                boolean success = false;
                                PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                if (playerPatch instanceof ServerPlayerPatch) {
                                    ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.SHADOW_OBSIDIAN_PILLAR.get());
                                    if (skillContainer != null && skillContainer.getStack() >= 1 && entity.level() instanceof ServerLevel) {
                                        Skill var95 = skillContainer.getSkill();
                                        if (var95 instanceof ShadowObsidianPillarSkill) {
                                            ShadowObsidianPillarSkill shadowObsidianPillarSkill = (ShadowObsidianPillarSkill)var95;
                                            success = true;
                                            shadowObsidianPillarSkill.getResourceType().consumer.consume(skillContainer, serverPlayerPatch, shadowObsidianPillarSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                        }
                                    }
                                }

                                if (success) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_INNATE_SPECIAL, 0.0F);
                                } else {
                                    livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.SHADOW_OBSIDIAN_PILLAR_SPECIAL, 0.0F);
                                }

                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get()) && entity.level() instanceof ServerLevel) {
                                if (offHandItem.getItem().equals(AnnoyingVillagersModItems.SHADOW_OBSIDIAN_SWORD.get())) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.SHADOW_OBSIDIAN_SWORD_DUAL_SPECIAL, 0.0F);
                                } else {
                                    livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_WEAPON_SPECIAL, 0.0F);
                                }

                                return;
                            }

                            if ((holdingItem.getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get()) || offHandItem.getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) && entity.level() instanceof ServerLevel) {
                                livingEntityPatch.playAnimationSynchronized(AnimsObsidianWeapon.OBSIDIAN_MACHINE_GUN, 0.0F);
                                if (player.getMainHandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                                    player.getMainHandItem().hurtAndBreak(10, player, EquipmentSlot.MAINHAND);
                                } else if (player.getOffhandItem().getItem().equals(AnnoyingVillagersModItems.HEROBRINE_ENDER_EYE.get())) {
                                    player.getOffhandItem().hurtAndBreak(10, player, EquipmentSlot.OFFHAND);
                                }

                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.LEGENDARY_SWORD.get()) && entity.level() instanceof ServerLevel) {
                                boolean success = false;
                                PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                if (playerPatch instanceof ServerPlayerPatch) {
                                    ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.LEGENDARY_SWORD.get());
                                    if (skillContainer != null) {
                                        Skill lookedPos = skillContainer.getSkill();
                                        if (lookedPos instanceof LegendarySwordSkill) {
                                            LegendarySwordSkill legendarySwordSkill = (LegendarySwordSkill)lookedPos;
                                            if (player.level() instanceof ServerLevel) {
                                                if (LegendarySwordSkill.isAwakened(skillContainer) && offHandItem.getItem().equals(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get())) {
                                                    livingEntityPatch.playAnimationSynchronized(AnimsAVSword.WOOPIE_INNATE_SPECIAL_LEGENDARY, 0.0F);
                                                    return;
                                                }

                                                if (skillContainer.getStack() >= 1) {
                                                    if (offHandItem.getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get())) {
                                                        livingEntityPatch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_ELECTRIC_FIELD, 0.0F);
                                                        legendarySwordSkill.getResourceType().consumer.consume(skillContainer, serverPlayerPatch, legendarySwordSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                                    } else {
                                                        livingEntityPatch.playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_INNATE_SPECIAL, 0.0F);
                                                    }

                                                    success = true;
                                                }
                                            }
                                        }
                                    }
                                }

                                if (!success) {
                                    if (offHandItem.getItem().equals(AnnoyingVillagersModItems.BLUE_DEMON_TRIDENT.get())) {
                                        livingEntityPatch.playAnimationSynchronized(AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_SPECIAL_LEGENDARY, 0.0F);
                                    } else {
                                        livingEntityPatch.playAnimationSynchronized(AnimsLegendarySword.LEGENDARY_SWORD_SPECIAL, 0.0F);
                                    }
                                }

                                return;
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.WOOPIE_THE_SWORD.get()) && entity.level() instanceof ServerLevel) {
                                PlayerPatch<?> playerPatch = (PlayerPatch)EpicFightCapabilities.getEntityPatch(player, PlayerPatch.class);
                                if (playerPatch instanceof ServerPlayerPatch) {
                                    ServerPlayerPatch serverPlayerPatch = (ServerPlayerPatch)playerPatch;
                                    SkillContainer skillContainer = serverPlayerPatch.getSkill(AVSkills.WOOPIE_THE_SWORD.get());
                                    if (skillContainer != null && skillContainer.getStack() == 1 && entity.level() instanceof ServerLevel) {
                                        Skill woopieSkill = skillContainer.getSkill();
                                        if (woopieSkill instanceof WoopieTheSwordSkill) {
                                            WoopieTheSwordSkill woopieTheSwordSkill = (WoopieTheSwordSkill)woopieSkill;
                                            livingEntityPatch.playAnimationSynchronized(AnimsAVSword.WOOPIE_INNATE_SPECIAL, 0.0F);
                                            woopieTheSwordSkill.getResourceType().consumer.consume(skillContainer, serverPlayerPatch, woopieTheSwordSkill.getDefaultConsumptionAmount(serverPlayerPatch));
                                            return;
                                        }
                                    }
                                }
                            }

                            if (holdingItem.getItem().equals(AnnoyingVillagersModItems.BLUE_FLAME_SWORD.get()) && entity.level() instanceof ServerLevel) {
                                Level playerPatch = player.level();
                                if (playerPatch instanceof ServerLevel) {
                                    ServerLevel level = (ServerLevel)playerPatch;
                                    double reach = player.blockInteractionRange();
                                    HitResult hitResult = player.pick(reach, 0.0F, false);
                                    if (hitResult.getType() == Type.BLOCK) {
                                        BlockHitResult blockHit = (BlockHitResult)hitResult;
                                        BlockPos lookedPos = blockHit.getBlockPos();
                                        BlockState lookedState = level.getBlockState(lookedPos);
                                        if (lookedState.is(Blocks.SOUL_SAND) || lookedState.is(Blocks.SOUL_SOIL)) {
                                            BlockPos firePos = lookedPos.above();
                                            if (level.isEmptyBlock(firePos)) {
                                                BlockState soulFireState = Blocks.SOUL_FIRE.defaultBlockState();
                                                if (soulFireState.canSurvive(level, firePos)) {
                                                    level.setBlock(firePos, soulFireState, 3);
                                                    livingEntityPatch.playAnimationSynchronized(AnimsAVSword.BLUE_FLAME_SWORD_SPECIAL, 0.0F);
                                                    holdingItem.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                                                    return;
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            if (playerpatch == null) {
                                return;
                            }

                            ResourceLocation key = BuiltInRegistries.ITEM.getKey(holdingItem.getItem());
                            if (ModList.get().isLoaded("efn") && key.getNamespace().equals("efn")) {
                                return;
                            }

                            CapabilityItem mainHandCapability = playerpatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
                            if (mainHandCapability.getWeaponCategory() == WeaponCategories.SWORD || mainHandCapability.getWeaponCategory() == WeaponCategories.AXE) {
                                if (mainHandCapability.getStyle(playerpatch) == Styles.ONE_HAND && entity.level() instanceof ServerLevel) {
                                    livingEntityPatch.playAnimationSynchronized(EFNSwordAnimations.NF_SWORD_SKILL, 0.0F);
                                    return;
                                }

                                if (mainHandCapability.getStyle(playerpatch) == Styles.TWO_HAND && entity.level() instanceof ServerLevel) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsEpicFightAwaken.STRAIGHTSWORD_DUAL_DODGE_SLASH, 0.0F);
                                    return;
                                }
                            }

                            if (mainHandCapability.getWeaponCategory() == WeaponCategories.TACHI && entity.level() instanceof ServerLevel) {
                                livingEntityPatch.playAnimationSynchronized(AnimsAVTachi.AV_TACHI_SPECIAL, 0.0F);
                                return;
                            }

                            if (mainHandCapability.getWeaponCategory() == WeaponCategories.DAGGER && entity.level() instanceof ServerLevel) {
                                livingEntityPatch.playAnimationSynchronized(EFNSwordAnimations.NF_SWORD_SKILL_SECOND, 0.0F);
                                return;
                            }

                            if (mainHandCapability.getWeaponCategory() == WeaponCategories.LONGSWORD) {
                                if (mainHandCapability.getStyle(playerpatch) == Styles.ONE_HAND && entity.level() instanceof ServerLevel) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsEpicFightAwaken.STRAIGHTSWORD_DODGE_SLASH1, 0.0F);
                                    return;
                                }

                                if (mainHandCapability.getStyle(playerpatch) == Styles.TWO_HAND && entity.level() instanceof ServerLevel) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsEpicFightAwaken.STRAIGHTSWORD_DUAL_DODGE_PURSUIT, 0.0F);
                                    return;
                                }
                            }

                            if (mainHandCapability.getWeaponCategory() == WeaponCategories.GREATSWORD && entity.level() instanceof ServerLevel) {
                                livingEntityPatch.playAnimationSynchronized(AnimsAVGreatsword.AV_GREATSWORD_SPECIAL, 0.0F);
                                return;
                            }

                            if ((mainHandCapability.getWeaponCategory() == WeaponCategories.FIST || mainHandCapability.getWeaponCategory() == WeaponCategories.NOT_WEAPON || mainHandCapability.getWeaponCategory() == WeaponCategories.RANGED) && entity.level() instanceof ServerLevel) {
                                if (entity.isSprinting()) {
                                    if (entity.level() instanceof ServerLevel) {
                                        livingEntityPatch.playAnimationSynchronized(AnimsAVFist.WHIRLWIND_KICK, 0.0F);
                                    }
                                } else if (!entity.getPersistentData().contains("FistCombo")) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsAVFist.FIST_LEFT, 0.0F);
                                    entity.getPersistentData().putDouble("FistCombo", (double)1.0F);
                                } else if (entity.getPersistentData().getDouble("FistCombo") == (double)1.0F) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsAVFist.FIST_UP, 0.0F);
                                    entity.getPersistentData().putDouble("FistCombo", (double)2.0F);
                                } else if (entity.getPersistentData().getDouble("FistCombo") == (double)2.0F) {
                                    livingEntityPatch.playAnimationSynchronized(AnimsAVFist.FIST_DASH, 0.0F);
                                    entity.getPersistentData().remove("FistCombo");
                                }

                                return;
                            }

                            if ((mainHandCapability.getWeaponCategory() == WeaponCategories.SPEAR || mainHandCapability.getWeaponCategory() == WeaponCategories.TRIDENT) && entity.level() instanceof ServerLevel) {
                                livingEntityPatch.playAnimationSynchronized(AnimsAVSpear.AV_SPEAR_SPECIAL, 0.0F);
                            }
                        }

                    }
                }
            }
        }
    }
}
