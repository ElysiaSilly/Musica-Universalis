package com.elysiasilly.musalis.client.render.be;

import com.elysiasilly.musalis.client.render.MURenderTypes;
import com.elysiasilly.musalis.client.render.MUShaders;
import com.elysiasilly.musalis.common.block.be.CrResonanceComposerBE;
import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.registry.MUComponents;
import com.elysiasilly.musalis.core.util.RGBA;
import com.elysiasilly.musalis.core.util.RenderUtil;
import com.mojang.blaze3d.shaders.Uniform;
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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import org.joml.Matrix4f;

public class ResonanceComposerRenderer implements BlockEntityRenderer<CrResonanceComposerBE> {

    public ResonanceComposerRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CrResonanceComposerBE be, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {

        if(be.getDisk() != null) {
            if(!be.getDisk().isEmpty()) {

                poseStack.pushPose();

                poseStack.translate((float) 1 / 16 * 6, (float) 1 / 16 * 7, 0);

                String string = BuiltInRegistries.ITEM.getKey(be.getDisk().getItem()).toString();
                string = string.substring(string.indexOf(":") + 1);

                BakedModel model = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(MusicaUniversalis.location("special/" + string)));

                VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.cutout());

                Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), consumer, null, model.applyTransform(ItemDisplayContext.GROUND, poseStack, false), 1f, 1f, 1f, 200, packedOverlay);

                poseStack.popPose();
            }
        }

        ItemStack disk = be.getDisk();

        if(disk != null) {
            if(!disk.isEmpty()) {
                DataDiskComponent component = disk.get(MUComponents.DATA_DISK);
                if(component != null) {
                    Leitmotif leitmotif = component.getLeitmotif();
                    if(leitmotif != null) {

                        Uniform uniform = MUShaders.getResonanceVisualiser().getUniform("seed");
                        if(uniform != null) uniform.set(leitmotif.hashCode());

                        VertexConsumer consumer = multiBufferSource.getBuffer(MURenderTypes.getTestingShader());

                        poseStack.translate(.5, 1.5, -1);

                        Matrix4f matrix4f = poseStack.last().pose();

                        RenderUtil.drawPlane(consumer, matrix4f, 200, new RGBA(1f), new Vec3(0, 0, 0), new Vec3(0, 3, 3), new Vec3(0, 0, 0), false);
                    }
                }
            }
        }



        /*
        poseStack.rotateAround(Axis.YP.rotationDegrees(90), 0, 0, 1.5f);
        matrix4f = poseStack.last().pose();
        RenderUtil.drawPlane(consumer, matrix4f, 200, new RGBA(1f), new Vec3(0, 0, 0), new Vec3(0, 3, 3), new Vec3(0, 0, 0), false);
         */
    }

    @Override
    public AABB getRenderBoundingBox(CrResonanceComposerBE blockEntity) {
        return AABB.INFINITE;
    }

    @Override
    public boolean shouldRenderOffScreen(CrResonanceComposerBE blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(CrResonanceComposerBE blockEntity, Vec3 cameraPos) {
        return true;
    }
}
