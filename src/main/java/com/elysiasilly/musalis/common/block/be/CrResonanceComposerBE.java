package com.elysiasilly.musalis.common.block.be;

import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.registry.MUComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrResonanceComposerBE extends NetworkingBE implements MenuProvider {

    ItemStack disk;
    int mode;

    public CrResonanceComposerBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.CREATIVE_RESONANCE_COMPOSER.get(), pos, blockState);
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

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return this.mode;
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

    public void composeDisk(int index) {
        this.disk = ItemStack.EMPTY;

        /*
        if(this.disk == null) return;
        if(this.disk.isEmpty()) return;

        DataDiskComponent component = this.disk.get(MUComponents.DATA_DISK);

        if(component == null) return;

        Leitmotif leitmotif = component.getLeitmotif();

        if(leitmotif == null) return;

        if(this.mode == 1) {
            if(leitmotif.isRecursive()) {
                leitmotif.getLeitmotifs().remove(index);
                this.disk.set(MUComponents.DATA_DISK, new DataDiskComponent(leitmotif));
                markUpdated();
            }
        }
         */
    }


    public void process() {

    }
    public ItemStack getDisk() {
        return this.disk;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("test");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ResonanceComposerMenu(id, inventory, this);
    }

}
