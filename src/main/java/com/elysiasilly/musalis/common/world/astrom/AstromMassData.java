package com.elysiasilly.musalis.common.world.astrom;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

public class AstromMassData extends SavedData {

    private int number = 0;

    public AstromMassData create() {
        return new AstromMassData();
    }

    public AstromMassData load(CompoundTag tag, HolderLookup.Provider provider) {
        this.number = tag.getInt("number"); // ?
        return this.create();
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("number", this.number);
        return tag;
    }

    public void update(int number) {
        this.number = number;
        this.setDirty();
    }

    public AstromMassData bruh(MinecraftServer server) {
        return null;// server.overworld().getDataStorage().computeIfAbsent(new Factory<>(AstromMassData::create, AstromMassData::load), "astrom_masses");
    }





    public void testinmg() {
        AstromMassData data = new AstromMassData();

        //data.
    }

}
