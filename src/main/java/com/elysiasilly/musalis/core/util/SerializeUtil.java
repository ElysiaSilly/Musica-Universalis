package com.elysiasilly.musalis.core.util;

import net.minecraft.world.phys.Vec3;

public class SerializeUtil {

    // this is so fucking stupid lmao

    private static final char mark = '%';

    public static String packVec3(Vec3 vec3) {
        return String.format("%s%s%s%s%s", vec3.x, mark, vec3.y, mark, vec3.z);
    }

    public static Vec3 unpackVec3(String string) {
        String[] parts = string.split(String.valueOf(mark));

        return new Vec3(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
    }

}
