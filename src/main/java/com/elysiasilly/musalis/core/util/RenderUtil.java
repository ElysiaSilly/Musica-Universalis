package com.elysiasilly.musalis.core.util;

import com.elysiasilly.musalis.core.util.type.RGBA;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class RenderUtil {


    public static void renderCube(
            VertexConsumer consumer, Matrix4f matrix4f,
            float size, int packedLight, boolean centred, boolean cull, RGBA rgba
    ) {

        // up
        renderPlane(consumer, matrix4f, packedLight, rgba, new Vec3(0, size, 0), new Vec3(size, size, size), new Vec3(size, size, 0), new Vec3(0, size, size));

        // down
        renderPlane(consumer, matrix4f, packedLight, rgba, new Vec3(0, 0, size), new Vec3(size, 0, 0), new Vec3(size, 0, size), new Vec3(0, 0, 0));

        // north
        renderPlane(consumer, matrix4f, packedLight, rgba, new Vec3(0, 0, 0), new Vec3(size, size, 0), new Vec3(size, 0, 0), new Vec3(0, size, 0));

        // south
        renderPlane(consumer, matrix4f, packedLight, rgba, new Vec3(0, size, size), new Vec3(size, 0, size), new Vec3(size, size, size), new Vec3(0, 0, size));

        // east
        renderPlane(consumer, matrix4f, packedLight, rgba, new Vec3(size, 0, 0), new Vec3(size, size, size), new Vec3(size, 0, size), new Vec3(size, size, 0));

        // west
        renderPlane(consumer, matrix4f, packedLight, rgba, new Vec3(0, size, 0), new Vec3(0, 0, size), new Vec3(0, size, size), new Vec3(0, 0, 0));
    }

    public static void renderLine(
            MultiBufferSource multiBufferSource, PoseStack.Pose stack, RGBA rgba,
            Vec3 start, Vec3 end
    ) {
        VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.debugLineStrip(50));

        Vector3f i = new Vector3f((float) start.x, (float) start.y, (float) start.z);

        Vector3f f = new Vector3f((float) end.x, (float) end.y, (float) end.z);

        Vector3f normal = i.sub(f).normalize();

        consumer.addVertex(stack, (float) start.x, (float) start.y, (float) start.z).setColor(rgba.red(), rgba.green(), rgba.blue(), rgba.alpha()).setNormal(stack, normal.x, normal.y, normal.z);
        consumer.addVertex(stack, (float) end.x, (float) end.y, (float) end.z).setColor(rgba.red(), rgba.green(), rgba.blue(), rgba.alpha()).setNormal(stack, normal.x, normal.y, normal.z);
    }

    public static void renderPlane(
            VertexConsumer consumer, Matrix4f matrix4f, int packedLight, RGBA rgba,
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

        renderPlane(consumer, matrix4f, packedLight, rgba, start, end, new Vec3(end.x, end.y, start.z), new Vec3(start.x, start.y, end.z));
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
            VertexConsumer consumer, Matrix4f matrix4f, int packedLight, RGBA rgba,
            Vec3 a, Vec3 b, Vec3 c, Vec3 d
    ) {

        consumer.addVertex(matrix4f, (float) c.x, (float) c.y, (float) c.z)
                .setUv(1, 0)
                .setLight(packedLight)
                .setColor(rgba.red(), rgba.green(), rgba.blue(), rgba.alpha())
                .setUv1(0, 0)
                .setUv2(1, 1)
                .setNormal(0, 1, 0);

        consumer.addVertex(matrix4f, (float) a.x, (float) a.y, (float) a.z)
                .setUv(0, 0)
                .setLight(packedLight)
                .setColor(rgba.red(), rgba.green(), rgba.blue(), rgba.alpha())
                .setUv1(0, 0)
                .setUv2(1, 1)
                .setNormal(0, 1, 0);

        consumer.addVertex(matrix4f, (float) d.x, (float) d.y, (float) d.z)
                .setUv(0, 1)
                .setLight(packedLight)
                .setColor(rgba.red(), rgba.green(), rgba.blue(), rgba.alpha())
                .setUv1(0, 0)
                .setUv2(1, 1)
                .setNormal(0, 1, 0);

        consumer.addVertex(matrix4f, (float) b.x,   (float) b.y,   (float) b.z)
                .setUv(1, 1)
                .setLight(packedLight)
                .setColor(rgba.red(), rgba.green(), rgba.blue(), rgba.alpha())
                .setUv1(0, 0)
                .setUv2(0, 0)
                .setNormal(0, 1, 0);
    }
}
