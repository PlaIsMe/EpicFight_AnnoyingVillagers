package com.pla.epicfight_annoyingvillagers.client.gui;

import com.mojang.blaze3d.platform.InputConstants;
import com.pla.epicfight_annoyingvillagers.EpicFightAnnoyingVillagers;
import com.pla.epicfight_annoyingvillagers.init.EpicFightAnnoyingVillagersModKeyMappings;
import com.pla.epicfight_annoyingvillagers.network.EmoteButtonMessage;
import com.pla.epicfight_annoyingvillagers.world.EmoteActions;
import com.pla.epicfight_annoyingvillagers.world.EmoteMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class EmoteScreen extends AbstractContainerScreen<EmoteMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(EpicFightAnnoyingVillagers.MODID, "textures/gui/emote_background.png");
    private static int lastPage = 0;

    private static final int BUTTON_SIZE = 60;

    private static final int[][] RADIAL_OFFSETS = {
            {   0, -86 },
            {  61, -61 },
            {  86,   0 },
            {  61,  61 },
            {   0,  86 },
            { -61,  61 },
            { -86,   0 },
            { -61, -61 }
    };

    private int page = 0;

    public EmoteScreen(EmoteMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 256;
        this.imageHeight = 256;
    }

    @Override
    protected void init() {
        this.page = Math.max(0, Math.min(lastPage, EmoteActions.pageCount() - 1));
        super.init();
        rebuildPage();
    }

    @Override
    public void onClose() {
        lastPage = this.page;
        super.onClose();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (EpicFightAnnoyingVillagersModKeyMappings.OPEN_EMOTE_MENU.isActiveAndMatches(InputConstants.getKey(keyCode, scanCode))) {
            if (this.minecraft != null && this.minecraft.player != null) {
                this.minecraft.player.closeContainer();
            } else {
                this.onClose();
            }
            return true;
        }

        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            if (this.minecraft != null && this.minecraft.player != null) {
                this.minecraft.player.closeContainer();
            } else {
                this.onClose();
            }
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void rebuildPage() {
        this.clearWidgets();

        int centerX = this.leftPos + this.imageWidth / 2;
        int centerY = this.topPos + this.imageHeight / 2;

        List<EmoteActions.EmoteAction> visible = EmoteActions.getPage(this.page);

        for (int i = 0; i < visible.size(); i++) {
            EmoteActions.EmoteAction action = visible.get(i);

            int x = centerX + RADIAL_OFFSETS[i][0] - BUTTON_SIZE / 2;
            int y = centerY + RADIAL_OFFSETS[i][1] - BUTTON_SIZE / 2;

            Button button = Button.builder(
                            Component.empty(),
                            b -> sendAction(action.key())
                    )
                    .bounds(x, y, BUTTON_SIZE, BUTTON_SIZE)
                    .tooltip(Tooltip.create(Component.translatable(action.translationKey())))
                    .build(builder -> new EmoteImageButton(builder, action.icon()));

            this.addRenderableWidget(button);
        }

        Button prev = Button.builder(Component.literal("<"), b -> changePage(-1))
                .bounds(centerX - 22, centerY - 10, 20, 20)
                .build();
        prev.active = this.page > 0;
        this.addRenderableWidget(prev);

        Button next = Button.builder(Component.literal(">"), b -> changePage(1))
                .bounds(centerX + 2, centerY - 10, 20, 20)
                .build();
        next.active = this.page < EmoteActions.pageCount() - 1;
        this.addRenderableWidget(next);
    }

    private void sendAction(ResourceLocation actionKey) {
        EpicFightAnnoyingVillagers.PACKET_HANDLER.sendToServer(new EmoteButtonMessage(actionKey));
    }

    private void changePage(int delta) {
        int newPage = Math.max(0, Math.min(this.page + delta, EmoteActions.pageCount() - 1));
        if (newPage != this.page) {
            this.page = newPage;
            lastPage = newPage;
            rebuildPage();
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scroll) {
        if (scroll > 0 && this.page > 0) {
            changePage(-1);
            return true;
        }
        if (scroll < 0 && this.page < EmoteActions.pageCount() - 1) {
            changePage(1);
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, scroll);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawCenteredString(this.font, this.title, this.imageWidth / 2, 8, 0x404040);
        graphics.drawCenteredString(
                this.font,
                Component.translatable("gui.epicfight_annoyingvillagers.emote.page", this.page + 1, EmoteActions.pageCount()),
                this.imageWidth / 2,
                this.imageHeight - 14,
                0xFFFFFF
        );
    }
}
