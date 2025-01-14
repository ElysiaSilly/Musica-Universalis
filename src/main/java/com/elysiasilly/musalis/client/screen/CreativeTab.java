package com.elysiasilly.musalis.client.screen;

import com.elysiasilly.babel.client.gui.BabelScreen;
import com.elysiasilly.babel.client.gui.BabelWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CreativeTab extends BabelScreen {

    public CreativeModeTab tab;

    public CreativeTab() {
        super(null);
    }

    @Override
    public @Nullable ResourceLocation mousePointerTexture() {
        return null;
    }

    @Override
    public void initBefore() {
        this.tab = BuiltInRegistries.CREATIVE_MODE_TAB.getOrThrow(CreativeModeTabs.BUILDING_BLOCKS);
        this.renderDebug = false;
    }

    @Override
    public void initAfter() {

    }

    @Override
    public List<BabelWidget> initWidgets() {

        List<BabelWidget> widgets = new ArrayList<>();

        int X = 0;
        int Y = 0;
        for(ItemStack stack : tab.getDisplayItems()) {
            if(X % 9 == 0) {
                Y++;
                X = 0;
            }

            ItemStackWidget widget = new ItemStackWidget(null, this);
            widget.stack = stack;
            widget.position = new Vec2(20 + (X * 20), 20 + (Y * 20));

            widget.boundStart = new Vec2(0 , 0 );
            widget.boundEnd = new Vec2(16, 16);

            widgets.add(widget);

            X++;
        }

        return widgets;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, float partialTick) {
        guiGraphics.fillGradient(0, 0, 10000, 10000, -1072689136, -804253680);

    }
}
