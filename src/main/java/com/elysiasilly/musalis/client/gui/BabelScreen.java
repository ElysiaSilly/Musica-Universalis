package com.elysiasilly.musalis.client.gui;

import com.elysiasilly.musalis.core.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public abstract class BabelScreen<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {

    final Minecraft minecraft;
    final T menu;

    final List<BabelWidget> widgets = new ArrayList<>(), allWidgets = new ArrayList<>();

    private Vec2 mousePosPrevious = new Vec2(0, 0), mousePos = new Vec2(0, 0), mouseVelocity = new Vec2(0, 0);
    private Vec2 offset = new Vec2(0, 0);

    BabelWidget hoveredWidget = null, draggedWidget = null;

    boolean draggable;
    //boolean parallax;

    boolean check = false;

    protected BabelScreen(T menu, Component title) {
        super(title);
        this.menu = menu;
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    protected void init() {
        initBefore();
        List<BabelWidget> widgets = initWidgets();
        this.widgets.addAll(widgets);
        this.allWidgets.addAll(widgets);
        initAfter();
        this.check = true;
    }

    public T getMenu() {
        return this.menu;
    }

    abstract @Nullable ResourceLocation mousePointerTexture();

    void renderMouse(GuiGraphics guiGraphics, Vec2 mousePos) {
        if(mousePointerTexture() == null) return;
        GLFW.glfwSetInputMode(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
        // todo : is there a proper way to render cursors?
        guiGraphics.blitSprite(Objects.requireNonNull(mousePointerTexture()), (int) mousePos.x - 4, (int) mousePos.y - 2, 16, 16);
    }

    abstract void initBefore();

    abstract void initAfter();

    abstract List<BabelWidget> initWidgets();

    void updateHover() {
        BabelWidget save = null;
        for(BabelWidget widget : this.allWidgets) {
            if(widget.hasBoundaries()) {
                if(MathUtil.withinBounds(this.mousePos, widget.localBoundStart, widget.localBoundEnd)) {
                    save = save == null ? isDragging(widget) ? null : widget : save.depth < widget.depth ? widget : save; // todo : clean this up wtf is going on
                }
            }
        }

        this.hoveredWidget = save;
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        updateHover();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if(isSomethingHovering()) if(this.hoveredWidget.clickable) this.hoveredWidget.onClick(this.mousePos, button);
        return super.mouseClicked(mouseX, mouseY, button); // do i need this stuff? :c
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if(isSomethingHovering() && !isSomethingDragging()) if(this.hoveredWidget.draggable) this.draggedWidget = this.hoveredWidget;
        if(isSomethingDragging()) this.draggedWidget.onDrag(this.mousePos, button, this.mouseVelocity);
        if(!isSomethingDragging() && !isSomethingHovering() && this.draggable) this.offset = this.offset.add(mouseVelocity);
        return true;//super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(this.draggedWidget != null) this.draggedWidget = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    boolean isHovering(BabelWidget widget) {
        if(this.hoveredWidget == null) return false;
        return this.hoveredWidget.equals(widget);
    }

    public BabelWidget getHoveredWidget() {
        return this.hoveredWidget;
    }

    public boolean isSomethingHovering() {
        return this.hoveredWidget != null;
    }

    boolean isDragging(BabelWidget widget) {
        if(this.draggedWidget == null) return false;
        return this.draggedWidget.equals(widget);
    }

    public BabelWidget getDraggedWidget() {
        return this.draggedWidget;
    }

    public boolean isSomethingDragging() {
        return this.draggedWidget != null;
    }

    public boolean isNothingInteracted() {
        return this.draggedWidget == null && this.hoveredWidget == null;
    }

    public Vec2 getMousePos() {
        return this.mousePos;
    }

    public Vec2 getMouseVelocity() {
        return this.mouseVelocity;
    }

    public Vec2 getOffset() {
        return this.offset;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);

        if(!this.check) return;

        this.mousePos = new Vec2(mouseX, mouseY);
        this.mouseVelocity = this.mousePos.add(new Vec2(-this.mousePosPrevious.x, -this.mousePosPrevious.y));

        for(BabelWidget widget : this.widgets) widget.processRendering(guiGraphics, this.mousePos, partialTick);

        renderMouse(guiGraphics, this.mousePos);
        this.mousePosPrevious = this.mousePos;
    }

    @Override
    public void tick() {
        for(BabelWidget widget : this.widgets) if(widget.ticks) widget.processTicking();
    }

    static class actions{

    }
}
