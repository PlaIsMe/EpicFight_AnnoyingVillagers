package com.pla.epicfight_annoyingvillagers.event;

import com.pla.annoyingvillagers.rig.RigStunEscapeEntity;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.util.EpicfightUtil;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.Map;
import java.util.WeakHashMap;

/** EF counterpart to the rig controller's delayed knockdown escape checks. */
@Mod.EventBusSubscriber(modid = EpicFightAnnoyingVillagers.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class EpicFightStunEscape {
    private static final Map<Mob, Integer> NEXT_CHECK = new WeakHashMap<>();

    private EpicFightStunEscape() {}

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Mob mob) || mob.level().isClientSide()
                || !(mob instanceof RigStunEscapeEntity escape)) return;
        LivingEntityPatch<?> patch = EpicFightCapabilities.getEntityPatch(mob, LivingEntityPatch.class);
        if (patch == null || !mob.isAlive() || mob.isRemoved() || !EpicfightUtil.isStunned(mob)) {
            NEXT_CHECK.remove(mob);
            return;
        }
        var player = patch.getAnimator().getPlayerFor(null);
        // Execution victims must not roll out. Match AV's knockdown/long-stun escape.
        if (player == null || player.isEmpty()
                || !EpicfightUtil.isLongHitAnimationNotExecutedAnimation(player.getRealAnimation(), patch)) {
            NEXT_CHECK.remove(mob);
            return;
        }
        int nextCheck = NEXT_CHECK.computeIfAbsent(mob, ignored -> mob.tickCount + 5 + mob.getRandom().nextInt(5));
        if (mob.tickCount < nextCheck) return;
        NEXT_CHECK.put(mob, mob.tickCount + 5);
        // This AV field is an active escape window, not a cooldown to wait out.
        if (escape.getStunEscapeCooldown() <= 0 || !mob.onGround() || mob.isPassenger() || mob.isNoAi()) return;
        escape.setStunEscapeCooldown(0);
        NEXT_CHECK.remove(mob);
        mob.getNavigation().stop();
        patch.playAnimationSynchronized(mob.getRandom().nextBoolean()
                ? Animations.BIPED_ROLL_FORWARD : Animations.BIPED_ROLL_BACKWARD, 0.0F);
    }
}
