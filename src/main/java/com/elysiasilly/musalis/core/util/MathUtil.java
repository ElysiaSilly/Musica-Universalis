package com.elysiasilly.musalis.core.util;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.joml.Vector3i;

public class MathUtil {

    public static Vector3f vec3ToVec3f(Vec3 vec3) {
        return new Vector3f((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public static Vector3i vec3ToVec3i(Vec3 vec3) {
        return new Vector3i((int) vec3.x, (int) vec3.y, (int) vec3.z);
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

    public static Vec2 getPointOnCircle(int radius, int current, int total) {
        double theta = (Math.PI*2) / total;
        double angle = theta * current;

        return new Vec2((float) (radius * Math.cos(angle)), (float) (radius * Math.sin(angle)));
    }
}
