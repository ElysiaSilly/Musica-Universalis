package com.elysiasilly.musalis.client.gui.composer;

import com.elysiasilly.babel.client.gui.BabelWidget;
import com.elysiasilly.musalis.core.util.MathUtil;
import com.elysiasilly.musalis.core.util.RGBA;
import com.elysiasilly.musalis.core.util.RenderUtil;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix4f;

import java.util.List;

public class RCOptionButton extends BabelWidget<BabelWidget, ResonanceComposerScreen> {

    final ResonanceComposerScreen.Options option;

    public RCOptionButton(BabelWidget parent, ResonanceComposerScreen screen, ResonanceComposerScreen.Options option) {
        super(parent, screen);
        this.option = option;
    }

    @Override
    public void initAfter() {
        this.clickable = true;
    }

    @Override
    public List<BabelWidget> initWidgets() {
        return List.of();
    }

    @Override
    public void onClick(Vec2 mousePos, int button) {
        this.screen.selected = this.option;
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
        if(this.screen.selected == this.option) {
            Matrix4f matrix4f = guiGraphics.pose().last().pose();
            MultiBufferSource bufferSource = guiGraphics.bufferSource();
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.gui());

            RenderUtil.drawOutlineRectangle(consumer, matrix4f, MathUtil.vec2ToVec3(this.localBoundStart), MathUtil.vec2ToVec3(this.localBoundEnd), 2, RGBA.colours.WHITE);
        }
    }
}
