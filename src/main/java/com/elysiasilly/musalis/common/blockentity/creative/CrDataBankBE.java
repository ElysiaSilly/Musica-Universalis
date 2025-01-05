package com.elysiasilly.musalis.common.blockentity.creative;

import com.elysiasilly.babel.common.block.BabelBE;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class CrDataBankBE extends BabelBE {
    public CrDataBankBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.CREATIVE_NOTE_TANK.get(), pos, blockState);
    }

    @Override
    public CompoundTag serialize(CompoundTag tag, HolderLookup.Provider registries) {
        return tag;
    }

    @Override
    public void deserialize(CompoundTag tag, HolderLookup.Provider registries) {

    }
}
