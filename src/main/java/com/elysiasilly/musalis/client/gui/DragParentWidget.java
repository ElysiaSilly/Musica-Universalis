package com.elysiasilly.musalis.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.Vec2;

import java.util.List;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DragParentWidget extends BabelWidget{

    public DragParentWidget(BabelWidget parent, BabelScreen screen) {
        super(parent, screen);
    }

    @Override
    public void initAfter() {
        this.draggable = true;
        this.hoverable = true;
        this.clickable = true;
        this.depth = this.parent.depth + .1f;
    }

    @Override
    List<BabelWidget> initWidgets() {
        return List.of();
    }

    @Override
    public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
        if(button == 0) {
            this.parent.moveWithChildren(mouseVelocity);
        }
    }

    @Override
    void tick() {

    }

    @Override
    public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {

    }
}
