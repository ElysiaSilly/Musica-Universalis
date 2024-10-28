package com.elysiasilly.musalis.common.block.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.Nullable;

public abstract class FluidBlockBE extends NetworkingBE{

    protected FluidBlockBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    private final FluidTank fluidHandler = new FluidTank(16000) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
        }
    };

    private final Lazy<IFluidHandler> lazyFluidHandler = Lazy.of(() -> fluidHandler);

    public Lazy<IFluidHandler> getLazyFluidHandler() {
        return lazyFluidHandler;
    }

    public FluidTank getFluidHandler() {
        return fluidHandler;
    }

    @Override
    public void invalidateCapabilities() {
        super.invalidateCapabilities();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("fluidHandler", getFluidHandler().writeToNBT(registries, new CompoundTag()));
        super.saveAdditional(tag, registries);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        getFluidHandler().readFromNBT(provider, tag.getCompound("fluidHandler"));
        super.loadAdditional(tag, provider);
    }

    @Override
    public void setChanged() {
        super.setChanged();
        assert level != null;
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
