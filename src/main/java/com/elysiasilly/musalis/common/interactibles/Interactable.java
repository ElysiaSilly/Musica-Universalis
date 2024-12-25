package com.elysiasilly.musalis.common.interactibles;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public abstract class Interactable<MANAGER extends InteractableManager> {

    MANAGER manager;

    public abstract void render();

    public abstract void tick(Level level);

    public abstract void serialize(CompoundTag compoundTag, HolderLookup.Provider provider);

    public abstract void deserialize(CompoundTag compoundTag, HolderLookup.Provider provider);

    public void destroy() {
        manager.destroy(this);
    }
}
