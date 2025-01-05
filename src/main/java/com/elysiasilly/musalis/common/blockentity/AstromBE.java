package com.elysiasilly.musalis.common.blockentity;

import com.elysiasilly.babel.common.block.BabelBE;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class AstromBE extends BabelBE {

    public AstromBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.ASTROM.get(), pos, blockState);
    }

    @Override
    public CompoundTag serialize(CompoundTag tag, HolderLookup.Provider registries) {
        return null;
    }

    @Override
    public void deserialize(CompoundTag tag, HolderLookup.Provider registries) {

    }
}
