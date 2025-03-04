package com.elysiasilly.musalis.common.blockentity.creative;

import com.elysiasilly.babel.common.block.BabelBE;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.util.RegistryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CrResonanceDissolverBE extends BabelBE {

    List<Note> notes = new ArrayList<>();

    public CrResonanceDissolverBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.CREATIVE_RESONANCE_DISSOLVER.get(), pos, blockState);
    }

    @Override
    public CompoundTag serialize(CompoundTag tag, HolderLookup.Provider registries) {
        return tag;
    }

    @Override
    public void deserialize(CompoundTag tag, HolderLookup.Provider registries) {}

    public boolean dissolveItem(ItemStack stack) {
        if(level == null) return false;
        Resonance resonance = RegistryUtil.getResonance(level, stack.getItem());
        if(resonance == null) return false;
        //this.notes.addAll(resonance.unpack().getNotes());
        stack.shrink(1);
        setDirty();
        return true;
    }

    public List<Note> extractNotes() {
        if(level.isClientSide) return null;
        List<Note> copy = this.notes;
        //this.notes.clear();
        setDirty();
        return copy;
    }
}
