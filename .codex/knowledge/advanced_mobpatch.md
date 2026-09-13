# SmartNpc advanced combat port (2026-09-10)

Runtime identity fix (2026-09-12): companion gradle.properties must use
`mod_id=epicfight_annoyingvillagers`, matching the Java @Mod and dependency tables.
Using the core's `annoyingvillagers` id caused two discovered mods to compete for the
same identity. The 13:03 debug/latest log first reports missing metadata for
epicfight_annoyingvillagers, then Mixin ClassMetadataNotFoundException for the core's
FishingRodGrappleUtil. The helper exists in the installed libs jar; fix mod identity
and regenerate processed resources/IDE runs instead of suppressing fishing mixins.
Current local dependency is libs/AnnoyingVillagers-1.20.1-1.0.0.jar. The user maintains
this jar manually; do not replace it as part of build validation.

The companion's existing `advancedmobpatch` package now contains the updated SmartNpc
engine: `AdvancedMobPatch`, `AdvancedCombatBehaviors`, `AdvancedAnimationAttackGoal`,
`AdvancedChasingGoal`, and `AdvancedStaminaStatus`. There is one active engine package.
Only package/accessor naming differs from the source engine; normal animation completion,
unified behavior roots, guard damage handling, equipment/durability change detection,
stamina, and owner-keyed combat locks are retained. Do not reintroduce recovery-window
early chaining or separate competing weapon/custom attack owners.

`compat.epicfight.AdvancedAvNpcPatch` is registered for the eleven current core AVNpc
types (Steve, Angry Steve, Alex, Chris, Jev, two scouts, four knights). Registration is
owned by `EpicFightAnnoyingVillagersModPatchEntities`; attributes come from
`AvNpcPatchRegistration`. Native rig melee/shield and random combat-jump goals are
removed for these patched entities. Core recovery and hand-use goals remain. Attack and
chase use priority 2 to fit AV's priorities; running higher-priority actions, real rig
playback and counted rig locks block attacks. Idle goals cannot veto combat admission.

`AvNpcRecoveryIntegration` handles core Forge events. CHECK_START waits for explicit EF
action locks and unowned inaction (stun/scripted actions); owned ordinary attacks and
local guard yield to higher-priority recovery. `AdvancedAnimationAttackGoal.ownsCurrentAnimation`
checks clip ownership without adopting external actions. START is a committed core
notification: stop remaining ordinary attack goals and cancel local guard before the
first pillar jump, even without a DIG/USE clip. The core counted recovery lock and
patch utility gate then suppress both attack and chase through the episode.
DIG/STOP_DIG/USE use the custom mob utility clips. Native profile attacks
are cancelled before native collider scheduling. Scripted non-profile rig actions keep
their timed gameplay hooks and make EF yield. Healing uses synchronized core state and
separate mob-safe EAT_MAINHAND/EAT_OFFHAND clips, never a Player mirror animation.
Custom utility clips are appended to the existing `AVAnimations` builder, not registered
through a second builder for the same mod namespace.

`CombatEvolution`/`CombatEvolutionBehaviorProvider` port the execution root and task,
including its owner lock/release. `ExecutionHandlerMixin` recognizes the updated engine.
`EFKick`/`EFKickBehaviorProvider` retain the SmartNpc optional kick roots.
Public lock API: `AdvancedMobPatch.lockCombatActions(Object owner)`,
`unlockCombatActions(Object owner)`, `isCombatActionLocked()`; integration also exposes
`AvNpcRecoveryIntegration.getPatch(AVNpc)` and `isCombatActionLocked(AVNpc)`.

Config: `config/epicfight_annoyingvillagers-movesets.toml`, section `advancedMobPatch`,
list `weaponCapabilityRedirects`. Format `source_item_id;target_weapon_preset_id`;
example `wom:agony;epicfight:spear`. This changes only the NPC's capability/moveset,
not its actual held item. SmartNpc parsing, defaults, preset-factory-aware cache and
invalid-entry fallback are retained. Existing companion guard config is separate.

The repository already excluded legacy mobpatch implementations (except Alex) and
combatbehaviour sources. Their old registry references and orphaned MobPatchCommon
helper did not build against the current core; the live registry now uses the new AvNpc
patch. Non-AV entities retain core native rig combat. Legacy sources remain available.
Scoped pre-port backups are under `.codex/backups/smartnpc-port-20260910`.

Build dependency is `libs/AnnoyingVillagers-1.20.1-1.0.0.jar`, copied from the
updated core delivery jar. Rebuild core before refreshing this local dependency.
Companion output has the distinct filename `EpicFight-AnnoyingVillagers-1.20.1-1.4.7.jar`.
Mixin config/refmap names now use `epicfight_annoyingvillagers` to avoid the core's
resource-name collision; stale references to nonexistent mixin classes were removed.
The Clash Blade plugin gate uses the correct companion package prefix. Mod dependency
table ownership now matches the companion id and explicitly requires the core.

Validate with Java assemble (including clean companion build); no automated tests.
Gameplay validation still requires Minecraft: attack/guard/execution/kicks, equipment
damage without combo reset, recovery with tool swaps and hand animation, and new-spawn
tool loadouts at all difficulties.
