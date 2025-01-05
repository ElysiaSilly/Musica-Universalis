package com.elysiasilly.musalis.common.world.astrom;

import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

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
}
