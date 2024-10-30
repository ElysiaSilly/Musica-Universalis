package com.elysiasilly.musalis.core.util;

import com.elysiasilly.musalis.core.util.type.RGBA;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class RenderUtil {
    /*
    public static void renderCube(
            VertexConsumer consumer, Matrix4f matrix4f,
            float s, int light, boolean centred, boolean cull) {


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


    }
    */

    public static void renderLine(
            MultiBufferSource multiBufferSource, PoseStack.Pose stack, RGBA rgba,
            Vec3 start, Vec3 end
    ) {
        // scary
        //Vector3f normal = new Vector3f((Vector3fc) start).sub(new Vector3f((Vector3fc) end).normalize());

        VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.lines());

        Vector3f i = new Vector3f((float) start.x, (float) start.y, (float) start.z);

        Vector3f f = new Vector3f((float) end.x, (float) end.y, (float) end.z);

        Vector3f normal = i.sub(f).normalize();

        consumer.addVertex(stack, (float) start.x, (float) start.y, (float) start.z).setColor(rgba.red(), rgba.green(), rgba.blue(), rgba.alpha()).setNormal(stack, normal.x, normal.y, normal.z);
        consumer.addVertex(stack, (float) end.x, (float) end.y, (float) end.z).setColor(rgba.red(), rgba.green(), rgba.blue(), rgba.alpha()).setNormal(stack, normal.x, normal.y, normal.z);
    }

    public static void renderPlane(
            VertexConsumer consumer, Matrix4f matrix4f, int packedLight, int translucency,
            Vec3 start, Vec3 end, Vec3 offset,
            boolean centred
    ) {

        //startX = centred ? startX / 2 : startX;
        //startY = centred ? startY / 2 : startY;
        //startZ = centred ? startZ / 2 : startZ;

        //endX = centred ? endX / 2 : endX;
        //endY = centred ? endY / 2 : endY;
        //endZ = centred ? endZ / 2 : endZ;

        // TODO : STUPID ^

        start.add(offset);
        end.add(offset);

        renderPlane(consumer, matrix4f, packedLight, translucency, start, end);
    }

    /*
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

     */

    private static void renderPlane(
            VertexConsumer consumer, Matrix4f matrix4f, int packedLight, int translucency,
            Vec3 start, Vec3 end
    ) {

        consumer.addVertex(matrix4f, (float) end.z, (float) end.y, (float) start.z)
                .setUv(1, 0)
                .setLight(packedLight)
                .setColor(-1, -1, -1, translucency)
                .setUv1(0, 0)
                .setUv2(1, 1)
                .setNormal(0, 1, 0);

        consumer.addVertex(matrix4f, (float) start.x, (float) start.y, (float) start.z)
                .setUv(0, 0)
                .setLight(packedLight)
                .setColor(-1, -1, -1, translucency)
                .setUv1(0, 0)
                .setUv2(1, 1)
                .setNormal(0, 1, 0);

        consumer.addVertex(matrix4f, (float) start.x, (float) start.y, (float) end.z)
                .setUv(0, 1)
                .setLight(packedLight)
                .setColor(-1, -1, -1, translucency)
                .setUv1(0, 0)
                .setUv2(1, 1)
                .setNormal(0, 1, 0);

        consumer.addVertex(matrix4f, (float) end.x,   (float) end.y,   (float) end.z)
                .setUv(1, 1)
                .setLight(packedLight)
                .setColor(-1, -1, -1, translucency)
                .setUv1(0, 0)
                .setUv2(0, 0)
                .setNormal(0, 1, 0);
    }
}
