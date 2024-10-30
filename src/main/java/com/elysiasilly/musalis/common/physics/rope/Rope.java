package com.elysiasilly.musalis.common.physics.rope;

import com.elysiasilly.musalis.core.util.SerializeUtil;
import net.minecraft.nbt.CompoundTag;
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

        for(int iterate = 0; iterate < this.segments.size();) {

            RopeSegment rope = segments.get(iterate);

            if(iterate == 0) {
                rope.position = origin;
            } else {
                RopeSegment previousSegment = segments.get(iterate - 1);

                rope.position = new Vec3(origin.x, previousSegment.position.y - previousSegment.length, origin.z);
            }

            iterate++;
        }
    }

    public void addSegment(float length) {
        new RopeSegment(this, length);
    }

    public List<Vec3> getSegmentPositions() {
        List<Vec3> list = new ArrayList<>();

        for(RopeSegment segment : segments) {
            list.add(segment.position);
        }

        return list;
    }

            /*
    public static CompoundTag serialize(Rope rope) {

        CompoundTag tag = new CompoundTag();

        tag.putString("origin", SerializeUtil.packVec3(rope.origin));
        tag.putInt("segment_amount", rope.segments.size());

        if(!rope.segments.isEmpty()) {
            for(int iterate = 0; iterate <= rope.segments.size(); iterate++) {
                RopeSegment segment = rope.segments.get(iterate);

                tag.putString(String.format("segment_position_%s", iterate), SerializeUtil.packVec3(segment.position));
                tag.putString(String.format("segment_previous_position_%s", iterate), SerializeUtil.packVec3(segment.previousPosition));
                tag.putFloat("segment_amount", segment.length);
            }
        }

        return tag;
    }
             */

    public static Rope deserialize(CompoundTag tag) {

        return null;
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
