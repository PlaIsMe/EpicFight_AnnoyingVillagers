package com.pla.epicfight_annoyingvillagers.gameasset;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;
import yesman.epicfight.api.collider.OBBCollider;

public class AVCollider {
    public static final Collider SHADOW_OBSIDIAN_PILLAR = new MultiOBBCollider(3, 0.2, 3.0, 0.2, 0.0F, 0.0F, 0.0F);
    public static final Collider KICK = new MultiOBBCollider(4, 0.4, 0.4, 0.4, (double)0.0F, 0.6, (double)0.0F);
}
