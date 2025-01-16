package com.elysiasilly.musalis.client.screen;

import com.elysiasilly.babel.client.gui.BabelWidget;
import com.elysiasilly.babel.client.gui.WidgetBounds;
import com.elysiasilly.babel.client.gui.widget.IClickListenerWidget;
import com.elysiasilly.babel.client.gui.widget.IHoverableWidget;
import com.elysiasilly.musalis.util.MathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

public class ItemStackWidget extends BabelWidget<BabelWidget<?, ?>, CreativeTab> implements IHoverableWidget, IClickListenerWidget {

    ItemStack stack = ItemStack.EMPTY;
    Vec3 rotOrigin;
    Vec3 rotation = new Vec3(0, -100, 0);

    float scale = 1;

    boolean mutable = false;

    public ItemStackWidget(@NotNull CreativeTab screen, WidgetBounds bounds) {
        super(screen, bounds);
    }

    @Override
    public void initBefore() {
        randomizeRot();
        this.rotation = this.rotOrigin;
    }

    public void randomizeRot() {
        int threshold = 7;
        if(Minecraft.getInstance().level == null) return;
        RandomSource random = Minecraft.getInstance().level.getRandom();
        this.rotOrigin = new Vec3(random.nextInt(-threshold, threshold), random.nextInt(-threshold, threshold), random.nextInt(-threshold, threshold));
    }

    @Override
    public void render(GuiGraphics guiGraphics, float partialTick) {
        if(isDragging()) {
            this.bounds.position = mousePos().add(-8);
        }

        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(this.bounds.position.x, this.bounds.position.y, 0);
        pose.pushPose();

        if(isDragging()) {
            //GLFW.glfwSetCursor(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_HAND_CURSOR);
            this.rotation = rotation.add(-mouseDrag().y * 2, (mouseDrag().x  * 2), 0);
            this.scale = Mth.lerp(0.2f, scale, 2f);
            pose.translate(0, 0, 100);
        }

        if(this.screen.draggedWidget instanceof ItemStackWidget widget && isHovering()) {
            if(widget.stack.is(this.stack.getItem())) {
                this.scale = Mth.lerp(0.2f, scale, 2f);
            }
        }

        if(isHovering() && ((!this.screen.isSomethingDragging() && !isDragging()) || !this.mutable)) {
            float clamp = MathUtil.numbers.castToRange(0, 360, 0, 1, (float) this.rotation.y);
            float rotationSpeed = (1 - clamp) * 2;
            this.rotation = rotation.add(0, rotationSpeed, 0);
            float scale = 1.5f;
            if(this.screen.isSomethingDragging()) scale = 1f;
            this.scale = Mth.lerp(0.2f, this.scale, scale);
        } else {
            this.rotation = this.rotation.lerp(this.rotOrigin, 0.2);
            this.scale = Mth.lerp(0.2f, this.scale, 1f);
        }

        pose.scale(this.scale, this.scale, this.scale);

        float transform = -MathUtil.numbers.castToRange(1f, 1.5f, 0f, 2.2f, this.scale);
        pose.translate(transform, transform, transform);

        pose.rotateAround(Axis.XP.rotationDegrees((float) this.rotation.x), 8, 8, 150);
        pose.rotateAround(Axis.YP.rotationDegrees((float) this.rotation.y), 8, 8, 150);
        pose.rotateAround(Axis.ZP.rotationDegrees((float) this.rotation.z), 8, 8, 150);

        guiGraphics.renderItem(stack, 0, 0);
        pose.popPose();

        pose.translate(0, 0, 200);
        if(isDragging() || isHovering()) {
            pose.translate(0, 0, 150);
        }
        float scale = .5f;
        pose.scale(scale, scale, 0);
        pose.translate(15, 15, 0);
        guiGraphics.renderItemDecorations(Minecraft.getInstance().font, stack, 0, 0);
        pose.popPose();
    }


    @Override
    public boolean canHover() {
        return true;
    }

    @Override
    public void onClick(int button) {
        if(isHovering() || isDragging()) {
            if(mutable) {
                if(this.screen.isSomethingHovering()) {
                    if(this.screen.hoveredWidget instanceof ItemStackWidget widget) {
                        if(!widget.mutable) {
                            if(ItemStack.isSameItem(this.stack, widget.stack)) {
                                if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                                    this.stack.grow(1);
                                }
                            } else {
                                if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                                    destroy();
                                }
                            }
                            if(button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                                if(this.stack.getCount() > 1) {
                                    this.stack.grow(-1);
                                } else {
                                    destroy();
                                }
                            }
                        }
                    }
                } else {
                    this.screen.releaseDragged();
                }
            } else {
                if(!this.screen.isSomethingDragging()) {
                    if(button == GLFW.GLFW_MOUSE_BUTTON_LEFT || button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                        ItemStackWidget copy = new ItemStackWidget(this.screen, new WidgetBounds(0, 16));
                        copy.stack = this.stack.copy();
                        copy.bounds.position = this.bounds.position;
                        copy.rotation = this.rotation;
                        this.screen.add(copy);
                        this.mutable = true;
                        this.screen.setDragged(this);
                    }
                    if(button == GLFW.GLFW_MOUSE_BUTTON_MIDDLE) {
                        ItemStackWidget copy = new ItemStackWidget(this.screen, new WidgetBounds(0, 16));
                        copy.stack = this.stack.copy();
                        this.stack.setCount(this.stack.getMaxStackSize());
                        copy.bounds.position = this.bounds.position;
                        copy.rotation = this.rotation;
                        this.screen.add(copy);
                        this.mutable = true;
                        this.screen.setDragged(this);
                    }
                }
            }
        }
    }
}
