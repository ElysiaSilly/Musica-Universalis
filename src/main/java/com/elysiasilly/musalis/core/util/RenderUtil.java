package com.elysiasilly.musalis.core.util;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class RenderUtil {

    public static void renderCube(
            VertexConsumer consumer, Matrix4f matrix4f,
            float s, int light, boolean centred, boolean cull) {

        /*
        if(cull & (pos == null || level == null)) return;

        float b = 0;

        float halfIt = s / 2;
        s = centred ? s - halfIt : s;
        b = centred ? b - halfIt : b;

        if(cull ? !level.getBlockState(pos.north()).isFaceSturdy(level, pos.above(), Direction.SOUTH) : true)
            renderPlaneFull(consumer, matrix4f, b, s, s, b, b, b, b, b, light);

        if(cull ? !level.getBlockState(pos.west()).isFaceSturdy(level, pos.above(), Direction.EAST) : true)
            renderPlaneFull(consumer, matrix4f, b, b, b, s, b, s, s, b, light);

        if(cull ? !level.getBlockState(pos.south()).isFaceSturdy(level, pos.above(), Direction.NORTH) : true)
            renderPlaneFull(consumer, matrix4f, b, s, b, s, s, s, s, s, light);

        if(cull ? !level.getBlockState(pos.east()).isFaceSturdy(level, pos.above(), Direction.WEST) : true)
            renderPlaneFull(consumer, matrix4f, s, s, s, b, b, s, s, b, light);

        if(cull ? !level.getBlockState(pos.below()).isFaceSturdy(level, pos.above(), Direction.UP) : true)
            renderPlaneFull(consumer, matrix4f, b, s, b, b, b, b, s, s, light);

        if(cull ? !level.getBlockState(pos.above()).isFaceSturdy(level, pos.above(), Direction.DOWN) : true)
            renderPlaneFull(consumer, matrix4f, b, s, s, s, s, s, b, b, light);

         */
    }

    public static void renderPlane(
            VertexConsumer consumer, Matrix4f matrix4f, int packedLight, int translucency,
            float startX, float startY, float startZ,
            float endX, float endY, float endZ,
            float offsetX, float offsetY, float offsetZ,
            boolean centred
    ) {

        startX = centred ? startX / 2 : startX;
        startY = centred ? startY / 2 : startY;
        startZ = centred ? startZ / 2 : startZ;

        endX = centred ? endX / 2 : endX;
        endY = centred ? endY / 2 : endY;
        endZ = centred ? endZ / 2 : endZ;

        // TODO : STUPID ^

        startX += offsetX;
        startY += offsetY;
        startZ += offsetZ;

        endX += offsetX;
        endY += offsetY;
        endZ += offsetZ;

        renderPlaneFull(consumer, matrix4f, packedLight, translucency, startX, startY, startZ, endX, endY, endZ);
    }

    public static void renderPlaneWithTextureDimensions(
            VertexConsumer consumer, Matrix4f matrix4f, int packedLight, int translucency, ResourceLocation resourceLocation,
            float offsetX, float offsetY, float offsetZ,
            boolean centred
    ) {

        NativeImage image;

        //try { image = ImageUtils.returnNativeImage(resourceLocation);
        //} catch (IOException e) { throw new RuntimeException(e);}

        int height = 0; //ImageUtils.getHeight(image); // TODO : fix
        int width = 0; //ImageUtils.getWidth(image);

        renderPlane(consumer, matrix4f, packedLight, translucency, 0, 0, 0, (float) width / 16, 0, (float) height / 16, offsetX, offsetY, offsetZ, centred);
    }


    private static void renderPlaneFull(
            VertexConsumer consumer, Matrix4f matrix4f, int packedLight, int translucency,
            float startX, float startY, float startZ,
            float endX, float endY, float endZ
    ) {
        float normal = 10;

        consumer.addVertex(matrix4f, endX,   endY,   startZ)
                .setUv(1, 0)
                .setLight(packedLight)
                .setColor(-1, -1, -1, translucency)
                .setUv1(0, 0)
                .setUv2(1, 1)
                .setNormal(0, 1, 0);

        consumer.addVertex(matrix4f, startX, startY, startZ)
                .setUv(0, 0)
                .setLight(packedLight)
                .setColor(-1, -1, -1, translucency)
                .setUv1(0, 0)
                .setUv2(1, 1)
                .setNormal(0, 1, 0);

        consumer.addVertex(matrix4f, startX, startY, endZ)
                .setUv(0, 1)
                .setLight(packedLight)
                .setColor(-1, -1, -1, translucency)
                .setUv1(0, 0)
                .setUv2(1, 1)
                .setNormal(0, 1, 0);

        consumer.addVertex(matrix4f, endX,   endY,   endZ)
                .setUv(1, 1)
                .setLight(packedLight)
                .setColor(-1, -1, -1, translucency)
                .setUv1(0, 0)
                .setUv2(0, 0)
                .setNormal(0, 1, 0);
    }
}
