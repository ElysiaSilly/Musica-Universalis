package com.elysiasilly.musalis.common.physics.rope;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class Rope {

    public Vec3 origin;
    final Level level;
    public List<RopeSegment> segments = new ArrayList<>();


    public Rope(Level level, Vec3 origin) {
        this.level = level;
        this.origin = origin;
    }

    public void tick() {
        for(RopeSegment segment : segments) {
            segment.tick();
        }

        for(int iterate = 0; iterate < this.segments.size(); iterate++) {

            RopeSegment rope = segments.get(iterate);

            if(iterate == 0) {
                rope.position = origin;
            } else {
                RopeSegment previousSegment = segments.get(iterate - 1);

                rope.position = new Vec3(origin.x, previousSegment.position.y - previousSegment.length, origin.z);
            }
        }
    }

    public void addSegment(float length) {
        segments.add(new RopeSegment(this, length));
    }

    public static class RopeSegment {

        final Rope rope;
        float length;
        Vec3 position, previousPosition;

        public RopeSegment(Rope rope, float length) {
            this.rope = rope;
            this.length = length;

            this.position = rope.origin;

            rope.segments.add(this);
        }

        public void destroySegment() {
            rope.segments.remove(this);
        }

        public Vec3 getPosition() {
            return this.position;
        }

        public void tick() {

        }
    }
}
