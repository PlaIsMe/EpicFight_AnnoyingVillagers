package com.pla.epicfight_annoyingvillagers.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.resources.ResourceLocation;

public class EmoteImageButton extends Button {
    private final ResourceLocation icon;

    public EmoteImageButton(Builder builder, ResourceLocation icon) {
        super(builder);
        this.icon = icon;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        int x = this.getX();
        int y = this.getY();
        int w = this.getWidth();
        int h = this.getHeight();

        graphics.blit(this.icon, x, y, 0, 0, w, h, w, h);
        if (!this.active) {
            graphics.fill(x, y, x + w, y + h, 0x88000000);
        } else if (this.isHoveredOrFocused()) {
            graphics.fill(x, y, x + w, y + h, 0x35FFFFFF);
        }
    }
}
