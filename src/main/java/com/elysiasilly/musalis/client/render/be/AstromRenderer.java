package com.elysiasilly.musalis.client.render.be;

import com.elysiasilly.babel.client.render.BabelBERenderer;
import com.elysiasilly.musalis.client.render.IDepthRenderTarget;
import com.elysiasilly.musalis.client.render.MUCoreShaders;
import com.elysiasilly.musalis.common.blockentity.AstromBE;
import com.elysiasilly.musalis.util.RGBA;
import com.elysiasilly.musalis.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AstromRenderer extends BabelBERenderer<AstromBE> {

    public AstromRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(AstromBE astromBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        VertexConsumer consumer = multiBufferSource.getBuffer(MUCoreShaders.depthShader.get());

        ShaderInstance instance = MUCoreShaders.depthShader.instance();

        if(((IDepthRenderTarget) Minecraft.getInstance().levelRenderer).getDepthRenderTarget() == null) return;
        instance.setSampler("DepthBuffer", ((IDepthRenderTarget) Minecraft.getInstance().levelRenderer).getDepthRenderTarget().getDepthTextureId());

        RenderUtil.setUniform(instance, "nearPlaneDistance", GameRenderer.PROJECTION_Z_NEAR);
        RenderUtil.setUniform(instance, "farPlaneDistance", Minecraft.getInstance().gameRenderer.getDepthFar());

        poseStack.translate(.01, .01, .01);

        RenderUtil.drawCube(consumer, poseStack.last().pose(), 5.98F, packedLight, false, false, RGBA.colours.WHITE);
    }

    @Override
    public AABB getRenderBoundingBox(AstromBE blockEntity) {
        return AABB.INFINITE;
    }

    @Override
    public boolean shouldRenderOffScreen(AstromBE blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(AstromBE blockEntity, Vec3 cameraPos) {
        return true;
    }
}
