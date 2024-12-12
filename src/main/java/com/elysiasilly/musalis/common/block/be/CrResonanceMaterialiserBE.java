package com.elysiasilly.musalis.common.block.be;

import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.registry.MUComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class CrResonanceMaterialiserBE extends NetworkingBE{

    ItemStack disk;

    public CrResonanceMaterialiserBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.CREATIVE_RESONANCE_MATERIALISER.get(), pos, blockState);
    }

    @Override
    public CompoundTag saveData(CompoundTag tag, HolderLookup.Provider registries) {

        NonNullList<ItemStack> iHateData = NonNullList.create();

        if(this.disk != null) {
            iHateData.add(this.disk);
        }
        ContainerHelper.saveAllItems(tag, iHateData, registries);

        return tag;
    }

    @Override
    public void loadData(CompoundTag tag, HolderLookup.Provider registries) {

        NonNullList<ItemStack> iHateData = NonNullList.create();

        ContainerHelper.loadAllItems(tag, iHateData, registries);
        if(iHateData.isEmpty()) return;

        for(ItemStack stack : iHateData) {
            insertDisk(stack);
        }
    }

    public boolean insertDisk(ItemStack stack) {
        if(this.disk != null) {
            if(!this.disk.isEmpty()) return false;
        }
        if(!stack.has(MUComponents.DATA_DISK)) return false;
        DataDiskComponent component = stack.get(MUComponents.DATA_DISK);
        if(component == null) return false;
        this.disk = stack.copyWithCount(1);
        stack.shrink(1);
        markUpdated();
        return true;
    }

    public ItemStack extractDisk() {
        if(this.disk == null) return ItemStack.EMPTY;
        if(this.disk.isEmpty()) return ItemStack.EMPTY;

        ItemStack copy = this.disk.copyWithCount(1);
        this.disk.shrink(1);
        markUpdated();
        return copy;
    }

    public ItemStack getDisk() {
        return this.disk;
    }
}
