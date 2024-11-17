package com.elysiasilly.musalis.common.block.dissolver;

import com.elysiasilly.musalis.common.resonance.Resonance;
import com.elysiasilly.musalis.common.resonance.String;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class DissolverBE extends BlockEntity {

    private final List<String> soup = new ArrayList<>();
    private Resonance store;

    public DissolverBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.DISSOLVER.get(), pos, blockState);
    }

    public void setStore(Resonance resonance) {
        this.store = resonance;
    }

    public Resonance getStore() {
        return this.store;
    }

    public void addSoup(String soup) {
        this.soup.add(soup);
    }

    public List<String> getSoup() {
        return this.soup;
    }

    public void removeSoup(String string) {
        this.soup.remove(string);
    }
}
