package com.elysiasilly.musalis.client.render.entity;

import com.elysiasilly.musalis.common.world.ether.EtherCoreEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

public class EtherCoreEntityRenderer extends EntityRenderer<EtherCoreEntity> {
    public EtherCoreEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(EtherCoreEntity etherCoreEntity) {
        return null;
    }

    // todo : make not shitty
    @Override
    public void render(EtherCoreEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);

        BakedModel model = Minecraft.getInstance().getModelManager().getModel(entity.getCore().getModel());

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.cutout());

        poseStack.pushPose();

        poseStack.translate(-0.5f, 0, -0.5);

        float angleY = (float) ((0.1f * (Minecraft.getInstance().player.tickCount + 0.09) * entity.getYRot()) * .001f);
        float angleX = (float) ((0.1f * (Minecraft.getInstance().player.tickCount + 0.09) * entity.getXRot()) * .0001f);

        poseStack.rotateAround(Axis.YP.rotationDegrees(angleY), 0.5f, 0, 0.5f);
        poseStack.rotateAround(Axis.XP.rotationDegrees(angleX), 0.5f, (float) 1 /16 * 1, 0.5f);

        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), consumer, null, model, 255, 255, 255, 200, packedLight);

        poseStack.popPose();
    }
}
