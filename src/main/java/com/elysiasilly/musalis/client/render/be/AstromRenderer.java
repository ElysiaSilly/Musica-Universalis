package com.elysiasilly.musalis.client.render.be;

import com.elysiasilly.babel.client.render.BabelBERenderer;
import com.elysiasilly.musalis.client.render.MUCoreShaders;
import com.elysiasilly.musalis.client.render.MURenderTypes;
import com.elysiasilly.musalis.common.block.be.AstromBE;
import com.elysiasilly.musalis.common.block.be.RMIBE;
import com.elysiasilly.musalis.core.util.RGBA;
import com.elysiasilly.musalis.core.util.RenderUtil;
import com.elysiasilly.musalis.core.awooga;
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
        VertexConsumer consumer = multiBufferSource.getBuffer(MURenderTypes.getDepthShader());

        ShaderInstance instance = MUCoreShaders.getDepthShader();

        if(((awooga) Minecraft.getInstance().levelRenderer).musicaUniversalis$getDepthRenderTarget() == null) return;
        instance.setSampler("DepthBuffer", ((awooga) Minecraft.getInstance().levelRenderer).musicaUniversalis$getDepthRenderTarget().getDepthTextureId());

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
