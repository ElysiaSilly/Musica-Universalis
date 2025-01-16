package com.elysiasilly.musalis.common.world.astrom;

import com.elysiasilly.musalis.common.interactibles.Interactable;
import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.util.Conversions;
import com.elysiasilly.musalis.util.MCUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.ArrayList;
import java.util.List;

public class AstromMassObject extends Interactable<AstromMassManager> {

    public final AstromMass mass;
    public final List<BlockPos> blocks = new ArrayList<>();

    public Float colour;

    static class codec {
        public static final Codec<AstromMassObject> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                AstromMass.codec.CODEC.fieldOf("mass").forGetter(i -> i.mass),
                BlockPos.CODEC.listOf().fieldOf("blocks").forGetter(i -> i.blocks)
        ).apply(instance, AstromMassObject::new));
    }

    private AstromMassObject(AstromMass mass, List<BlockPos> blocks) {
        this.mass = mass;
        this.blocks.addAll(blocks);
    }

    public AstromMassObject(AstromMass mass) {
        this.mass = mass;
    }

    public void merge(List<AstromMassObject> masses) {
        for(AstromMassObject mass : masses) {
            this.blocks.addAll(mass.blocks);
            mass.destroy();
        }
    }

    public void merge(AstromMassObject...masses) {
        for(AstromMassObject mass : masses) {
            this.blocks.addAll(mass.blocks);
            mass.destroy();
        }
    }


    @Override
    public void render(RenderLevelStageEvent event) {

    }

    @Override
    public void tick(Level level) {
        if(colour == null) this.colour = level.random.nextFloat();
        if(blocks.isEmpty()) {
            Musalis.LOGGER.info("destroyed mass");
            destroy();
        }

        for(BlockPos pos : this.blocks) {
            MCUtil.particle.add(level, ParticleTypes.NOTE, Conversions.vector.vec3(pos.above()), new Vec3(this.colour, 0, 0));
        }
    }
}
