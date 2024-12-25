package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.common.interactibles.Interactable;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.core.MURegistries;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class EtherCoreObject extends Interactable<EtherCoreManager> {

    //private final Level level;
    private final EtherCore core;

    private Vec3 rotation = Vec3.ZERO;
    private Vec3 position = Vec3.ZERO;

    public static class codec{
        public static final Codec<EtherCoreObject> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                MURegistries.ETHER_CORE.byNameCodec().fieldOf("core").forGetter(i -> i.core),
                Vec3.CODEC.fieldOf("rotation").forGetter(i -> i.rotation),
                Vec3.CODEC.fieldOf("position").forGetter(i -> i.position)
        ).apply(instance, EtherCoreObject::new));
    }

    public EtherCoreObject(EtherCore core, Vec3 rotation, Vec3 position) {
        this.core = core;
        this.rotation = rotation;
        this.position = position;
    }

    public EtherCoreObject(Level level, Vec3 rotation, Vec3 position) {
        this.core = level.registryAccess().registry(MUResourceKeys.registries.ETHER_CORE).flatMap(registry -> registry.getRandom(level.random)).get().value();
        this.rotation = rotation;
        this.position = position;
    }


    public EtherCoreObject(Level level, EtherCore core) {
        this.core = core;
        //this.level = level;
    }

    public EtherCoreObject(Level level) {
        this.core = level.registryAccess().registry(MUResourceKeys.registries.ETHER_CORE).flatMap(registry -> registry.getRandom(level.random)).get().value();
        //this.level = level;
    }

    public EtherCore getCore() {
        return this.core;
    }

    public Vec3 getRotation() {
        return this.rotation;
    }

    public void setRotation(Vec3 rotation) {
        this.rotation = rotation;
    }

    public Vec3 getPosition() {
        return this.position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    @OnlyIn(Dist.CLIENT)
    public void render() {

    }

    @Override
    public void tick(Level level) {
        if(level instanceof ServerLevel serverLevel) serverLevel.sendParticles(ParticleTypes.FALLING_LAVA, this.position.x, this.position.y, this.position.z, 1, 0, 0, 0, 0);

        for(VoxelShape shape : level.getBlockCollisions(null, new AABB(position, position).inflate(.5))) {
        }

        Iterable<VoxelShape> sh = level.getBlockCollisions(null, new AABB(position, position).inflate(.1));

        if(!sh.iterator().hasNext()) setPosition(this.position.subtract(0, .5, 0));

        //setPosition(this.position.subtract(0, 1, 0));
    }

    @Override
    public void serialize(CompoundTag compoundTag, HolderLookup.Provider provider) {

    }

    @Override
    public void deserialize(CompoundTag compoundTag, HolderLookup.Provider provider) {

    }
}
