package com.elysiasilly.musalis.common.block.be;

import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.util.RegistryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CrResonanceDissolverBE extends NetworkingBE {

    List<Note> notes = new ArrayList<>();

    public CrResonanceDissolverBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.CREATIVE_RESONANCE_DISSOLVER.get(), pos, blockState);
    }

    @Override
    public CompoundTag saveData(CompoundTag tag, HolderLookup.Provider registries) {
        return tag;
    }

    @Override
    public void loadData(CompoundTag tag, HolderLookup.Provider registries) {}

    public boolean dissolveItem(ItemStack stack) {
        if(level == null) return false;
        Resonance resonance = RegistryUtil.getResonance(level, stack.getItem());
        if(resonance == null) return false;
        this.notes.addAll(resonance.getLeitmotif().getNotes());
        stack.shrink(1);
        markUpdated();
        return true;
    }

    public List<Note> extractNotes() {
        List<Note> copy = this.notes;
        //this.notes.clear();
        markUpdated();
        return copy;
    }
}
