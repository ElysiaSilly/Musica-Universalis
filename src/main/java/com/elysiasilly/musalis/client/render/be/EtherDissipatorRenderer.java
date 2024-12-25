package com.elysiasilly.musalis.client.render.be;

import com.elysiasilly.musalis.common.block.be.creative.CrEtherDissipatorBE;
import com.elysiasilly.musalis.common.component.EtherCoreComponent;
import com.elysiasilly.musalis.common.world.ether.EtherStack;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.registry.MUComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;

public class EtherDissipatorRenderer implements BlockEntityRenderer<CrEtherDissipatorBE> {

    public EtherDissipatorRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CrEtherDissipatorBE be, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {

        if(be.getCore() != null) {
            if(!be.getCore().isEmpty()) {

                String string = BuiltInRegistries.ITEM.getKey(be.getCore().getItem()).toString();
                string = string.substring(string.indexOf(":") + 1);

                BakedModel model = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(MusicaUniversalis.location("special/" + string)));

                VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.cutout());

                EtherCoreComponent component = be.getCore().get(MUComponents.ETHER_CORE);

                if(component != null) {
                    EtherStack stack = component.getEther();
                    if (stack != null) {
                        if (component.getEther().getAmount() != 0) {
                            float angleX = (0.1f * (Minecraft.getInstance().player.tickCount + v) * component.getEther().getAmount()) * .01f;
                            poseStack.rotateAround(Axis.YN.rotationDegrees(angleX), .5f, 0, .5f);
                        }
                    }
                }

                poseStack.translate(0, (float) 1 / 16 * 15, 0);

                poseStack.pushPose();

                Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), consumer, null, model, 255, 255, 255, 200, i1);

                poseStack.popPose();
            }
        }
    }
}
