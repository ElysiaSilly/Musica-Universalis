package com.elysiasilly.musalis.client.gui.rmi;

import com.elysiasilly.babel.client.gui.BabelWidget;
import com.elysiasilly.musalis.client.gui.RMIScreen;
import com.elysiasilly.musalis.core.util.MathUtil;
import com.elysiasilly.musalis.core.util.RGBA;
import com.elysiasilly.musalis.core.util.RenderUtil;
import com.elysiasilly.musalis.networking.payloads.RMIScreenPayload;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class RMIGimbalWidget extends BabelWidget<BabelWidget, RMIScreen> {

    public RMIGimbalWidget(@Nullable BabelWidget parent, RMIScreen screen) {
        super(parent, screen);
    }

    @Override
    public List<BabelWidget> initWidgets() {
        return List.of();
    }

    @Override
    public void initAfter() {
        this.clickable = true;
        this.hoverable = true;
        this.draggable = true;
    }

    @Override
    public void onClick(Vec2 mousePos, int button) {
        if(button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) PacketDistributor.sendToServer(new RMIScreenPayload(4, 0));
    }

    @Override
    public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
        PacketDistributor.sendToServer(new RMIScreenPayload(2, (int) (-mouseVelocity.y * .5)));
        PacketDistributor.sendToServer(new RMIScreenPayload(3, (int) (-mouseVelocity.x * .5)));
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {

        PoseStack stack = guiGraphics.pose();

        stack.rotateAround(Axis.YP.rotationDegrees(this.screen.menu.getBE().getYRot()), this.position.x + 15, this.position.y + 15, 15);
        stack.rotateAround(Axis.XP.rotationDegrees(-this.screen.menu.getBE().getXRot()), this.position.x + 15, this.position.y + 15, 15);

        MultiBufferSource bufferSource = guiGraphics.bufferSource();

        stack.scale(30, 30, 30);

        stack.translate(1, 1, 0);

        RGBA rgba = RGBA.colours.WHITE;
        if(isHovering() || isDragging()) {
            rgba.a(254);

        } else {
            rgba.a((int) (255 * .5));

        }


        RenderUtil.drawCube(bufferSource.getBuffer(RenderType.gui()), stack.last().pose(), 1, RenderUtil.LIGHTING, true, true, rgba);

        //Minecraft.getInstance().getBlockRenderer().renderSingleBlock(Blocks.REDSTONE_LAMP.defaultBlockState(), stack, bufferSource, RenderUtil.LIGHTING, OverlayTexture.NO_OVERLAY, null, RenderType.cutout());
    }
}
