package com.pla.epicfight_annoyingvillagers.mobpatch;

import com.pla.annoyingvillagers.entity.AegisHerobrineEntity;
import net.minecraft.world.damagesource.DamageSource;

public class AegisHerobrinePatch extends FullDodgeAvNpcPatch<AegisHerobrineEntity> {
    public AegisHerobrinePatch(AegisHerobrineEntity original) {
        super(original);
    }

    @Override
    protected void onSuccessfulGuard(DamageSource damageSource) {
        super.onSuccessfulGuard(damageSource);
        if (!this.isLogicalClient()) {
            this.getOriginal().fireSecondFormShieldShot();
        }
    }
}
