package com.elysiasilly.musalis.common.blockentity.creative;

import com.elysiasilly.babel.common.block.BabelBE;
import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.registry.MUComponents;
import com.elysiasilly.musalis.util.MCUtil;
import com.elysiasilly.musalis.util.RegistryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class CrResonanceRecorderBE extends BabelBE {

    ItemStack disk;
    ItemStack item;

    boolean ejectDisk;

    public CrResonanceRecorderBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.CREATIVE_RESONANCE_RECORDER.get(), pos, blockState);
    }

    @Override
    public CompoundTag serialize(CompoundTag tag, HolderLookup.Provider registries) {
        MCUtil.item.serialize("disk", this.disk, tag, registries);
        MCUtil.item.serialize("item", this.item, tag, registries);
        tag.putBoolean("eject", this.ejectDisk);
        return tag;
    }

    @Override
    public void deserialize(CompoundTag tag, HolderLookup.Provider registries) {
        this.disk = MCUtil.item.deserialize("disk", tag, registries);
        this.item = MCUtil.item.deserialize("item", tag, registries);
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
        setDirty();
        return true;
    }

    public ItemStack extractItem() {
        if(this.item == null) return ItemStack.EMPTY;
        if(this.item.isEmpty()) return ItemStack.EMPTY;

        ItemStack copy = this.item.copyWithCount(1);
        this.item.shrink(1);
        setDirty();
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
        setDirty();
        return true;
    }

    public ItemStack extractDisk() {
        if(MCUtil.item.isEmpty(this.disk)) return ItemStack.EMPTY;
        ItemStack copy = this.disk.split(1);
        this.ejectDisk = false;
        setDirty();
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
            if(!MCUtil.item.isEmpty(this.item, this.disk)) {
                Resonance resonance = RegistryUtil.getResonance(level, this.item.getItem());
                //this.disk.set(MUComponents.DATA_DISK, new DataDiskComponent(resonance.unpack()));

                this.ejectDisk = true;
                setDirty();
            }
        }
    }

    public boolean getEjectDisk() {
        return this.ejectDisk;
    }
}
