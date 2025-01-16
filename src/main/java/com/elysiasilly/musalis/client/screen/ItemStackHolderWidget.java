package com.elysiasilly.musalis.client.screen;

import com.elysiasilly.babel.client.screen.BabelScreen;
import com.elysiasilly.babel.client.screen.BabelScreenUtil;
import com.elysiasilly.babel.client.screen.BabelWidget;
import com.elysiasilly.babel.client.screen.WidgetBounds;
import com.elysiasilly.babel.client.screen.widget.IClickListenerWidget;
import com.elysiasilly.babel.client.screen.widget.IHoverableWidget;
import com.elysiasilly.musalis.util.RGBA;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemStackHolderWidget extends BabelWidget<BabelWidget<?, ?>, BabelScreen<?, ?>> implements IClickListenerWidget, IHoverableWidget {

    public CreativeModeTab tab;

    public ItemStackHolderWidget(@NotNull BabelScreen<?, ?> screen, CreativeModeTab tab) {
        super(screen, new WidgetBounds(0, 16));
    }

    @Override
    public List<BabelWidget<?, ?>> initWidgets() {
        int leftPos = (this.screen.width - 176) / 2;
        int topPos = (this.screen.height - 166) / 2;

        this.bounds.localStart = new Vec2(leftPos, topPos);
        this.bounds.localEnd = new Vec2(leftPos + 176, topPos + 166);

        List<BabelWidget<?, ?>> widgets = new ArrayList<>();

        Vec2 start = new Vec2(leftPos + 9, topPos);
        int offset = 18;

        int X = 0;
        int Y = 0;

        CreativeModeTab tab = BuiltInRegistries.CREATIVE_MODE_TAB.getOrThrow(CreativeModeTabs.REDSTONE_BLOCKS);

        for(ItemStack stack : tab.getDisplayItems()) {

            if(X % 9 == 0) {
                Y++;
                X = 0;
            }

            if(Y >= 6) break;

            ItemStackWidget widget = new ItemStackWidget(this, this.screen, new WidgetBounds(0, 16));
            widget.stack = stack.copy();
            widget.bounds.position = new Vec2(start.x + (X * offset), start.y + (Y * offset));

            widgets.add(widget);

            X++;
        }

        return widgets;
    }

    @Override
    public void onClick(int button) {
        if(isDragging()) {
            this.screen.releaseDragged();
        } else if(isHovering() && !this.screen.isSomethingDragging()) {
            this.screen.setDragged(this);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, float partialTick) {
        if(isDragging()) {
            moveWithChildren(this.screen.getMouseDrag());
        }

        this.bounds.depth = 0;
        BabelScreenUtil.fill(this.bounds, guiGraphics.bufferSource().getBuffer(RenderType.gui()), guiGraphics.pose().last().pose(), new RGBA(.2f, .2f, .2f, .5f));

    }

    @Override
    public boolean canHover() {
        return true;
    }
}
