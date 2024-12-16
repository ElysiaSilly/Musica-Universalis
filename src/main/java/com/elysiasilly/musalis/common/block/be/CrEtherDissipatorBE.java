package com.elysiasilly.musalis.common.block.be;

import com.elysiasilly.musalis.common.block.api.IEtherCoreHolder;
import com.elysiasilly.musalis.common.component.EtherCoreComponent;
import com.elysiasilly.musalis.common.world.ether.EtherStack;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.registry.MUComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CrEtherDissipatorBE extends BlockEntity implements IEtherCoreHolder {

    ItemStack core;

    public CrEtherDissipatorBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.CREATIVE_ETHER_DISSIPATOR.get(), pos, blockState);
    }

    public void tick() {
        if(this.core != null) {
            if(this.core.has(MUComponents.ETHER_CORE)) {
                EtherCoreComponent component = core.get(MUComponents.ETHER_CORE);
                if(component == null) return;
                EtherStack ether = component.getEther();
                if(ether == null) return;
                if(ether.getAmount() > 0) ether.getEther().passiveDissipate(this.core, getBlockPos(), getLevel());
                component.extract(1);
            }
        }
    }

    public ItemStack extractCore() {
        if(this.core == null) return ItemStack.EMPTY;
        if(this.core.isEmpty()) return ItemStack.EMPTY;
        ItemStack copy = this.core.copy();
        this.core.copyAndClear();
        markUpdated();
        return copy;
    }

    public boolean insertCore(ItemStack stack) {
        if(this.core != null) {
            if(!this.core.isEmpty()) return false;
        }
        if(stack.has(MUComponents.ETHER_CORE)) {
            this.core = stack.copyWithCount(1);
            stack.shrink(1);
            markUpdated();
            return true;
        }
        return false;
    }

    public ItemStack getCore() {
        return this.core;
    }

    // todo

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void markUpdated() {
        this.setChanged();
        assert level != null;
        level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_CLIENTS);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        return saveData(tag, registries);
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider lookupProvider) {
        super.handleUpdateTag(tag, lookupProvider);
        loadData(tag, lookupProvider);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        saveData(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        loadData(tag, registries);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        loadData(pkt.getTag(), lookupProvider);
    }

    public CompoundTag saveData(CompoundTag tag, HolderLookup.Provider registries) {

        assert level != null;
        NonNullList<ItemStack> iHateData = NonNullList.create();
        if(this.core == null) return tag;
        iHateData.add(this.core);
        ContainerHelper.saveAllItems(tag, iHateData, registries);

        return tag;
    }

    public CompoundTag loadData(CompoundTag tag, HolderLookup.Provider registries) {

        NonNullList<ItemStack> iHateData = NonNullList.create();
        ContainerHelper.loadAllItems(tag, iHateData, registries);
        if(iHateData.isEmpty()) return tag;
        this.core = iHateData.getFirst();

        return tag;
    }

    @Override
    public Vec3 snapPosition() {
        return new Vec3(0, (float) 1 / 16 * 9, 0).add(this.getBlockPos().getCenter());
    }
}
