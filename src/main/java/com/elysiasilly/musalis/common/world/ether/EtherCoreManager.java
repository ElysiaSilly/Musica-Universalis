package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.util.MathUtil;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.List;

@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.GAME)
public class EtherCoreManager extends InteractableManager<EtherCoreObject> {

    public static EtherCoreManager get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(new Factory<>(EtherCoreManager::create, EtherCoreManager::load), "ether");
    }

    public static EtherCoreManager create() {
        return new EtherCoreManager();
    }

    public static EtherCoreManager load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        EtherCoreManager data = create();

        int size = compoundTag.getInt("size");

        for(int i = 0; i <= size; i++) {
            data.interactables.add(EtherCoreObject.codec.CODEC.parse(NbtOps.INSTANCE, compoundTag.get(String.valueOf(i)) ).getOrThrow());
        }

        return data;
    }

    public void addCore(Level level, Vec3 pos) {
        this.interactables.add(new EtherCoreObject(level, Vec3.ZERO, pos));
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putInt("size", this.interactables.size());

        int index = 1;
        for(EtherCoreObject core : this.interactables) {
            compoundTag.put(String.valueOf(index), EtherCoreObject.codec.CODEC.encodeStart(NbtOps.INSTANCE, core).getOrThrow());
            index++;
        }

        return compoundTag;
    }

    @SubscribeEvent
    public static void tick(LevelTickEvent.Pre event) {
        if(event.getLevel() instanceof ServerLevel level) {
            EtherCoreManager manager = EtherCoreManager.get(level);
            manager.tick(event.getLevel());
        }
    }


}
