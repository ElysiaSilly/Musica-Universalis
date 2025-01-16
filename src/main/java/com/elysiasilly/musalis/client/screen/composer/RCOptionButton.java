package com.elysiasilly.musalis.client.screen.composer;

import com.elysiasilly.babel.client.gui.BabelWidget;

public class RCOptionButton extends BabelWidget<BabelWidget, ResonanceComposerScreen> {

    final ResonanceComposerScreen.Options option;

    public RCOptionButton(BabelWidget parent, ResonanceComposerScreen screen, ResonanceComposerScreen.Options option) {
        super(parent, screen, null);
        this.option = option;
    }

    //@Override
    //public void initAfter() {
    //    this.clickable = true;
    //}

    //@Override
    //public void onClick(Vec2 mousePos, int button) {
    //    this.screen.selected = this.option;
    //}


    //@Override
    //public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
    //    if(this.screen.selected == this.option) {
    //        Matrix4f matrix4f = guiGraphics.pose().last().pose();
    //        MultiBufferSource bufferSource = guiGraphics.bufferSource();
    //        VertexConsumer consumer = bufferSource.getBuffer(RenderType.gui());
    //        RenderUtil.drawOutlineRectangle(consumer, matrix4f,  Conversions.vector.vec3(this.localBoundStart),  Conversions.vector.vec3(this.localBoundEnd), 2, RGBA.colours.WHITE);
    //    }
    //}
}
