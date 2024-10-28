package com.elysiasilly.musalis.common.block.ropeblock;

import com.elysiasilly.musalis.client.render.MusicaRenderTypes;
import com.elysiasilly.musalis.common.physics.rope.Rope;
import com.elysiasilly.musalis.core.util.RenderUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class RopeBERenderer implements BlockEntityRenderer<RopeBE> {

    public RopeBERenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(RopeBE ropeBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {


        if(ropeBE.getRope() == null) return;

        for (Rope.RopeSegment segment : ropeBE.getRope().segments) {

            Vec3 position = segment.getPosition();

            //poseStack.translate(0.5, 0.5, 0.5);

            Matrix4f matrix4f = poseStack.last().pose();
            VertexConsumer consumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(MusicaRenderTypes.getTestingShader());

            Vec3 start = new Vec3(0, 0, 0);
            Vec3 end = new Vec3(1, 0, 1);
            Vec3 offset = new Vec3(0, position.y, 0);

            RenderUtil.renderPlane(consumer, matrix4f, packedLight, 0, start, end, offset, false);
        }

    }

}
