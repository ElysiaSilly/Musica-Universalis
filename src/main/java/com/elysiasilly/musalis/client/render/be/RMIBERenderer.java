package com.elysiasilly.musalis.client.render.be;

import com.elysiasilly.babel.client.render.BabelBERenderer;
import com.elysiasilly.musalis.client.screen.RMIScreen;
import com.elysiasilly.musalis.common.blockentity.RMIBE;
import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class RMIBERenderer extends BabelBERenderer<RMIBE> {

    static final ResourceLocation TEXTURE = Musalis.location("textures/block/rmi.png");

    static final RenderUtil.ObjRenderer DISH = new RenderUtil.ObjRenderer(Musalis.location("special/rmi"), false, true, true, true, null);
    static final RenderUtil.ObjRenderer FRAME = new RenderUtil.ObjRenderer(Musalis.location("special/rmi_frame"), false, true, true, true, null);

    public RMIBERenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(RMIBE be, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {

        poseStack.rotateAround(Axis.YP.rotationDegrees(be.getYRot()), .5f, 0, .5f);

        poseStack.pushPose();

        if(be.getXRot() < 0) poseStack.translate(0, be.getXRot() * .04, be.getXRot() * .035);

        poseStack.translate(.5f, be.getPosition(), .5f);
        poseStack.translate(0, .3, -1);
        poseStack.rotateAround(Axis.XP.rotationDegrees(be.getXRot()), 1f, 2.1875f, 1f);

        boolean flag = true;

        if(Minecraft.getInstance().screen instanceof RMIScreen screen) if(screen.menu.getBE().equals(be)) flag = false;

        if(flag) DISH.render(poseStack, multiBufferSource, RenderType.entityCutout(TEXTURE), LightTexture.FULL_SKY);

        poseStack.popPose();

        poseStack.translate(.5, 0, .5);

        for(int i = 0; i <= be.getPosition() + 1; i++) {
            poseStack.translate(0, 1, 0);
            poseStack.pushPose();

            FRAME.render(poseStack, multiBufferSource, RenderType.entityCutout(TEXTURE), 0xFFFFFF);
            poseStack.popPose();
        }
    }

    @Override
    public AABB getRenderBoundingBox(RMIBE blockEntity) {
        return AABB.INFINITE;
    }

    @Override
    public boolean shouldRenderOffScreen(RMIBE blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(RMIBE blockEntity, Vec3 cameraPos) {
        return true;
    }
}
