package com.pla.epicfight_annoyingvillagers.skill;
import com.pla.epicfight_annoyingvillagers.mixin.client.BattleModeGuiAccessor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;

public class StunEscapeSkill extends PassiveSkill {
    public static final String NBT_STUN_ESCAPE_CD = "StunEscapeCooldown";

    private int lastSynced = Integer.MIN_VALUE;

    public StunEscapeSkill(SkillBuilder<?> builder) {
        super(builder);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        super.updateContainer(container);

        if (container.getExecutor().isLogicalClient()) return;

        CompoundTag data = container.getExecutor().getOriginal().getPersistentData();
        int remain = data.contains(NBT_STUN_ESCAPE_CD) ? data.getInt(NBT_STUN_ESCAPE_CD) - 1 : 0;
        if (container.getExecutor().getOriginal().tickCount % 20 != 0) return;

        if (remain == lastSynced) return;
        lastSynced = remain;

        if (remain > 0) {
            setSkillMaxResourceSynchronize(container, (float) remain);
            setSkillConsumptionSynchronize(container, 0.0F);
        } else {
            setSkillMaxResourceSynchronize(container, 0.0F);
            setSkillConsumptionSynchronize(container, 0.0F);
        }
    }

    @Override
    public boolean shouldDraw(SkillContainer container) {
        return container.getMaxResource() > 0.0F;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics,
                          float x, float y, float partialTick) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F,
                ((BattleModeGuiAccessor) (Object) gui).epicFightAnnoyingVillagers$getSliding(partialTick), 0.0F);

        guiGraphics.blit(this.getSkillTexture(), (int)x, (int)y, 24, 24,
                0.0F, 0.0F, 1, 1, 1, 1);

        int remain = (int)container.getMaxResource();
        if (remain > 0) {
            String remainString = Integer.toString(remain);
            guiGraphics.drawString(gui.getFont(), remainString,
                    x + 12.0F - (4.0F * remainString.length()),
                    y + 6.0F,
                    0xFFFFFF, true);
        }

        guiGraphics.pose().popPose();
    }
}
