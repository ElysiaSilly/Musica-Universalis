package com.elysiasilly.babel.client.gui;

import com.elysiasilly.musalis.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public abstract class BabelScreen<MENU extends AbstractContainerMenu> extends Screen implements MenuAccess<MENU> {

    public final MENU menu;

    public final List<BabelWidget> children = new ArrayList<>(), descendants = new ArrayList<>();

    public Vec2 mousePosPrevious = new Vec2(0, 0), mousePos = new Vec2(0, 0), mouseVelocity = new Vec2(0, 0);
    public Vec2 offset = new Vec2(0, 0);

    public BabelWidget hoveredWidget = null, draggedWidget = null, focusedWidget = null;

    public boolean renderDebug = false;
    public boolean draggable = false;
    //boolean parallax;

    boolean check = false;

    public BabelScreen(@Nullable MENU menu) {
        super(Component.empty());
        this.menu = menu;
    }

    @Override
    public void init() {
        initBefore();
        List<BabelWidget> widgets = initWidgets();
        this.children.addAll(widgets);
        this.descendants.addAll(widgets);
        initAfter();
        this.check = true;
    }

    public @NotNull MENU getMenu() {
        return this.menu;
    }

    public abstract @Nullable ResourceLocation mousePointerTexture();

    public void renderMouse(GuiGraphics guiGraphics, Vec2 mousePos) {
        if(mousePointerTexture() == null) return;
        try
        {
            //GLFW.glfwSetInputMode(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
            // todo : is there a proper way to render cursors?

            BufferedImage bufferedImage = ImageIO.read(minecraft.getResourceManager().open(Objects.requireNonNull(mousePointerTexture())));

            /*
            ByteBuffer buffer = BufferUtils.createByteBuffer(bufferedImage.getWidth() * bufferedImage.getHeight());

            if(bufferedImage.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {
                final BufferedImage convertedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
                final Graphics2D graphics = convertedImage.createGraphics();
                final int targetWidth = bufferedImage.getWidth();
                final int targetHeight = bufferedImage.getHeight();
                graphics.drawImage(bufferedImage, 0, 0, targetWidth, targetHeight, null);
                graphics.dispose();
                bufferedImage = convertedImage;
            }


            for (int i = 0; i < bufferedImage.getHeight(); i++) {
                for (int j = 0; j < bufferedImage.getWidth(); j++) {
                    int colorSpace = bufferedImage.getRGB(j, i);
                    buffer.put((byte) ((colorSpace << 8) >> 24));
                    buffer.put((byte) ((colorSpace << 16) >> 24));
                    buffer.put((byte) ((colorSpace << 24) >> 24));
                    buffer.put((byte) (colorSpace >> 24));
                }
            }

             */
;
            GLFWImage image = imageToGLFWImage(bufferedImage);
            //image.set(image.width(), image.height(), buffer);

            System.out.println(image);

            long cursor = GLFW.glfwCreateCursor(image, 0, 16);
            GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), cursor);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //guiGraphics.blitSprite(Objects.requireNonNull(mousePointerTexture()), (int) mousePos.x - 4, (int) mousePos.y - 2, 16, 16);
    }


    private static GLFWImage imageToGLFWImage(BufferedImage image) {

        if (image.getType() != BufferedImage.TYPE_INT_ARGB_PRE) {
            final BufferedImage convertedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
            final Graphics2D graphics = convertedImage.createGraphics();
            final int targetWidth = image.getWidth();
            final int targetHeight = image.getHeight();
            graphics.drawImage(image, 0, 0, targetWidth, targetHeight, null);
            graphics.dispose();
            image = convertedImage;
        }

        final ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int colorSpace = image.getRGB(j, i);
                buffer.put((byte) ((colorSpace << 8) >> 24));
                buffer.put((byte) ((colorSpace << 16) >> 24));
                buffer.put((byte) ((colorSpace << 24) >> 24));
                buffer.put((byte) (colorSpace >> 24));
            }
        }

        buffer.flip();
        final GLFWImage result = GLFWImage.create();
        result.set(image.getWidth(), image.getHeight(), buffer);
        return result;
    }



    public abstract void initBefore();

    public abstract void initAfter();

    public abstract List<BabelWidget> initWidgets();

    public void updateHover() {
        BabelWidget save = null;
        for(BabelWidget widget : this.descendants) {
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
        this.focusedWidget = isSomethingHovering() ? this.hoveredWidget.clickable ? this.hoveredWidget : null : null;
        return super.mouseClicked(mouseX, mouseY, button); // do i need this stuff? :c
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if(isSomethingFocused()) this.focusedWidget.onType(codePoint, modifiers);
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        if(isSomethingHovering()) this.hoveredWidget.onScroll(this.mousePos, new Vec2((float) scrollX, (float) scrollY));
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
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

    public boolean isHovering(BabelWidget widget) {
        if(this.hoveredWidget == null) return false;
        return this.hoveredWidget.equals(widget);
    }

    public BabelWidget getHoveredWidget() {
        return this.hoveredWidget;
    }

    public boolean isSomethingHovering() {
        return this.hoveredWidget != null;
    }

    public boolean isDragging(BabelWidget widget) {
        if(this.draggedWidget == null) return false;
        return this.draggedWidget.equals(widget);
    }

    public boolean isFocused(BabelWidget widget) {
        if(this.focusedWidget == null) return false;
        return this.focusedWidget.equals(widget);
    }

    public BabelWidget getDraggedWidget() {
        return this.draggedWidget;
    }

    public boolean isSomethingDragging() {
        return this.draggedWidget != null;
    }

    public boolean isSomethingFocused() {
        return this.focusedWidget != null;
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
        renderBackground(guiGraphics, partialTick);

        if(!this.check) return;

        this.mousePos = new Vec2(mouseX, mouseY);
        this.mouseVelocity = this.mousePos.add(new Vec2(-this.mousePosPrevious.x, -this.mousePosPrevious.y));

        for(BabelWidget widget : this.children) widget.processRendering(guiGraphics, this.mousePos, partialTick);

        renderMouse(guiGraphics, this.mousePos);
        this.mousePosPrevious = this.mousePos;
    }

    public abstract void renderBackground(GuiGraphics guiGraphics, float partialTick);

    @Override
    public void tick() {
        for(BabelWidget widget : this.children) widget.processTicking();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    static class actions{

    }
}
