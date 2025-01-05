package com.elysiasilly.musalis.common.world.rimestar;

import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.core.registry.MUAttachments;
import com.elysiasilly.musalis.networking.payloads.RimestarChunkWeightPayload;
import com.elysiasilly.musalis.util.MathUtil;
import com.elysiasilly.musalis.util.RGBA;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Matrix4f;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = Musalis.MODID, bus = EventBusSubscriber.Bus.GAME)
public class RimestarChunkWeight {

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void blockBroken(BlockEvent.BreakEvent event) {
        LevelAccessor level = event.getLevel();
        ChunkAccess chunk = level.getChunk(event.getPos());
        int y = event.getPos().getY();

        add(chunk, y > level.getSeaLevel() ? 0.01f : 0.005f);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void blockPlaced(BlockEvent.EntityPlaceEvent event) {
        LevelAccessor level = event.getLevel();
        ChunkAccess chunk = level.getChunk(event.getPos());
        int y = event.getPos().getY();

        add(chunk, y > level.getSeaLevel() ? 0.1f : 0.05f);
    }

    public static boolean testChunk(ChunkAccess chunk) {
        return !chunk.getBlockEntitiesPos().isEmpty() || chunk.getInhabitedTime() > 1000000;
        // hasStructures, hasBEs, isSpawnChunk,

        //System.out.println("Has Structures? " + chunk.hasAnyStructureReferences());
        //System.out.println("Has BlockEntities? " + !chunk.getBlockEntitiesPos().isEmpty());
        //System.out.println("Inhabit time: " + chunk.getInhabitedTime());
        //System.out.println("Weight: " + chunk.getData(MUAttachments.RIMESTAR_WEIGHT.get()));
        //System.out.println("Is Spawnchunks? " + chunk.getLevel().getChunkSource().getLoadedChunksCount());
    }

    public static float getWeight(ChunkAccess chunk) {
        if(testChunk(chunk)) return 1f;
        float val = testChunkFlatness(chunk) + chunk.getData(MUAttachments.RIMESTAR_WEIGHT);
        return Math.min(val, 1f);
    }


    public static float testChunkFlatness(ChunkAccess chunk) {
        return 0;
    }

    public static float testChunkCaveIntersection(ChunkAccess chunk) {
        return 0;
    }

    public static void add(ChunkAccess chunk, float amount) {
        chunk.setData(MUAttachments.RIMESTAR_WEIGHT.get(), chunk.getData(MUAttachments.RIMESTAR_WEIGHT.get()) + amount);
    }

    public static Map<BlockPos, Float> weights = new Hashtable<>();

    @SubscribeEvent
    public static void renderDebug(RenderLevelStageEvent event) {

        if(!(event.getStage() == RenderLevelStageEvent.Stage.AFTER_WEATHER)) return;

        for(Map.Entry<BlockPos, Float> weight : weights.entrySet()) {
            PoseStack stack = new PoseStack();

            BlockPos pos = weight.getKey();
            Float val = weight.getValue();

            renderText(stack, pos.atY(200).getCenter(), String.valueOf(val), new RGBA(val, MathUtil.numbers.castToRange(0, 1, 1, 0, val), 0f));
        }
    }

    public static void renderText(PoseStack stack, Vec3 pos, String string, RGBA colour) {
        DebugRenderer.renderFloatingText(stack, Minecraft.getInstance().renderBuffers().bufferSource(), string, pos.x, pos.y, pos.z, colour.dec(), 1);

        // above method for some reason doesnt work as expected when i dont also call drawinbatch
        // i dont know either man
        stack.scale(.0001f, .0001f, .0001f);
        Matrix4f matrix4f = stack.last().pose();
        Minecraft.getInstance().font.drawInBatch(string, (float) -Minecraft.getInstance().font.width(string) / 2, 1, RGBA.colours.WHITE.dec(), true, matrix4f, Minecraft.getInstance().renderBuffers().bufferSource(), Font.DisplayMode.POLYGON_OFFSET, 0, 200);
    }

    @SubscribeEvent
    public static void sync(ChunkWatchEvent.Sent event) {
        //PacketDistributor.sendToPlayersTrackingChunk(event.getLevel(), event.getPos(), new RimestarChunkWeightPayload(getWeight(event.getChunk())));
    }

    @SubscribeEvent
    public static void tick(LevelTickEvent.Post event) {

        if(event.getLevel().isClientSide) return;

        List<? extends Player> players = event.getLevel().players();

        if(players.isEmpty()) return;

        ChunkAccess chunk = event.getLevel().getChunk(players.getFirst().getOnPos());

        float weight = getWeight(chunk);
        if(event.getLevel() instanceof ServerLevel serverLevel) PacketDistributor.sendToAllPlayers(new RimestarChunkWeightPayload(chunk.getPos().getMiddleBlockX(),chunk.getPos().getMiddleBlockZ() ,weight));

    }

}
