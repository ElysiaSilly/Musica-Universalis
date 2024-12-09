package com.elysiasilly.musalis.common.world.physics.rope;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
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

        //for(int iterate = 0; iterate < this.segments.size();) {

            //RopeSegment rope = segments.get(iterate);
            RopeSegment previousSegment = null;

            for (int i = 0; i < this.segments.size(); i++) {
                RopeSegment segment = this.segments.get(i);

                if(segment.anchored) return;

                if (i == 0) {
                    Vec3 pos1 = origin;
                    Vec3 pos2 = segment.position;

                    Vec3 center = pos1.add(pos2).scale(0.5);
                    Vec3 direction = pos1.subtract(pos2).normalize();

                    segment.position = center.subtract(direction.scale(segment.length * 0.5));
                } else {
                    Vec3 pos1 = previousSegment.position;
                    Vec3 pos2 = segment.position;

                    Vec3 center = pos1.add(pos2).scale(0.5);
                    Vec3 direction = pos1.subtract(pos2).normalize();

                    previousSegment.position = center.add(direction.scale(segment.length * 0.5));
                    segment.position = center.subtract(direction.scale(segment.length * 0.5));
                }

                previousSegment = segment;
            //}

            //iterate++;
        }
    }

    public void addSegment(float length) {
        new RopeSegment(this, length);
    }

    public void addAnchoredSegment(float length, boolean anchored, Vec3 pos) {
        new RopeSegment(this, length, anchored, pos);
    }

    public void addSegments(float length, int amount, boolean anchored) {
        for(int iterate = 0; iterate <= amount; iterate++) {
            new RopeSegment(this, length);
        }
    }

    public List<Vec3> getSegmentPositions() {
        List<Vec3> list = new ArrayList<>();

        for(RopeSegment segment : segments) {
            list.add(segment.position);
        }

        return list;
    }


    public static class RopeSegment {

        final Rope rope;
        boolean anchored;
        float length;
        Vec3 position, previousPosition;

        public RopeSegment(Rope rope, float length) {
            this.rope = rope;
            this.length = length;
            this.anchored = false;

            this.position = rope.origin;
            this.previousPosition = this.position;

            rope.segments.add(this);
        }

        public RopeSegment(Rope rope, float length, boolean anchored, Vec3 position) {
            this.rope = rope;
            this.length = length;
            this.anchored = anchored;

            this.position = position;

            rope.segments.add(this);
        }

        public void destroySegment() {
            rope.segments.remove(this);
        }

        public Vec3 getPosition() {
            return this.position;
        }

        public void tick() {
            if(anchored) return;
            Vec3 velocity = this.position.subtract(this.previousPosition).add(0, -0.08, 0).scale(0.99);
            this.previousPosition = this.position;
            this.position = this.position.add(velocity);
            collide();
        }

        private void collide() {
            Level level = rope.level;

            //Entity thing = null;

            for(Entity entity : level.getEntities(null, new AABB(position, position).inflate(.5))) {

                position = entity.position();
                //rope.origin = entity.position();

                //level.addParticle(ParticleTypes.CHERRY_LEAVES, pos.x, pos.y, pos.z, 1, 1, 1);

                //ParticleUtils.spawnParticleInBlock(level, new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), 10, ParticleTypes.CHERRY_LEAVES);

                //level.setBlockAndUpdate(new BlockPos((int) Math.floor(position.x), (int) Math.floor(position.y), (int) Math.floor(position.z)), Blocks.YELLOW_CONCRETE.defaultBlockState());
                //ParticleUtils.spawnParticleInBlock(level, new BlockPos(pos.x, pos.y, pos.z), );
                //entity.discard();


                //System.out.println(entity.position());

            }

            //if(thing != null) {
            //    rope.origin = thing.position();
            //}
        }
    }
}
