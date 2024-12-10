package com.elysiasilly.musalis.common.block.be;

import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CrResonanceComposerBE extends NetworkingBE{

    List<Note> notes = new ArrayList<>();

    public CrResonanceComposerBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.CREATIVE_RESONANCE_COMPOSER.get(), pos, blockState);
    }

    @Override
    public CompoundTag saveData(CompoundTag tag, HolderLookup.Provider registries) {
        return tag;
    }

    @Override
    public void loadData(CompoundTag tag, HolderLookup.Provider registries) {}
}
