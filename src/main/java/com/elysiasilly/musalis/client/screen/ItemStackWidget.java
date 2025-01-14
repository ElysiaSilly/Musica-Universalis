package com.elysiasilly.musalis.client.screen;

import com.elysiasilly.babel.client.gui.BabelWidget;
import com.elysiasilly.musalis.util.MathUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemStackWidget extends BabelWidget<BabelWidget, CreativeTab> {

    ItemStack stack;
    Vec3 rotOrigin;
    Vec3 rotation = Vec3.ZERO;

    float scale = 0;

    public ItemStackWidget(@Nullable BabelWidget parent, @NotNull CreativeTab screen) {
        super(parent, screen);
    }

    @Override
    public void initBefore() {
        int threshold = 7;
        RandomSource random = Minecraft.getInstance().level.getRandom();
        this.rotOrigin = new Vec3(random.nextInt(-threshold, threshold), random.nextInt(-threshold, threshold), random.nextInt(-threshold, threshold));
        this.rotation = this.rotOrigin;

        this.hoverable = true;
        this.clickable = true;
        this.draggable = true;

    }

    @Override
    public void initAfter() {
        //this.startPos = this.position;
        //this.endPos = this.position.add(new Vec2(0, .5f));
    }

    @Override
    public void onClick(Vec2 mousePos, int button) {
        super.onClick(mousePos, button);
    }

    @Override
    public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
        super.onDrag(mousePos, button, mouseVelocity);
    }

    @Override
    public List<BabelWidget> initWidgets() {
        return List.of();
    }

    @Override
    public void tick() {
        if(this.startPos == null) {
            this.startPos = this.position;
            this.endPos = this.position.add(new Vec2(0, -3));
        }
    }

    Vec2 startPos;
    Vec2 endPos;

    @Override
    public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
        PoseStack pose = guiGraphics.pose();
        pose.pushPose();
        pose.translate(this.position.x, this.position.y, 0);

        //double delta = 0.5;

        //if(this.endPos != null) {
        //    if(isHovering() && this.position.y > this.endPos.y) {
        //        this.position = MathUtil.vectors.lerp(this.position, this.endPos, delta);
        //    } else if (!isHovering() && this.position.y < this.startPos.y) {
        //        this.position = MathUtil.vectors.lerp(this.position, this.startPos, delta * 2);
        //    }
        //}

        if(isHovering()) {
            this.rotation = rotation.add(0, 3, 0);
            this.scale = Mth.lerp(0.2f, scale, 1.5f);
        } else {
            this.rotation = this.rotation.lerp(this.rotOrigin, 0.2);
            this.scale = Mth.lerp(0.2f, scale, 1f);
        }


        pose.scale(this.scale, this.scale, this.scale);

        // todo : ???
        pose.translate(this.scale * -2, this.scale * -2, this.scale * -2);

        pose.rotateAround(Axis.XP.rotationDegrees((float) this.rotation.x), 8, 8, 150);
        pose.rotateAround(Axis.YP.rotationDegrees((float) this.rotation.y), 8, 8, 150);
        pose.rotateAround(Axis.ZP.rotationDegrees((float) this.rotation.z), 8, 8, 150);

        guiGraphics.renderItem(stack, 0, 0);
        pose.popPose();
    }
}
