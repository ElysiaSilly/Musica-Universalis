package com.elysiasilly.musalis.client.screen;

import com.elysiasilly.babel.client.gui.BabelWidget;
import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.util.MathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.particle.SquidInkParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class ItemStackWidget extends BabelWidget<BabelWidget, CreativeTab> {

    ItemStack stack = ItemStack.EMPTY;
    Vec3 rotOrigin;
    Vec3 rotation = new Vec3(0, -100, 0);

    float scale = 1;

    boolean copy = false;

    public ItemStackWidget(@Nullable BabelWidget parent, @NotNull CreativeTab screen) {
        super(parent, screen);
    }

    @Override
    public void initBefore() {
        randomizeRot();
        this.rotation = this.rotOrigin;

        this.hoverable = true;
        this.clickable = true;
        this.draggable = true;
    }

    @Override
    public void onClick(Vec2 mousePos, int button) {

        if(button == GLFW.GLFW_MOUSE_BUTTON_2 && copy && stack.getCount() > 1) {
            ItemStackWidget copy = new ItemStackWidget(null, this.screen);
            copy.stack = stack.split(stack.getCount() / 2);
            copy.copy = true;
            copy.boundStart = new Vec2(0 , 0 );
            copy.boundEnd = new Vec2(16, 16);
            copy.position = this.position;
            this.screen.add(copy);
            this.screen.draggedWidget = copy;
        }
    }

    @Override
    public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
        if(!this.screen.isDragging()) {
            if(this.copy) {
                move(mouseVelocity);
                this.position = mousePos().add(-8);
            } else {
                ItemStackWidget copy = new ItemStackWidget(null, this.screen);
                int count = button == GLFW.GLFW_MOUSE_BUTTON_1 ? 1 : stack.getMaxStackSize();
                copy.stack = stack.copyWithCount(count);
                copy.copy = true;
                copy.boundStart = new Vec2(0 , 0 );
                copy.boundEnd = new Vec2(16, 16);
                copy.position = mousePos;
                copy.rotation = this.rotation;

                this.screen.add(copy);
                this.screen.draggedWidget = copy;
            }
        }
    }

    public void randomizeRot() {
        int threshold = 7;
        if(Minecraft.getInstance().level == null) return;
        RandomSource random = Minecraft.getInstance().level.getRandom();
        this.rotOrigin = new Vec3(random.nextInt(-threshold, threshold), random.nextInt(-threshold, threshold), random.nextInt(-threshold, threshold));
    }

    @Override
    public void onDragRelease(Vec2 mousePos, int button) {
        if(this.screen.hoveredWidget instanceof ItemStackWidget widget && this.screen.hoveredWidget != this) {
            if(widget.copy) {
                if(widget.stack.is(this.stack.getItem())) {
                    widget.stack.grow(this.stack.getCount());
                    widget.randomizeRot();
                    destroy();
                }
            } else {
                destroy();
            }
        }
    }

    @Override
    public List<BabelWidget> initWidgets() {
        return List.of();
    }

    @Override
    public void tick() {
        if(this.copy) {
            //ParticleTypes.GLOW_SQUID_INK.getType().

            //SquidInkParticle.GlowInkProvider particle = new SquidInkParticle.GlowInkProvider()

            Minecraft.getInstance().particleEngine.createParticle(ParticleTypes.ANGRY_VILLAGER, this.position.x, this.position.y, 0, 0, 0, 0);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(this.position.x, this.position.y, 0);
        pose.pushPose();

        if(isDragging()) {
            this.rotation = rotation.add(-mouseVel().y * 2, (mouseVel().x  * 2), 0);
            this.scale = Mth.lerp(0.2f, scale, 2f);
            pose.translate(0, 0, 100);
        }

        if(this.screen.draggedWidget instanceof ItemStackWidget widget && isHovering()) {
            if(widget.stack.is(this.stack.getItem())) {
                this.scale = Mth.lerp(0.2f, scale, 2f);
            }
        }

        if(isHovering() && ((!this.screen.isSomethingDragging() && !isDragging()) || !this.copy)) {
            float clamp = MathUtil.numbers.castToRange(0, 360, 0, 1, (float) this.rotation.y);
            float rotationSpeed = (1 - clamp) * 4;
            this.rotation = rotation.add(0, rotationSpeed, 0);
            this.scale = Mth.lerp(0.2f, scale, 1.5f);
        } else {
            this.rotation = this.rotation.lerp(this.rotOrigin, 0.2);
            this.scale = Mth.lerp(0.2f, scale, 1f);
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
}
