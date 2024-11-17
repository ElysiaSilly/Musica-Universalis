package com.elysiasilly.musalis.client.render.misc;

import com.elysiasilly.musalis.client.render.MusicaRenderTypes;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.joml.Matrix4f;

import java.util.Iterator;

@SuppressWarnings({"unused"})
@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class GhostBlockRenderer {

    @SubscribeEvent
    public static void onRenderLevelEvent(RenderLevelStageEvent event) {

        if(true) return;

        //Minecraft.getInstance().player.getMainHandItem();

        //assert Minecraft.getInstance().player != null;
        if(!(Minecraft.getInstance().player.getMainHandItem().getItem() instanceof BlockItem item)) return;
        if(!(Minecraft.getInstance().hitResult instanceof BlockHitResult hitResult)) return;
        if(Minecraft.getInstance().hitResult.getType() != HitResult.Type.BLOCK) return;

        if(!(event.getStage() == RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS)) return;

        //RenderSystem.activeTexture(10);

        BlockPos blockPos = hitResult.getBlockPos().relative(hitResult.getDirection());
        //print("bruh");

        PoseStack stack = event.getPoseStack();

        Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();


        stack.translate(blockPos.getX() - cam.getPosition().x, blockPos.getY() - cam.getPosition().y, blockPos.getZ() - cam.getPosition().z);
        stack.pushPose();

        BlockHitResult result = new BlockHitResult(Minecraft.getInstance().hitResult.getLocation(), hitResult.getDirection(), blockPos, false);

        BlockPlaceContext context = new BlockPlaceContext(Minecraft.getInstance().player, Minecraft.getInstance().player.getUsedItemHand(), Minecraft.getInstance().player.getMainHandItem(), result);

        BlockState block = item.getBlock().getStateForPlacement(context);

        BlockModelShaper modelShaper = new BlockModelShaper(Minecraft.getInstance().getModelManager());

        BakedModel model = modelShaper.getBlockModel(block);

        //System.out.println(MusicaRenderTypes.getTestingShader().outline());

        Iterator<RenderType> renderTypeIterator = model.getRenderTypes(block, RandomSource.create(42L), ModelData.EMPTY).iterator();

        while(renderTypeIterator.hasNext()) {
            RenderType rt = renderTypeIterator.next();
        if(Minecraft.getInstance().level instanceof BlockAndTintGetter tint) {

            Minecraft.getInstance().getBlockRenderer().renderBatched(
                    block,
                    blockPos,
                    tint,
                    event.getPoseStack(),
                    Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(MusicaRenderTypes.getTestingShader()),
                    //Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(rt),
                    true,
                    Minecraft.getInstance().level.getRandom()
            );
        }}




        //Minecraft.getInstance().getBlockRenderer().renderSingleBlock(block, event.getPoseStack(), Minecraft.getInstance().renderBuffers().bufferSource(), 10, 10, ModelData.EMPTY, MusicaRenderTypes.getTestingShader());

        Matrix4f matrix4f = stack.last().pose();
        //VertexConsumer consumer = Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(MusicaRenderTypes.getTestingShader());

        //int packedLight = 10;

        //RenderUtil.renderPlane(consumer, matrix4f, packedLight, 0, 0, 0, 0, 1, 0, 1, 0, 2, 0, false);
    }
}
