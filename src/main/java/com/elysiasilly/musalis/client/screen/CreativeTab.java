package com.elysiasilly.musalis.client.screen;

import com.elysiasilly.babel.client.screen.BabelScreen;
import com.elysiasilly.babel.client.screen.BabelWidget;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CreativeTab extends BabelScreen {

    public CreativeTab() {
        super(null);
    }

    @Override
    public List<BabelWidget> initWidgets() {
        List<BabelWidget> widgets = new ArrayList<>();

        ItemStackHolderWidget itemStackHolder = new ItemStackHolderWidget(this, null);

        widgets.add(itemStackHolder);

        return widgets;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, float partialTick) {
        guiGraphics.fillGradient(0, 0, 10000, 10000, -1072689136, -804253680);

        int leftPos = (this.width - 176) / 2;
        int topPos = (this.height - 166) / 2;



        //guiGraphics.fill(leftPos, topPos, 176, 166, DyeColor.PURPLE.getTextColor());

        //guiGraphics.blit(tab.getBackgroundTexture(), leftPos, topPos, 0, 0, 176, 166);

    }

    @Override
    public boolean renderDebug() {
        return false;
    }
}
