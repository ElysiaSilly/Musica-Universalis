package com.elysiasilly.musalis.common.world.rimestar;

import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;

public class RimestarManager extends InteractableManager<RimestarObject> {

    public RimestarManager() {
        super(Tick.PRE, "rimestars");
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putInt("size", this.interactables.size());

        int index = 1;
        for(RimestarObject rimestar : this.interactables) {
            compoundTag.put(String.valueOf(index), RimestarObject.codec.CODEC.encodeStart(NbtOps.INSTANCE, rimestar).getOrThrow());
            index++;
        }

        return compoundTag;
    }

    @Override
    public Codec<RimestarObject> getCodec() {
        return RimestarObject.codec.CODEC;
    }

    @Override
    public RimestarManager load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        RimestarManager manager = create();

        int size = compoundTag.getInt("size");

        for(int i = 1; i <= size; i++) {
            RimestarObject result = RimestarObject.codec.CODEC.parse(NbtOps.INSTANCE, compoundTag.get(String.valueOf(i))).getOrThrow();
            manager.interactables.add(result);
        }

        return manager;
    }

    @Override
    public RimestarManager create() {
        return new RimestarManager();
    }
}
