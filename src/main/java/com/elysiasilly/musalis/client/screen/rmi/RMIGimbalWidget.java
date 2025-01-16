package com.elysiasilly.musalis.client.screen.rmi;

import com.elysiasilly.babel.client.screen.BabelWidget;
import com.elysiasilly.musalis.client.screen.RMIScreen;
import com.elysiasilly.musalis.util.RGBA;
import com.elysiasilly.musalis.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RMIGimbalWidget extends BabelWidget<BabelWidget, RMIScreen> {

    public RMIGimbalWidget(@Nullable BabelWidget parent, RMIScreen screen) {
        super(parent, screen, null);
    }

    @Override
    public List<BabelWidget<?, ?>> initWidgets() {
        return List.of();
    }

    //@Override
    //public void initAfter() {
    //    this.clickable = true;
    //    this.hoverable = true;
    //    this.draggable = true;
    //}

    //@Override
    //public void onClick(Vec2 mousePos, int button) {
    //    if(button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) PacketDistributor.sendToServer(new RMIScreenPayload(4, 0));
    //}

    //@Override
    //public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
    //    PacketDistributor.sendToServer(new RMIScreenPayload(2, (int) (-mouseVelocity.y * .5)));
    //    PacketDistributor.sendToServer(new RMIScreenPayload(3, (int) (-mouseVelocity.x * .5)));
    //}

    @Override
    public void tick() {

    }

    @Override
    public void render(GuiGraphics guiGraphics, float partialTick) {

        PoseStack stack = guiGraphics.pose();

        stack.rotateAround(Axis.YP.rotationDegrees(this.screen.menu.getBE().getYRot()), this.bounds.position.x + 15, this.bounds.position.y + 15, 15);
        stack.rotateAround(Axis.XP.rotationDegrees(-this.screen.menu.getBE().getXRot()), this.bounds.position.x + 15, this.bounds.position.y + 15, 15);

        MultiBufferSource bufferSource = guiGraphics.bufferSource();

        stack.scale(30, 30, 30);

        stack.translate(1, 1, 0);

        RGBA rgba = RGBA.colours.WHITE;

        rgba.alpha = isHovering() || isDragging() ? 254 : (int) (255 * .5);

        RenderUtil.drawCube(bufferSource.getBuffer(RenderType.gui()), stack.last().pose(), 1, LightTexture.FULL_SKY, true, true, rgba);

        //Minecraft.getInstance().getBlockRenderer().renderSingleBlock(Blocks.REDSTONE_LAMP.defaultBlockState(), stack, bufferSource, RenderUtil.LIGHTING, OverlayTexture.NO_OVERLAY, null, RenderType.cutout());
    }
}
