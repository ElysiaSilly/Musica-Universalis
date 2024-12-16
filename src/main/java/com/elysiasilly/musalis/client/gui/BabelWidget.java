package com.elysiasilly.musalis.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public abstract class BabelWidget<W extends BabelWidget, S extends BabelScreen> {

    final S screen;
    W parent;

    final List<BabelWidget> subWidgets = new ArrayList<>();
    final String ID;

    float depth = 1;
    Vec2 position = new Vec2(0, 0);
    Vec2 boundStart, boundEnd = new Vec2(0, 0);
    Vec2 localBoundStart, localBoundEnd = new Vec2(0, 0);

    boolean hoverable = false, clickable = false, draggable = false, ticks = false, anchored = false;

    boolean check = false;

    @SuppressWarnings("unchecked")
    public BabelWidget(S screen, @Nullable W parent) {
        this.screen = screen;
        this.parent = parent;
        this.ID = createID();
        initBefore();
        this.subWidgets.addAll(initWidgets());
        this.screen.allWidgets.addAll(this.subWidgets);
        initAfter();
        this.check = true;
    }

    @SuppressWarnings("unchecked")
    public BabelWidget(S screen) {
        this.screen = screen;
        this.parent = null;
        this.ID = createID();
        initBefore();
        this.subWidgets.addAll(initWidgets());
        this.screen.allWidgets.addAll(this.subWidgets);
        initAfter();
        this.check = true;
    }

    // todo
    public String createID() {
        return String.valueOf(this.screen.widgets.size());
    }

    public boolean isHovering() {
        return this.screen.isHovering(this);
    }

    public boolean isDragging() {
        return this.screen.isDragging(this);
    }

    public abstract void initBefore();

    public abstract void initAfter();

    abstract List<BabelWidget> initWidgets();

    public abstract void onClick(Vec2 mousePos, int button);

    public abstract void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity);

    void processRendering(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
        if(!check) return;
        localToGlobal();
        for(BabelWidget widget : this.subWidgets) widget.processRendering(guiGraphics, mousePos, partialTick);
        render(guiGraphics, mousePos, partialTick);
    }

    void processTicking() {
        if(!check) return;
        tick();
        for(BabelWidget widget : this.subWidgets) if(widget.ticks) widget.processTicking();
    }

    abstract void tick();  // todo : make sure bounds exist before ticking

    void localToGlobal() {
        if(this.boundStart != null && this.boundEnd != null) {
            this.localBoundStart = this.position.add(boundStart).add(this.screen.getOffset()); // todo : offset stuff (parallax woohoo)
            this.localBoundEnd = this.position.add(boundEnd).add(this.screen.getOffset());
        }
    }

    public boolean hasBoundaries() {
        return this.boundStart != null && this.boundEnd != null && this.localBoundStart != null && this.localBoundEnd != null;
    }

    public abstract void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick);

    public void destroy() {
        if(this.parent != null) {
            this.parent.subWidgets.remove(this);
            this.screen.allWidgets.remove(this);
        } else {
            this.screen.widgets.remove(this);
        }
    }

    public void move(Vec2 vec2) {
        this.position = this.position.add(vec2);


    }

    public static class Bounds {

        final BabelWidget widget;
        final BabelScreen screen;

        Vec2 localStart, localEnd;
        Vec2 globalStart, globalEnd;

        public Bounds(Vec2 start, Vec2 end, BabelWidget widget, BabelScreen screen) {
            this.localStart = start;
            this.globalStart = start;
            this.localEnd = end;
            this.globalEnd = end;
            this.widget = widget;
            this.screen = screen;
        }

        public void calculateGlobals(Vec2 globalPosition) {
            this.globalStart = globalPosition.add(localStart);
            this.globalEnd = globalPosition.add(localEnd);
            if(!widget.anchored) {
                this.globalStart.add(this.screen.getOffset());
                this.globalEnd.add(this.screen.getOffset());
            }
        }

        public Vec2 getCentre() {
            return null;
        }
    }
}
