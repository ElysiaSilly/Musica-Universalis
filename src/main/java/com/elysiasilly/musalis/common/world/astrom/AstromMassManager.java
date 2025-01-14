package com.elysiasilly.musalis.common.world.astrom;

import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.util.MCUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class AstromMassManager extends InteractableManager<AstromMassObject> {
    public AstromMassManager() {
        super(Tick.PRE, "astrom_masses");
    }

    @Override
    public Codec<AstromMassObject> getCodec() {
        return null;
    }

    @Override
    public AstromMassManager load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        return new AstromMassManager();
    }

    @Override
    public AstromMassManager create() {
        return new AstromMassManager();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        return tag;
    }

    public AstromMassObject get(BlockPos get) {
        AstromMassObject mass = null;

        for(AstromMassObject obj : this.interactables) {
            if(obj.blocks.contains(get)) {
                mass = obj; break;
            }
        }

        return mass;
    }

    public void remove(BlockPos remove) {
        AstromMassObject mass = null;

        for(AstromMassObject obj : this.interactables) {
            if(obj.blocks.contains(remove)) {
                mass = obj; break;
            }
        }

        if(mass != null) {
            mass.blocks.remove(remove);
        }
    }

    public void add(BlockPos add) {
        List<AstromMassObject> connections = new ArrayList<>();

        for(AstromMassObject obj : this.interactables) {
            for(BlockPos pos : obj.blocks) {
                if(MCUtil.blockPos.isNeighbour(add, pos)) {
                    connections.add(obj);
                    break;
                }
            }
        }

        if(connections.isEmpty()) {
            Musalis.LOGGER.info("new mass");
            AstromMassObject mass = new AstromMassObject(new AstromMass());
            mass.blocks.add(add);
            add(mass);
        }
        if(connections.size() == 1) {
            Musalis.LOGGER.info("added to mass");
            connections.getFirst().blocks.add(add);
        }
        if(connections.size() > 1) {
            Musalis.LOGGER.info("merged mass");
            AstromMassObject mass = new AstromMassObject(new AstromMass());
            mass.merge(connections);
            mass.blocks.add(add);
            add(mass);
        }
    }
}
