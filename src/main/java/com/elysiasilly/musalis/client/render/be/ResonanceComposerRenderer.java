package com.elysiasilly.musalis.client.render.be;

import com.elysiasilly.musalis.common.block.be.CrResonanceComposerBE;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemDisplayContext;

public class ResonanceComposerRenderer implements BlockEntityRenderer<CrResonanceComposerBE> {

    public ResonanceComposerRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CrResonanceComposerBE be, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {

        if(be.getDisk() != null) {
            if(!be.getDisk().isEmpty()) {

                poseStack.translate((float) 1 / 16 * 6, (float) 1 / 16 * 7, 0);

                String string = BuiltInRegistries.ITEM.getKey(be.getDisk().getItem()).toString();
                string = string.substring(string.indexOf(":") + 1);

                BakedModel model = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(MusicaUniversalis.location("special/" + string)));

                VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.cutout());

                Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), consumer, null, model.applyTransform(ItemDisplayContext.GROUND, poseStack, false), 255, 255, 255, 200, packedOverlay);
            }
        }
    }
}
