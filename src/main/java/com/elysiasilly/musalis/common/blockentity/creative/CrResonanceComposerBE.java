package com.elysiasilly.musalis.common.blockentity.creative;

import com.elysiasilly.babel.common.block.BabelBE;
import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.common.menu.ResonanceComposerMenu;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.registry.MUComponents;
import com.elysiasilly.musalis.util.MCUtil;
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

public class CrResonanceComposerBE extends BabelBE implements MenuProvider {

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
    public CompoundTag serialize(CompoundTag tag, HolderLookup.Provider registries) {
        MCUtil.item.serialize("disk", this.disk, tag, registries);
        tag.putString("mode", this.mode.name());
        return tag;
    }

    @Override
    public void deserialize(CompoundTag tag, HolderLookup.Provider registries) {
        this.disk = MCUtil.item.deserialize("disk", tag, registries);
        //this.mode = Mode.valueOf(tag.get("mode").toString());
    }

    public void setMode(modes mode) {
        this.mode = mode;
        setDirty();
    }

    public modes getMode() {
        return this.mode;
    }

    public boolean insertDisk(ItemStack stack) {
        if(!MCUtil.item.isEmpty(this.disk)) return false;

        if(!stack.has(MUComponents.DATA_DISK)) return false;
        DataDiskComponent component = stack.get(MUComponents.DATA_DISK);
        if(component == null) return false;
        this.disk = stack.split(1);

        setDirty();
        return true;
    }

    public ItemStack extractDisk() {
        if(MCUtil.item.isEmpty(this.disk)) return ItemStack.EMPTY;
        ItemStack copy = this.disk.split(1);
        setDirty();
        return copy;
    }

    public void composeDisk(int index) {
        if(MCUtil.item.isEmpty(this.disk)) return;

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
            if(leitmotif.isRecursive()) {
                List<Leitmotif> copy = new ArrayList<>(leitmotif.getLeitmotifs());

                for(Leitmotif recursive : copy) {
                    if(recursive.hashCode() == index) {
                        copy.remove(recursive);
                        break;
                    }
                }

                //copy.add(index + 1, leitmotif.getLeitmotifs().get(index));

                //System.out.println("help");

                this.disk.set(MUComponents.DATA_DISK, new DataDiskComponent(new Leitmotif(copy)));
                setDirty();
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
