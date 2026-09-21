package com.pla.epicfight_annoyingvillagers.mobpatch;

import com.pla.epicfight_annoyingvillagers.advancedmobpatch.AdvancedAvNpcPatch;
import net.minecraft.world.entity.PathfinderMob;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;

import java.util.List;

public class StepDodgeAvNpcPatch<T extends PathfinderMob> extends AdvancedAvNpcPatch<T> {
    public StepDodgeAvNpcPatch(T original) {
        super(original);
    }

    @Override
    protected List<AnimationManager.AnimationAccessor<? extends StaticAnimation>> getDodgeAnimations() {
        return List.of(
                Animations.BIPED_STEP_BACKWARD,
                Animations.BIPED_STEP_FORWARD,
                Animations.BIPED_STEP_LEFT,
                Animations.BIPED_STEP_RIGHT
        );
    }
}
