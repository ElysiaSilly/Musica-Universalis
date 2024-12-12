package com.elysiasilly.musalis.client.render.be;

import com.elysiasilly.musalis.common.block.be.CrResonanceRecorderBE;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemDisplayContext;

public class ResonanceRecorderRenderer implements BlockEntityRenderer<CrResonanceRecorderBE> {

    public ResonanceRecorderRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CrResonanceRecorderBE be, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {

        poseStack.pushPose();

        if(be.getItem() != null) {
            if(!be.getItem().isEmpty()) {

                poseStack.translate(0.5, 1, 0.5);

                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));

                poseStack.scale(0.6f, 0.6f, 0.6f);

                BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(be.getItem(), Minecraft.getInstance().level, null, 1);

                Minecraft.getInstance().getItemRenderer().render(be.getItem(), ItemDisplayContext.FIXED, false, poseStack, multiBufferSource, 200, OverlayTexture.NO_OVERLAY, model);
            }
        }

        poseStack.popPose();

        if(be.getDisk() != null) {
            if(!be.getDisk().isEmpty()) {

                int offset = be.getEjectDisk() ? 7 : 6;

                poseStack.translate((float) 1 / 16 * offset, (float) 1 / 16 * 7, 0);

                String string = BuiltInRegistries.ITEM.getKey(be.getDisk().getItem()).toString();
                string = string.substring(string.indexOf(":") + 1);

                BakedModel model = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(MusicaUniversalis.location("special/" + string)));

                VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.cutout());

                Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), consumer, null, model.applyTransform(ItemDisplayContext.GROUND, poseStack, false), 255, 255, 255, 200, packedOverlay);

            }
        }

    }
}
