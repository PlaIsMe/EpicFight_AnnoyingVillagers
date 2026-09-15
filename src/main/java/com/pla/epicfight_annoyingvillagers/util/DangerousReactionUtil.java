package com.pla.epicfight_annoyingvillagers.util;

import com.hm.efn.gameasset.EFNAnimations;
import com.hm.efn.gameasset.animations.*;
import com.pla.annoyingvillagers.clazz.HerobrineMob;
import com.pla.annoyingvillagers.clazz.DangerousReaction;
import com.pla.annoyingvillagers.entity.AngrySteveEntity;
import com.pla.annoyingvillagers.entity.BlueDemonEntity;
import com.pla.annoyingvillagers.entity.ReaperHerobrineEntity;
import com.pla.epicfight_annoyingvillagers.gameasset.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.fml.ModList;
import net.shelmarow.combat_evolution.gameassets.animation.ExecutionAttackAnimation;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.*;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.MobPatch;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class DangerousReactionUtil {
    private static final Set<String> DANGEROUS_ANIMATIONS = new HashSet<>();

    static {
        DANGEROUS_ANIMATIONS.addAll(Set.of(
                AnimsEnderGlaive.ENDER_GLAIVE_INNATE.get().getRegistryName().toString(),
                AnimsEnderGlaive.ENDER_GLAIVE_INNATE_SPECIAL.get().getRegistryName().toString(),
                AnimsEnderAegis.ENDER_AEGIS_INNATE.get().getRegistryName().toString(),
                AnimsEnderSlayerScythe.ENDER_SLAYER_SCYTHE_INNATE.get().getRegistryName().toString(),
                AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE.get().getRegistryName().toString(),
                AnimsDemoniacVoltageReaver.DEMONIAC_VOLTAGE_REAVER_INNATE_SPECIAL.get().getRegistryName().toString(),
                AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_INNATE.get().getRegistryName().toString(),
                AnimsObsidianSledgehammer.OBSIDIAN_SLEDGEHAMMER_INNATE_SPECIAL.get().getRegistryName().toString(),
                AnimsLegendarySword.LEGENDARY_SWORD_INNATE.get().getRegistryName().toString(),
                AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_FESTIVAL.get().getRegistryName().toString(),
                AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_ELECTRIC_FIELD.get().getRegistryName().toString(),
                AnimsBlueDemonTrident.BLUE_DEMON_TRIDENT_THUNDER_ATTACK.get().getRegistryName().toString(),
                AnimsBlueDemonTrident.BLUE_DEMON_STATE_TRANSFORM.get().getRegistryName().toString(),

                AnimsAgony.AGONY_SKY_DIVE_X.get().getRegistryName().toString(),
                AnimsAgony.AGONY_SKY_DIVE.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_CHARGED_ATTACK_2.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_CHARGED_ATTACK_3.get().getRegistryName().toString(),
                AnimsRuine.RUINE_PLUNDER.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_LAPSE.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_ASCENSION.get().getRegistryName().toString(),
                WOMAnimations.ANTITHEUS_ASCENDED_BLACKHOLE.get().getRegistryName().toString(),
                WOMAnimations.TORMENT_BERSERK_CONVERT.get().getRegistryName().toString(),
                AnimsSatsujin.SATSUJIN_GESSHOKU.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_AUTO_3.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_SPRENGKOPF.get().getRegistryName().toString(),
                AnimsHerrscher.GESETZ_WIDERSTAND.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_ECHO.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_ECLIPSE.get().getRegistryName().toString(),
                AnimsMoonless.MOONLESS_LUNAR_FULLMOON.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_OBSCURIDAD.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_CREMATORIO.get().getRegistryName().toString(),
                AnimsSolar.SOLAR_BRASERO_INFIERNO.get().getRegistryName().toString(),
                AnimsNapoleon.NAPOLEON_AUSTERLITZ_SHOOT.get().getRegistryName().toString(),
                AnimsNapoleon.NAPOLEON_WATERLOW_SHOOT.get().getRegistryName().toString(),
                AnimsOrbit.ORBIT_LIGHT_BEAM.get().getRegistryName().toString(),

                EFNGreatSwordAnimations.NG_GREATSWORD_CHARGING.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARG1MAX_FIRST.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARG1MAX_SECOND.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_CHARGING_MOB.get().getRegistryName().toString(),
                EFNGreatSwordAnimations.NG_GREATSWORD_AIRSLASH.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_DASH.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGING.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGING_MOB.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGE1.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGE2.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_CHARGE3.get().getRegistryName().toString(),
                EFNLanceAnimations.NF_MEEN_FINISHER.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_JUDEMENCUT_ALL.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_JUDEMENCUT.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_JUDEMENCUT_CHARGE.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_JUDEMENCUT_JUST.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_VOLCANOL_ALL.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_VOLCANOL.get().getRegistryName().toString(),
                EFNYamatoAnimations.YAMATO_VOLCANOL_CHARGE.get().getRegistryName().toString(),
                EFNAnimations.DMC5_V_JC.get().getRegistryName().toString(),
                EFNSkillAnimations.EXECUTION.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_ZANDATSU.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_ZANDATSU_AIR.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_DASH_Y.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_DASH_Y_SP.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_KICK_Y.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_CHARGE.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_CHARGE_THROUGH.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_AIR.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_Y_CHARGE_AIR.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XY.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XY_CHARGE.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXY.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXY_CHARGE.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXX.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXXY.get().getRegistryName().toString(),
                EFNMurasamaAnimations.HF_MURASAMA_XXXY_CHARGE.get().getRegistryName().toString(),
                EFNSekiroAnimations.DRAGON_FLASH.get().getRegistryName().toString(),
                EFNSekiroAnimations.MORTAL_BLADE_1.get().getRegistryName().toString(),
                EFNSekiroAnimations.MORTAL_BLADE_2.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_START.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_LOOP.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_START_N.get().getRegistryName().toString(),
                EFNThornWheelAnimations.THORNWHEEL_SKILL_LOOP_N.get().getRegistryName().toString(),
                EFNScytheAnimations.SCYTHE_HARVEST.get().getRegistryName().toString(),
                EFNScytheAnimations.SCYTHE_AIR_SLASH.get().getRegistryName().toString(),
                EFNScytheAnimations.SCYTHE_SCARLET_END.get().getRegistryName().toString()
        ));

        if (ModList.get().isLoaded("efn")) {
            try {
                DANGEROUS_ANIMATIONS.addAll(EpicFightNightFallUtil.getDangerousAnimations());
            } catch (Exception e) {
                e.fillInStackTrace();
            }
        }
    }

    public static boolean isAnimationDangerous(AssetAccessor<? extends StaticAnimation> targetDynamicAnimation) {
        if (targetDynamicAnimation != null && targetDynamicAnimation.get().getRegistryName() != null) {
            String animation = targetDynamicAnimation.get().getRegistryName().toString();
            return DANGEROUS_ANIMATIONS.contains(animation);
        }
        return false;
    }

    public static boolean isCurrentAnimationDangerous(LivingEntityPatch<?> patch) {
        if (patch == null) return false;
        var player = patch.getAnimator().getPlayerFor(null);
        return player != null && !player.isEmpty() && isAnimationDangerous(player.getRealAnimation());
    }

    public static boolean canReact(Mob mob) {
        if (DangerousReaction.canReact(mob)) return true;
        LivingEntity target = mob == null ? null : mob.getTarget();
        if (mob == null
                || mob.level().isClientSide()
                || !mob.isAlive()
                || mob.isRemoved()
                || mob.isDeadOrDying()
                || mob.isNoAi()
                || mob instanceof ReaperHerobrineEntity reaper && reaper.isSecondFormDragonRider()
                || EpicfightUtil.isStunned(mob)
                || !(target instanceof Mob targetMob)
                || !target.isAlive()
                || target.isRemoved()
                || mob.distanceToSqr(target) > 64.0D) {
            return false;
        }
        return isCurrentAnimationDangerous(EpicFightCapabilities.getEntityPatch(targetMob, LivingEntityPatch.class));
    }

    public static boolean checkEscape(Mob mob) {
        LivingEntity target = mob.getTarget();
        LivingEntityPatch<?> targetLivingEntityPatch = EpicFightCapabilities.getEntityPatch(target, LivingEntityPatch.class);
        if (target == null || targetLivingEntityPatch == null) return false;
        AssetAccessor<? extends StaticAnimation> targetDynamicAnimation = Objects.requireNonNull(targetLivingEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
        return isAnimationDangerous(targetDynamicAnimation) || targetDynamicAnimation.get() instanceof ExecutionAttackAnimation;
    }

    public static void stepLeftRightOnHurtByDangerousAnimation(DamageSource damageSource, MobPatch<?> mobPatch) {
        Entity target = damageSource.getEntity();
        if (!(target instanceof LivingEntity livingEntity)) return;
        LivingEntityPatch<?> targetEntityPatch = EpicFightCapabilities.getEntityPatch(livingEntity, LivingEntityPatch.class);
        if (targetEntityPatch != null) {
            AssetAccessor<? extends StaticAnimation> targetDynamicAnimation = Objects.requireNonNull(targetEntityPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            AssetAccessor<? extends StaticAnimation> dynamicAnimation = Objects.requireNonNull(mobPatch.getAnimator().getPlayerFor(null)).getRealAnimation();
            if (isAnimationDangerous(targetDynamicAnimation)
                    && !EpicfightUtil.isLongHitAnimation(dynamicAnimation, mobPatch)) {
                if (mobPatch.getOriginal() instanceof HerobrineMob herobrineMob
                        && herobrineMob.getStunEscapeCooldown() == 0) {
                    herobrineMob.setStunEscapeCooldown(60);
                    if (new Random().nextBoolean()) {
                        mobPatch.playAnimationSynchronized(WOMAnimations.ENDERSTEP_LEFT, 0.0F);
                    } else {
                        mobPatch.playAnimationSynchronized(WOMAnimations.ENDERSTEP_RIGHT, 0.0F);
                    }
                }

                if (mobPatch.getOriginal() instanceof AngrySteveEntity angrySteveEntity
                        && angrySteveEntity.getStunEscapeCooldown() == 0) {
                    angrySteveEntity.setStunEscapeCooldown(60);
                    if (new Random().nextBoolean()) {
                        mobPatch.playAnimationSynchronized(Animations.BIPED_STEP_LEFT, 0.0F);
                    } else {
                        mobPatch.playAnimationSynchronized(Animations.BIPED_STEP_RIGHT, 0.0F);
                    }
                }

                if (mobPatch.getOriginal() instanceof BlueDemonEntity blueDemonEntity
                        && blueDemonEntity.getStunEscapeCooldown() == 0) {
                    blueDemonEntity.setStunEscapeCooldown(60);
                    if (new Random().nextBoolean()) {
                        mobPatch.playAnimationSynchronized(Animations.BIPED_STEP_LEFT, 0.0F);
                    } else {
                        mobPatch.playAnimationSynchronized(Animations.BIPED_STEP_RIGHT, 0.0F);
                    }
                }
            }
        }
    }
}
