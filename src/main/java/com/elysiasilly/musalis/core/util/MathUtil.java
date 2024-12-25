package com.elysiasilly.musalis.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class MathUtil {

    // why the fuck does mojang use so many different vector implementations

    public static BlockPos vec3ToBlockPos(Vec3 vec3) {
        return new BlockPos((int) Math.floor(vec3.x), (int) Math.floor(vec3.y), (int) Math.floor(vec3.z));
    }

    public static Vec3 blockPosToVec3(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vector3f vec3ToVec3f(Vec3 vec3) {
        return new Vector3f((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public static Vector3i vec3ToVec3i(Vec3 vec3) {
        return new Vector3i((int) vec3.x, (int) vec3.y, (int) vec3.z);
    }

    public static Vec3 vec3iToVec3(Vec3i vec3i) {
        return new Vec3(vec3i.getX(), vec3i.getY(), vec3i.getZ());
    }

    public static Vec3 vec2ToVec3(Vec2 vec2) {
        return new Vec3(vec2.x, vec2.y, 0);
    }

    public static Vec2 vec3ToVec2(Vec3 vec3) {
        return new Vec2((float) vec3.x, (float) vec3.y);
    }

    public static float distance(Vector3f start, Vector3f end) {
        return Vector3f.distance(start.x, start.y, start.z, end.x, end.y, end.z);
    }

    public static float distance(Vec3 start, Vec3 end) {
        return distance(vec3ToVec3f(start), vec3ToVec3f(end));
    }

    public static float castToRange(float oldMin, float oldMax, float newMin, float newMax, float value) {
        return (((value - oldMin) * (newMax - newMin)) / (oldMax - oldMin)) + newMin;
    }

    public static float closest(float number, float...values) {
        float distance = Math.abs(values[0] - number);
        int index = 0;
        for(int c = 1; c < values.length; c++){
            float temp = Math.abs(values[c] - number);
            if(temp < distance){
                index = c;
                distance = temp;
            }
        }
        return values[index];
    }

    public static boolean withinBounds(Vec2 position, Vec2 boundaryStart, Vec2 boundaryEnd) {
        return position.x >= boundaryStart.x && position.y >= boundaryStart.y && position.x <= boundaryEnd.x && position.y <= boundaryEnd.y;
    }

    public static Vec2 getPointOnCircle(int radius, int current, int total) {
        double theta = (Math.PI * 2) / total;
        double angle = theta * current;
        return new Vec2((float) (radius * Math.cos(angle)), (float) (radius * Math.sin(angle)));
    }

    public static float degreeToRadian(float degree) {
        return (float) (degree * Math.PI / 180);
    }

    public static Vec2 rotateAroundPoint(Vec2 centre, Vec2 position, float degrees) {
        degrees = degreeToRadian(degrees);

        double x = centre.x + (position.x - centre.x) * Math.cos(degrees) - (position.y - centre.y) * Math.sin(degrees);
        double y = centre.y + (position.x - centre.x) * Math.sin(degrees) + (position.y - centre.y) * Math.cos(degrees);
        return new Vec2((float) x, (float) y);
    }

    public static class Velocity {

        Vec3 previous;
        Vec3 current;

        public Velocity() {}

        public void update(Vec3 position) {
            if(this.previous == null) this.previous = position;
            if(this.current != null) this.previous = current;
            this.current = position;
        }

        // todo : need to actually do the math for this lol
        public Vec3 velocity() {
            return this.previous.subtract(this.current);
        }
    }
}
