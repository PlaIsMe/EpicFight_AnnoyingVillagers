# AV_EFM integration handoff

The requested marker list, including the subsequently approved Transporter marker, is implemented in source. AV has now been edited with permission: the public API changes below are applied and five combat goals share replaceable animation hooks. The existing AV JAR has not been refreshed. Neither project was compiled or tested in-game, as requested.

## Applied AV public API changes

These previously requested members are now public; existing types, static/final modifiers, initializers, arguments, and bodies are preserved. Paths below are relative to `D:/projects/AnnoyingVillagers/src/main/java/com/pla/annoyingvillagers/`.

| AV file | Members to expose |
| --- | --- |
| `entity/BlueDemonEntity.java` | `dieTick` |
| `entity/EliteHerobrineKnockedEntity.java` | `eatCount` |
| `compat/aaa_particles/emitterinfo/DiamondAttractorParticleEmitterInfo.java` | `swordLocalOffset` |
| `entity/goal/AdvancedEscapeHoleGoal.java` | `mob`, `exitPosition`, `finished` |
| `entity/goal/BlueDemonEscapeHoleGoal.java` | `exitRoll` |
| `entity/goal/HerobrineEscapeHoleGoal.java` | `exitRoll` |
| `entity/goal/WaterEnderPearlEscapeGoal.java` | `mob` |
| `item/HookGunItem.java` | `HOOK_ANIMATION_NORMAL`, `HOOK_ANIMATION_TOP`, `getHookHandAnimationTag(boolean)` |
| `event/SpecialAttackOnKeyPressedEvent.java` | `playHookGunBindAnimationAfterHandRefresh(Player)`, `playTransporterFragmentAnimation(Player, TransporterFragmentItem.UseMode)` |

That is 14 members across nine files. `NullEntity.getAvailableNullWeapons()` is also now public for the EF release attack. AV additionally exposes `AnimatedMobGoal` backend hooks and the shared `NullSummonSkeletonGoal.summonSkeleton(NullEntity)` helper. Refresh `libs/AnnoyingVillagers-1.20.1-1.0.0.jar` from the updated AV source before building this project later. No compilation or JAR replacement was performed. Mixins use normal public APIs, with no accessors, shadows, reflection, or access transformers.

**No AV method signature changes are needed.** The earlier suggestion to add entity/damage-source arguments is superseded by call-site redirects that preserve AV's current signatures.

## Marker audit

The current AV tree contains **115 Java AV_EFM markers**, plus two knowledge-file mentions (117 total). The provided list covers 114 Java markers.

The previously unlisted marker, now implemented and registered, is:
- `entity/TransporterHerobrineCloneEntity.java:295`: `playPortalSupportAnimation(RigAnimationId, LivingEntity)`.

It is no longer pending. The list repeats `HerobrineGregEntity.playPortalSupportAnimation` twice. “spinfor5seconds mixin” was interpreted as `clazz/NullWeapon`, where both requested methods exist. The class-name typos were resolved to `HerobrineObsidianBlock`, `ArmoredHerobrineEntity`, and `SwordsmanHerobrineEntity`.

## Implementation details

- Burst protection uses a concrete AV helper, `BurstProtectionUtil.shouldIgnoreBurstProtection`, called by the interface's default method. `BurstProtectionUtilMixin` injects into that static helper; callback injection in the former interface mixin was unsupported. The two low-clone overrides still return true without calling the helper. AV alone retains the original rig stun/critical check. This fix requires refreshing the AV JAR with the helper and delegate change.

- 59 new mixin classes are registered in `src/main/resources/epicfight_annoyingvillagers.mixins.json` across this integration work. Two emitter mixins are client-only and gated on `aaa_particles`.
- `(*)` void hooks replace AV behavior and cancel. Non-void hooks return the Epic Fight result; sword-position hooks preserve their vanilla fallback if a joint is unavailable. `(&)` hooks augment without cancelling.
- Three hooks lack context in their original signature: the held-key animation methods lack an entity, Blue Demon's damage predicate lacks a damage source, and the pressed-key special dispatcher lacks its crosshair target. Their calls are redirected where that context is available, replacing the call without storing global state or changing AV.
- `HookGunItem.getHookHandAnimation` must continue to return `RigAnimationId` for AV callers. The commented Epic Fight mapping is a private, unique helper in the hook-gun mixin, used by the replacement play/stop methods. Hook-hand tags are maintained for AV cleanup.
- Escape goals likewise keep rig IDs only as forward/backward selection values. Actual playback, duration, active-animation checks, stuns, and combat locks use Epic Fight.
- The pinned Epic Fight artifact `8049910` defines `BOW` and `CROSSBOW`, not `RANGED`; the implementation uses those verified constants. Its roll constants are `BIPED_ROLL_FORWARD` and `BIPED_ROLL_BACKWARD`.
- The obsolete `AnimsWom.GLOWING_AGONY_GUARD` reference is mapped to the pinned WOM public accessor `AnimsAgony.AGONY_GUARD`.
- Snake combat locking starts in `DemoniacVoltageReaverItem.acquireSnakeProfileAttackLock`. Acquire/release hooks mirror this into `AdvancedMobPatch.lockCombatActions/unlockCombatActions` with `DemoniacVoltageReaverItem.class` as the owner. Snake cancellation calls AV's central release method so other cleanup paths share the same unlock.
- Escape locks use the goal instance as owner, preserving independent snake and escape locks.
- `AVAnimations.FLY_UP` calls AV's public `HerobrineEscapeHoleGoal.placeFlyUpPillarBlock` at 0.15, 0.30, 0.45, and 0.60 seconds, matching rig ticks 3, 6, 9, and 12. `ZIPLINE` repeats so it stays active during the lift.
- `EscapeAnimationCompat.stop` handles cancellation during an Epic Fight link by replacing it with `IDLE_BREAK`; merely terminating the link leaves its destination queued in the pinned server animator.
- `build.gradle` adds the same AAA Particles artifact as AV as a compile-only dependency for the emitter superclass.

## Implemented marked methods

All rows below describe source implementation, not runtime verification. The public visibility changes above are now applied in AV source.

| AV class | Marked methods covered |
| --- | --- |
| `AdvancedEscapeHoleGoal` | `prepareRandomExitRoll`, `isEscapeAttackLocked`, `hasBlockingEscapeAnimation`, `acquireEscapeAttackLock`, `releaseEscapeAttackLock`, `stopEscapeShieldGuard`, `stopActiveEscapeProfileAttack`, `isEscapeRigStunned`, `chooseEscapeRollAnimation`, `isBackwardEscapeRoll` |
| `AegisHerobrineEntity` | `addEpicFightAttributes` |
| `AlexEntity` | `addEpicFightAttributes` |
| `AngrySteveEntity` | `playGuardBreakAttackAnimation`, `playTriedAnimation`, `addEpicFightAttributes` |
| `ArmoredHerobrineEntity` | `addEpicFightAttributes` |
| `BlackFireEntity` | `getOwnerSwordPosition` |
| `BlackFireParticleEmitterInfo` | `getSwordPosition` |
| `BlackFireSwordItem` | `getSwordOrBodyPosition` |
| `BlockProjectileEntity` | `applyLongStun` |
| `BlueDemonEntity` | `playFinalDeathAnimation`, `playDeathAnimation`, `ignoreDamageForSomeEpicFightAnimation`, `conditionToAbsorbNearbyGroundedTridents`, `playStateTransformAnimation`, `playStateTransformEndAnimation`, `playTridentFestivalAnimation`, `addEpicFightAttributes` |
| `BlueDemonEscapeHoleGoal` | `getZiplineAnimationDurationTicks`, `playHeldZiplineAnimation`, `isZiplineAnimationActive`, `getExitRollAnimationDurationTicks`, `isExitRollAnimationActive`, `playExitRollAnimation`, `stopEscapeAnimation` |
| `BlueDemonThunderBeamEntity` | `resolveBeamHandPositions` |
| `BlueDemonTridentItem` | `inventoryTick` |
| `BurstProtectEntity` | `shouldIgnoreBurstProtection` |
| `ChrisEntity` | `addEpicFightAttributes` |
| `CommonUtil` | `stunImmunity` |
| `DemoniacVoltageReaverItem` | `isPlayingSnakeBladeAnimation`, `getToolTipPos`, `secondFormNbtTag` |
| `DiamondAttractorParticleEmitterInfo` | `getSwordPosition` |
| `DiamondAttractorSwordItem` | `knockDownFromPulling` |
| `DragonBeamEntity` | `dealEpicFightStaminaDamage` |
| `ElectricPhaseEntity` | `getOwnerSwordPosition` |
| `EliteHerobrineKnockedEntity` | `playBeingEatenAnimation` |
| `EnderAegisItem` | `secondFormNbtTag` |
| `EnderAegisProjectile` | `playStunAnimation` |
| `GlaiveHerobrineEntity` | `addEpicFightAttributes` |
| `Herobrine7Entity` | `addEpicFightAttributes` |
| `HerobrineCloneEntity` | `addEpicFightAttributes` |
| `HerobrineDragonEntity` | `isAllowedEpicFightHeldCategory` |
| `HerobrineEscapeHoleGoal` | `isFlyUpAnimationActive`, `playFlyUpAnimation`, `getFlyUpAnimationDurationTicks`, `isExitRollAnimationActive`, `getExitRollAnimationDurationTicks`, `playExitRollAnimation`, `stopEscapeAnimation`, `getFlyUpAnimationStartTick`, `getRemainingAuthoredFlyRise` |
| `HerobrineGregEntity` | `playPortalSupportAnimation` |
| `HerobrineMob` | `playFallAnimation`, `playInitAnimation`, `playStageChangeAnimation` |
| `HerobrineObsidianBlock` | `applyEpicFightShortStun`, `applyEpicFightRandomStun` |
| `HerobrineUtil` | `getJointOrVanillaActionPosition` |
| `HookGunCombatUtil` | `playHookGunAnimation` |
| `HookGunItem` | `getHookStartPosition`, `setHookHandAnimationState`, `stopHookHandAnimations`, `getHookHandAnimation` |
| `ItemProjectile` | `getTargetHandPosition`, `applyLongStun` |
| `LowHerobrineCloneEntity` | `playHerobrineHealingAnimations`, `playHerobrinePossessAnimation`, `getHealingArmPosition` |
| `LowShadowHerobrineCloneEntity` | `playHerobrinePossessionAnimation`, `playAssistanceOrSacrificingAnimation`, `playLowCloneEscapeAnimation`, `getSacrificingArmPosition`, `getHealingArmPosition` |
| `NullEntity` | `addEpicFightAttributes` |
| `NullWeapon` | `spinfor5seconds`, `isAllowedHeldCategory` |
| `ObsidianSledgehammerItem` | `inventoryTick` |
| `ReaperHerobrineEntity` | `addEpicFightAttributes` |
| `ShadowHerobrineCloneEntity` | `addEpicFightAttributes` |
| `ShadowHerobrineEntity` | `addEpicFightAttributes` |
| `ShockWaveBlockEntity` | `playTriedAnimation` |
| `SledgehammerHerobrineEntity` | `addEpicFightAttributes` |
| `SnakeBladeEntity` | `dealStaminaDamage`, `knockBack`, `cancelAnimation`, `dealStaminaDamageByPercentage` |
| `SpecialAttackOnKeyHeldEvent` | `efmConditionToExecute`, `playPortalSummonAnimation`, `playChestplateActivationAnimation` |
| `SpecialAttackOnKeyPressedEvent` | `playHookGunAnimation`, `playPortalSummonAnimation`, `playSinglePortalSummonAnimation`, `efmConditionToExecute`, `addEfmSpecialAttackCompat` |
| `SteveEntity` | `addEpicFightAttributes` |
| `SwordsmanHerobrineEntity` | `addEpicFightAttributes` |
| `ThrowableSpearItem` | `playEpicFightShotAnimation` |
| `ThrowingPearlKeyPressedEvent` | `efmConditionToExecute`, `playThrowingPearlAnimation` |
| `TotemUsingEvent` | `playGuardBreakAttackAnimation` |
| `TransporterHerobrineCloneEntity` | `playPortalSupportAnimation`, including the additional middle-hand support gesture used by its caller |
| `WaterEnderPearlEscapeGoal` | `playPearlAnimation`, `isEpicFightLongHitAnimation` |

## Shared combat goals and stun escape (follow-up)

No cloned goals or mod-loaded `canUse()` workarounds are needed. AV's `AnimatedMobGoal` owns the public backend seam; its default methods keep rig playback. `AnimatedMobGoalMixin` substitutes EF playback, active-animation/stun queries, event ownership, and per-goal combat locks. All five goals remain registered once in AV. Goal continuation follows the actual playing animation, including link transitions, rather than a fixed rig duration. Stopping releases only that goal's lock and does not cancel a newer stun/dodge animation.

| Shared AV goal/action | Epic Fight implementation |
| --- | --- |
| `EliteHerobrineSecondFormGoal` | Aegis shield shot; Glaive, Sledgehammer, and Reaver weapon innate variants; Reaper's three hand-command animations. Existing cooldowns, selection, mounted retries, and preferred portal target remain in AV. |
| `ObsidianMachineGunGoal` | `OBSIDIAN_MACHINE_GUN`; its existing server event starts AV's machine-gun ticks. |
| `ShadowHerobrineShootDarkObGoal` / `ShadowHerobrineSummonDarkObGoal` | Hand gestures cast at 0.25 seconds, gated to running combat goals so portal support does not cast spells. |
| `NullSummonSkeletonGoal` | `NULL_WEAPON_INNATE_SPECIAL`; its 1.5-second event exclusively calls AV's shared spawn helper. Slot, cooldown, current eligibility, target, and failed spawn are handled in one place. The goal suppresses its rig tick-30 spawn under EF. |
| Null weapon release | Rig source is `RigAnimationSpecs.NULL_EXTRA_ATTACK`, selected by `RigAnimatedMeleeAttackGoal`. EF uses `NULL_WEAPON_SPECIAL` with a server begin event calling `releaseRandomNullWeapon`. A custom shared-patch behavior selects it within 24 blocks, with an 80-tick behavior cooldown. |
| Stun escape | `EpicFightStunEscape` reuses AV's escape-window state, checks stun through `EpicfightUtil`, waits 5–9 ticks and retries every 5 ticks, then consumes the window and plays forward/backward roll when grounded. Execution victims are excluded. Rig stun application is disabled only for EF-patched mobs. |

Second-form action consumption is added at EF effect events, not again in goal stop. Aegis/Reaper use their AV methods that already consume an action. Existing EF damage/projectile/wave events are retained, not replayed from rig attack windows.

The previous active registry only installed ordinary AV NPC patches; excluded legacy mobpatch sources did not register the requested mobs. The shared patch registry now also includes the five elites, Shadow, Null, Transporter, and Blue Demon. Null Skeleton uses EF's skeleton patch. Their EF attributes are installed too. This provides the requested actions through the shared engine, not a wholesale restoration of every excluded legacy boss behavior. Reaper's separate dragon-summoning sequence remains rig-backed; its action window is respected by the shared engine.

Neither project was compiled, neither JAR was replaced, and no client/server runtime was started. The updated compatibility source requires the updated AV source/JAR, including the new base class.

### Validation and next steps

Source checks cover mixin registration, injection target signatures, method cancellation/return behavior, local animation and skill references, selected dependency APIs via bytecode inspection, and whitespace. These do not replace a successful compile or runtime Mixin application.

After refreshing the AV JAR from the updated source, verify:

1. Client and dedicated server startup: all mixins apply and the emitter mixins remain client-only.
2. Each requested weapon special, held-key action, pearl throw, hook-gun hand pose, and second-form state. Confirm vanilla special effects do not also execute.
3. Blue Demon transformation/death/damage immunity; clone possession, healing, sacrifice, and portal support; burst protection and projectile stun/stamina effects.
4. Snake start, natural end, interruption, weapon change, and entity removal release the combat lock. A separate escape lock must stay owned independently.
5. Blue Demon zipline and Herobrine pillar escape: correct roll direction, four pillar events, repeated cycles, interruption during transitions, and restored attacks after stopping.
6. Transporter portal summon, toward-hand gesture, and middle-hand support pose.
7. All five shared goals with AV alone and with EF: proper cooldowns, elite state-1 action consumption, state-2 repetition, Reaper mounted commands, and clean interruption/restart without stuck combat locks.
8. Null release at range; exactly one skeleton per successful cast, assigned slot/target and reset cooldown; interruption before 1.5 seconds must not summon.
9. Stun escape with and without a positive escape window, on ground/in air, during execution, and after entity removal. Rig-only mobs must retain their original stun handling.
