package com.elysiasilly.musalis.client.gui.composer;

import com.elysiasilly.babel.client.gui.BabelScreen;
import com.elysiasilly.babel.client.gui.BabelWidget;
import com.elysiasilly.musalis.core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.Vec2;

import java.util.List;

public class RCLeitmotifSearchBar extends BabelWidget<RCLeitmotifCatalogueWidget, BabelScreen> {

    String string = "";

    public RCLeitmotifSearchBar(RCLeitmotifCatalogueWidget parent, BabelScreen screen) {
        super(parent, screen);
    }

    @Override
    public void initAfter() {
        this.hoverable = true;
        this.clickable = true;
    }

    @Override
    public List<BabelWidget> initWidgets() {
        return List.of();
    }

    @Override
    public void onType(char codePoint, int modifiers) {
        this.string = this.string + codePoint;
        this.parent.leitmotifIndex.requestRecalculation = true;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
        guiGraphics.drawString(Minecraft.getInstance().font, this.string, (int) localBoundStart.x + 2, (int) localBoundStart.y + 5, RGBA.colours.WHITE.toDec());
    }
}
