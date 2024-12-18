package com.elysiasilly.musalis.common.block.be;

import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.registry.MUComponents;
import com.elysiasilly.musalis.core.util.ItemUtil;
import com.elysiasilly.musalis.core.util.RegistryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class CrResonanceRecorderBE extends NetworkingBE {

    ItemStack disk;
    ItemStack item;

    boolean ejectDisk;

    public CrResonanceRecorderBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.CREATIVE_RESONANCE_RECORDER.get(), pos, blockState);
    }

    @Override
    public CompoundTag saveData(CompoundTag tag, HolderLookup.Provider registries) {
        ItemUtil.serialize("disk", this.disk, tag, registries);
        ItemUtil.serialize("item", this.item, tag, registries);
        tag.putBoolean("eject", this.ejectDisk);
        return tag;
    }

    @Override
    public void loadData(CompoundTag tag, HolderLookup.Provider registries) {
        this.disk = ItemUtil.deserialize("disk", tag, registries);
        this.item = ItemUtil.deserialize("item", tag, registries);
        this.ejectDisk = tag.getBoolean("eject");
    }

    public boolean insertItem(ItemStack stack) {
        if(this.item != null) {
            if(!this.item.isEmpty()) return false;
        }
        if(level == null) return false;
        Resonance resonance = RegistryUtil.getResonance(level, stack.getItem());
        if(resonance == null) return false;
        this.item = stack.copyWithCount(1);
        stack.shrink(1);
        markUpdated();
        return true;
    }

    public ItemStack extractItem() {
        if(this.item == null) return ItemStack.EMPTY;
        if(this.item.isEmpty()) return ItemStack.EMPTY;

        ItemStack copy = this.item.copyWithCount(1);
        this.item.shrink(1);
        markUpdated();
        return copy;
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
        this.ejectDisk = false;
        markUpdated();
        return true;
    }

    public ItemStack extractDisk() {
        if(ItemUtil.isEmpty(this.disk)) return ItemStack.EMPTY;
        ItemStack copy = ItemUtil.splitAndCopy(this.disk, 1);
        this.ejectDisk = false;
        markUpdated();
        return copy;
    }

    public ItemStack getDisk() {
        return this.disk;
    }

    public ItemStack getItem() {
        return this.item;
    }

    public void tick() {
        if(!this.ejectDisk) {
            if(!ItemUtil.isEmpty(this.item, this.disk)) {
                Resonance resonance = RegistryUtil.getResonance(level, this.item.getItem());
                this.disk.set(MUComponents.DATA_DISK, new DataDiskComponent(resonance.unpack()));

                this.ejectDisk = true;
                markUpdated();
            }
        }
    }

    public boolean getEjectDisk() {
        return this.ejectDisk;
    }
}
