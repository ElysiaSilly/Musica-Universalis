package com.elysiasilly.musalis.common.block.fluid;

import com.elysiasilly.musalis.client.render.MusicaRenderTypes;
import com.elysiasilly.musalis.client.render.MusicaShaders;
import com.elysiasilly.musalis.core.util.MathUtil;
import com.elysiasilly.musalis.core.util.RenderUtil;
import com.mojang.blaze3d.shaders.Uniform;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.joml.Matrix4f;

public class FluidBlockRenderer implements BlockEntityRenderer<FluidBE> {

    //ResourceLocation fluidTexture;

    public FluidBlockRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(FluidBE t, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {

        /*
        System.out.println(

                Minecraft.getInstance().hitResult + " " +

                Minecraft.getInstance().player.getPickResult()
        );

         */

        float u = MathUtil.castToRange(-64, 320, 0, 1, t.getBlockPos().getY());
        float a = MathUtil.castToRange(-64, 320, 1, 0, t.getBlockPos().getY());

        //float u = MathUtil.castToRange(0, 10, 0, 1, 8);

        //System.out.println(u);

        Uniform uniform = MusicaShaders.getTestingShader().getUniform("Test");
        assert uniform != null;
        uniform.set(a, u, 0.0f, 0.0f);

        poseStack.pushPose();

        poseStack.translate(0.5, 0, 0.5);

        float angleX = 0.1f * (Minecraft.getInstance().player.tickCount + partialTick) * 90;

        poseStack.mulPose(Axis.YP.rotationDegrees(angleX));

        //Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getModelManager().getModel().
        VertexConsumer consumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(MusicaRenderTypes.getTestingShader());

        float offset = -0.5f;

        /*
        RenderUtil.renderPlane(consumer, poseStack.last().pose(), packedLight, 0, 0 + offset, 0, 0 + offset, 1 + offset, 0, 1 + offset, 0, 1.2f, 0, false);
        poseStack.mulPose(Axis.YP.rotationDegrees(angleX));

        RenderUtil.renderPlane(consumer, poseStack.last().pose(), packedLight, 0, -0.2f + offset, 0, -0.2f + offset, 1.2f + offset, 0, 1.2f + offset, 0, 1.6f, 0, false);
        poseStack.mulPose(Axis.YP.rotationDegrees(angleX));

        RenderUtil.renderPlane(consumer, poseStack.last().pose(), packedLight, 0, -0.4f + offset, 0, -0.4f + offset, 1.4f + offset, 0, 1.4f + offset, 0, 2f, 0, false);

         */
        poseStack.popPose();

        if(t.getLevel() instanceof BlockAndTintGetter tint) {
            Minecraft.getInstance().getBlockRenderer().renderBatched(Blocks.DEEPSLATE.defaultBlockState(), t.getBlockPos(), tint, poseStack, multiBufferSource.getBuffer(MusicaRenderTypes.getTestingShader()), true, t.getLevel().random);
        }

        if(true) return;

        //Minecraft.getInstance().getBlockRenderer().renderSingleBlock(Blocks.GLASS.defaultBlockState(), poseStack, multiBufferSource, packedLight, packedOverlay, ModelData.EMPTY, RenderType.cutout());

        //BlockPlaceContext context = n

        //Blocks.STONE_BRICK_STAIRS.getStateForPlacement()

        if(t.getTank().getFluid().getFluidType() == Fluids.EMPTY.getFluidType()) return;

        //fluidTexture = ResourceLocation.withDefaultNamespace("textures/block/diamond_block.png");

        //IClientFluidTypeExtensions fluidExtensions = IClientFluidTypeExtensions.of(t.getTank().getFluid().getFluidType());
        //TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(fluidExtensions.getStillTexture());

        //fluidTexture = sprite.contents().name().withSuffix(".png").withPrefix("textures/");
        //fluidTexture = ResourceLocation.read("sprite.contents().name()").getOrThrow();

        //System.out.println(fluidTexture);
        //System.out.println(sprite.contents().name());



        float height = (float) 0.875 / t.getTankSize() * t.getFluidAmount();

        //sprite.

        Matrix4f matrix4f = poseStack.last().pose();
        //VertexConsumer consumer = multiBufferSource.getBuffer(this.renderType());


        //consumer.addVertex(matrix4f, 0, height, 0).setColor(-1, -1, -1, -1).uv(sprite.getU((1 - CORNER_FLUID) * 16);, minV).uv2(packedLight).normal(0, 1, 0).endVertex();


        //RenderUtils.renderPlane(consumer, matrix4f, 15, 255, 0, 0, 0, 1, 0, 16, 0, height, 0, false);

        VertexConsumer con = multiBufferSource.getBuffer(RenderType.translucent());
        IClientFluidTypeExtensions fluidExtensions = IClientFluidTypeExtensions.of(t.getTank().getFluid().getFluidType());
        TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(fluidExtensions.getStillTexture());
        //colors are argb

        //Color color = Color.packedInt(fluidExtensions.getTintColor());

        //if fluid is water, use biome water color
        //if(be.getFluid().getFluid().isSame(Fluids.WATER)) {
        //    color = Color.packedInt(BiomeColors.getAverageWaterColor(t.getLevel(), t.getBlockPos())).setA(color.a());
        //}

        float help = 1;

        float minU = sprite.getU(help * 1);
        float maxU = sprite.getU((1 - help) * 1);
        float minV = sprite.getV(help * 1);
        float maxV = sprite.getV((1 - help) * 1);



        con.addVertex(matrix4f, help, height, help).setColor(-1, -1, -1, -1).setUv(minU, minV).setLight(packedLight).setNormal(0, 1, 0);
        con.addVertex(matrix4f, help, height, 1 - help).setColor(-1, -1, -1, -1).setUv(minU, maxV).setLight(packedLight).setNormal(0, 1, 0);
        con.addVertex(matrix4f, 1 - help, height, 1 - help).setColor(-1, -1, -1, -1).setUv(maxU, maxV).setLight(packedLight).setNormal(0, 1, 0);
        con.addVertex(matrix4f, 1 - help, height, help).setColor(-1, -1, -1, -1).setUv(maxU, minV).setLight(packedLight).setNormal(0, 1, 0);

        matrix4f.scale(0.015f, 0.015f, 0.015f);
        matrix4f.rotate(Axis.ZP.rotationDegrees(180));
        matrix4f.translate(-33f, -75f, 0.5f);

        String text = t.getFluidAmount() + "\n" + t.getPressure();

        float f2 = (float)(-Minecraft.getInstance().font.width(text) / 2);

        Minecraft.getInstance().font.drawInBatch(text, f2, 0, 1, false, matrix4f, multiBufferSource, Font.DisplayMode.NORMAL, 255, packedLight);



    }

    /*
    protected RenderType renderType() {
        return RenderType.entityTranslucentEmissive(fluidTexture);
    }

     */
}
