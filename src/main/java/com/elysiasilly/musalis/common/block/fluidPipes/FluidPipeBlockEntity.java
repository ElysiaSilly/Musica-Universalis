package com.elysiasilly.musalis.common.block.fluidPipes;

import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class FluidPipeBlockEntity extends BlockEntity {
    public FluidPipeBlockEntity(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.FLUID_PIPE_BE.get(), pos, blockState);
    }

    private int PRESSURE = 0;
    private final int TANK_SIZE = 16000;

    private final String NBT_TANK = "FluidTank";
    private final FluidTank fluidTank = createFluidTank();
    private final Lazy<FluidTank> fluidHandler = Lazy.of(() -> this.fluidTank);

    private FluidTank createFluidTank() {
        return new FluidTank(TANK_SIZE) {
            @Override
            protected void onContentsChanged() {
                super.onContentsChanged();
                setChanged();
                assert level != null;
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        };
    }

    public FluidTank getTank() {return fluidTank;}

    public int getTankSize() {return this.TANK_SIZE;}

    public int getFluidAmount() {return fluidTank.getFluidAmount();}

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        saveClientData(tag, registries);
    }

    private void saveClientData(CompoundTag tag, HolderLookup.Provider provider) {
        tag.put(NBT_TANK, fluidTank.writeToNBT(provider, new CompoundTag()));
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        loadClientData(tag, provider);
    }

    private void loadClientData(CompoundTag tag, HolderLookup.Provider provider) {
        if (tag.contains(NBT_TANK)) {
            fluidTank.readFromNBT(provider, tag.getCompound(NBT_TANK));
        }
    }

    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        saveClientData(tag, provider);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
        loadClientData(tag, provider);
    }

    public void tickServer() {
        if(this.fluidTank.getFluidAmount() >= this.fluidTank.getCapacity()) {
            return;
        }

        // Lazy<IFluidHandlerItem> fluidItem = stack


    }
}
