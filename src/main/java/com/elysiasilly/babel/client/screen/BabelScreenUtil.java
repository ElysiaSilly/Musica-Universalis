package com.elysiasilly.babel.client.screen;

import com.elysiasilly.musalis.util.RGBA;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.world.phys.Vec2;
import org.joml.Matrix4f;

public class BabelScreenUtil {

    public static void renderItemWithDecorations() {
        renderItem();
        renderItemDecorations();
    }

    public static void renderItem() {

    }

    public static void renderItemDecorations() {

    }

    public static void renderBlock() {

    }

    public static void fill(WidgetBounds bounds, VertexConsumer consumer, Matrix4f matrix4f, RGBA rgba) {
        fill(consumer, matrix4f, bounds.globalStart, bounds.globalEnd, bounds.depth, rgba);
    }

    public static void fill(VertexConsumer consumer, Matrix4f matrix4f, Vec2 min, Vec2 max, float z, RGBA rgba) {
        consumer.addVertex(matrix4f, min.x, min.y, z).setColor(rgba.red, rgba.green, rgba.blue, rgba.alpha);
        consumer.addVertex(matrix4f, min.x, max.y, z).setColor(rgba.red, rgba.green, rgba.blue, rgba.alpha);
        consumer.addVertex(matrix4f, max.x, max.y, z).setColor(rgba.red, rgba.green, rgba.blue, rgba.alpha);
        consumer.addVertex(matrix4f, max.x, min.y, z).setColor(rgba.red, rgba.green, rgba.blue, rgba.alpha);
    }

    public static void blit() {}
}
