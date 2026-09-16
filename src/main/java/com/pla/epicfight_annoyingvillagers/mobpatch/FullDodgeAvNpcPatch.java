package com.pla.epicfight_annoyingvillagers.mobpatch;

import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAvNpcPatch;
import net.minecraft.world.entity.PathfinderMob;
import reascer.wom.gameasset.WOMAnimations;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public class FullDodgeAvNpcPatch<T extends PathfinderMob> extends AdvancedAvNpcPatch<T> {
    @Override
    protected List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> getDodgeAnimations() {
        return List.of(
                WOMAnimations.ENDERSTEP_FORWARD,
                WOMAnimations.ENDERSTEP_BACKWARD,
                WOMAnimations.ENDERSTEP_LEFT,
                WOMAnimations.ENDERSTEP_RIGHT,
                Animations.BIPED_STEP_BACKWARD,
                Animations.BIPED_STEP_FORWARD,
                Animations.BIPED_STEP_LEFT,
                Animations.BIPED_STEP_RIGHT,
                Animations.BIPED_ROLL_BACKWARD,
                Animations.BIPED_ROLL_FORWARD
        );
    }
}
