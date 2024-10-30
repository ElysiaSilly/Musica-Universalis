package com.elysiasilly.musalis.common.block.ropeblock;

import com.elysiasilly.musalis.client.render.MusicaRenderTypes;
import com.elysiasilly.musalis.common.physics.rope.Rope;
import com.elysiasilly.musalis.core.util.RenderUtil;
import com.elysiasilly.musalis.core.util.type.RGBA;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector4d;

public class RopeBERenderer implements BlockEntityRenderer<RopeBE> {

    public RopeBERenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(RopeBE ropeBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        if(ropeBE.getRopeSegmentsCLIENT() == null) return;
        if(ropeBE.getRopeSegmentsCLIENT().isEmpty()) return;

        Vec3 previousSegment = new Vec3(.5f, 0, .5f);

        int num = 0;

        for (Vec3 segment : ropeBE.getRopeSegmentsCLIENT()) {

            RGBA colour = new RGBA(255, 255, 255, 255);

            if(num % 2 == 0) colour = new RGBA(255, 0, 0, 255);

            RenderUtil.renderLine(multiBufferSource, poseStack.last(), colour, previousSegment, segment);

            previousSegment = segment;
            num++;
        }
    }

    @Override
    public boolean shouldRenderOffScreen(RopeBE blockEntity) {
        return true;
    }
}
