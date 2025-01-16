package com.elysiasilly.musalis.client.screen.composer;

import com.elysiasilly.babel.client.gui.BabelScreen;
import com.elysiasilly.babel.client.gui.BabelWidget;

public class RCLeitmotifSearchBar extends BabelWidget<RCLeitmotifCatalogueWidget, BabelScreen> {

    String string = "";

    public RCLeitmotifSearchBar(RCLeitmotifCatalogueWidget parent, BabelScreen screen) {
        super(parent, screen, null);
    }

    //@Override
    //public void initAfter() {
    //    this.hoverable = true;
    //    this.clickable = true;
    //}

    //@Override
    //public void onType(char codePoint, int modifiers) {
    //    this.string = this.string + codePoint;
    //    this.parent.leitmotifIndex.requestRecalculation = true;
    //}

    //@Override
    //public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
    //    guiGraphics.drawString(Minecraft.getInstance().font, this.string, (int) localBoundStart.x + 2, (int) localBoundStart.y + 5, RGBA.colours.WHITE.dec());
    //}
}
