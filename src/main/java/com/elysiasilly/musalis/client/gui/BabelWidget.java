package com.elysiasilly.musalis.client.gui;

import com.elysiasilly.musalis.core.util.MathUtil;
import com.elysiasilly.musalis.core.util.RGBA;
import com.elysiasilly.musalis.core.util.RenderUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

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
    Vec2 boundStart = new Vec2(0, 0), boundEnd = new Vec2(0, 0);
    Vec2 localBoundStart = new Vec2(0, 0), localBoundEnd = new Vec2(0, 0);

    boolean hoverable = false, clickable = false, draggable = false, anchored = false;

    boolean check = false;

    @SuppressWarnings("unchecked")
    public BabelWidget(@Nullable W parent, S screen) {
        this.screen = screen;
        this.parent = parent;
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

    public boolean isFocused() {
        return this.screen.isFocused(this);
    }

    public void initBefore() {}

    public void initAfter() {}

    abstract List<BabelWidget> initWidgets();

    public void onClick(Vec2 mousePos, int button) {}

    public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {}

    public void onType(char codePoint, int modifiers) {}

    public void onScroll(Vec2 mousePos, Vec2 scroll) {}

    void processRendering(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
        if(!check) return;
        localToGlobal();
        for(BabelWidget widget : this.subWidgets) widget.processRendering(guiGraphics, mousePos, partialTick);
        if(this.screen.renderDebug) renderDebug(guiGraphics, mousePos, partialTick);
        render(guiGraphics, mousePos, partialTick);
    }

    void processTicking() {
        if(!check) return;
        tick();
        for(BabelWidget widget : this.subWidgets) widget.processTicking();
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

    public void renderDebug(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        MultiBufferSource bufferSource = guiGraphics.bufferSource();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.gui());

        RGBA colour = isHovering() || isDragging() || isFocused() ? RGBA.colours.WHITE : new RGBA(this.depth / 2);

        RenderUtil.drawOutlineRectangle(consumer, matrix4f, MathUtil.vec2ToVec3(this.localBoundStart), MathUtil.vec2ToVec3(this.localBoundEnd), 1, colour);
    }

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

    public void moveWithChildren(Vec2 vec2) {
        this.position = this.position.add(vec2);
        for(BabelWidget widget : this.subWidgets) widget.moveWithChildren(vec2);
    }

    public void moveChildren(Vec2 vec2) {
        for(BabelWidget widget : this.subWidgets) widget.moveWithChildren(vec2);
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
