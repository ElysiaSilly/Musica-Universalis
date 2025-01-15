package com.elysiasilly.musalis.client.screen;

import com.elysiasilly.babel.client.gui.BabelScreen;
import com.elysiasilly.babel.client.gui.BabelWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
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
        this.tab = BuiltInRegistries.CREATIVE_MODE_TAB.getOrThrow(CreativeModeTabs.REDSTONE_BLOCKS);
        //this.tab = MUCreativeTabs.MACHINE.get();
        this.renderDebug = true;
    }

    @Override
    public void initAfter() {

    }

    @Override
    public List<BabelWidget> initWidgets() {
        int leftPos = (this.width - 176) / 2;
        int topPos = (this.height - 166) / 2;

        List<BabelWidget> widgets = new ArrayList<>();

        Vec2 start = new Vec2(leftPos + 9, topPos);
        int offset = 18;

        int X = 0;
        int Y = 0;
        for(ItemStack stack : tab.getDisplayItems()) {

            if(X % 9 == 0) {
                Y++;
                X = 0;
            }

            if(Y >= 6) break;

            ItemStackWidget widget = new ItemStackWidget(null, this);
            widget.stack = stack.copy();
            widget.position = new Vec2(start.x + (X * offset), start.y + (Y * offset));

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

        int leftPos = (this.width - 176) / 2;
        int topPos = (this.height - 166) / 2;

        guiGraphics.blit(tab.getBackgroundTexture(), leftPos, topPos, 0, 0, 176, 166);

    }
}
