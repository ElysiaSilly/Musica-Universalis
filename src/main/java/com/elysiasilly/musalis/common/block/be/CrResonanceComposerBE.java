package com.elysiasilly.musalis.common.block.be;

import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.registry.MUComponents;
import com.elysiasilly.musalis.core.util.ItemUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CrResonanceComposerBE extends NetworkingBE implements MenuProvider {

    ItemStack disk;
    modes mode = CrResonanceComposerBE.modes.DELETE;

    public enum modes implements StringRepresentable {
        DELETE("delete"),
        LOOKUP("lookup"),
        SELECT("select");

        private final String name;

        modes(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }

    public CrResonanceComposerBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.CREATIVE_RESONANCE_COMPOSER.get(), pos, blockState);
    }

    @Override
    public CompoundTag saveData(CompoundTag tag, HolderLookup.Provider registries) {
        ItemUtil.serialize("disk", this.disk, tag, registries);
        tag.putString("mode", this.mode.name());
        return tag;
    }

    @Override
    public void loadData(CompoundTag tag, HolderLookup.Provider registries) {
        this.disk = ItemUtil.deserialize("disk", tag, registries);
        //this.mode = Mode.valueOf(tag.get("mode").toString());
    }

    public void setMode(modes mode) {
        this.mode = mode;
        markUpdated();
    }

    public modes getMode() {
        return this.mode;
    }

    public boolean insertDisk(ItemStack stack) {
        if(!ItemUtil.isEmpty(this.disk)) return false;

        if(!stack.has(MUComponents.DATA_DISK)) return false;
        DataDiskComponent component = stack.get(MUComponents.DATA_DISK);
        if(component == null) return false;
        this.disk = ItemUtil.splitAndCopy(stack, 1);

        markUpdated();
        return true;
    }

    public ItemStack extractDisk() {
        if(ItemUtil.isEmpty(this.disk)) return ItemStack.EMPTY;
        ItemStack copy = ItemUtil.splitAndCopy(this.disk, 1);
        markUpdated();
        return copy;
    }

    public void composeDisk(int index) {
        if(ItemUtil.isEmpty(this.disk)) return;

        DataDiskComponent component = this.disk.get(MUComponents.DATA_DISK);

        if(component == null) return;

        Leitmotif leitmotif = component.getLeitmotif();

        if(leitmotif == null) return;

        /*
        if(this.mode.equals(CrResonanceComposerBE.modes.DELETE)) {
            if(leitmotif.type().equals(Leitmotif.type.RECURSIVE)) {
                List<Leitmotif> copy = new ArrayList<>(leitmotif.getLeitmotifs());

                copy.remove(index);

                System.out.println("help");

                this.disk.set(MUComponents.DATA_DISK, new DataDiskComponent(new Leitmotif(List.of(), copy)));
                markUpdated();
            }
        }

         */
        if(this.mode.equals(modes.DELETE)) {
            if(leitmotif.type().equals(Leitmotif.type.RECURSIVE)) {
                List<Leitmotif> copy = new ArrayList<>(leitmotif.getLeitmotifs());

                for(Leitmotif recursive : copy) {
                    if(recursive.hashCode() == index) {
                        copy.remove(recursive);
                        break;
                    }
                }

                //copy.add(index + 1, leitmotif.getLeitmotifs().get(index));

                //System.out.println("help");

                this.disk.set(MUComponents.DATA_DISK, new DataDiskComponent(new Leitmotif(List.of(), copy)));
                markUpdated();
            }
        }
    }

    public void process() {}

    public ItemStack getDisk() {
        return this.disk;
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ResonanceComposerMenu(id, inventory, this);
    }

}
