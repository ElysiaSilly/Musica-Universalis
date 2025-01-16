package com.elysiasilly.musalis.client.screen;

import com.elysiasilly.babel.client.gui.BabelScreen;
import com.elysiasilly.babel.client.gui.BabelWidget;
import com.elysiasilly.babel.client.gui.WidgetBounds;
import com.elysiasilly.musalis.util.RenderUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;

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
