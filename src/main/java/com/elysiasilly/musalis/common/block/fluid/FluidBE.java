package com.elysiasilly.musalis.common.block.fluid;

import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nullable;

public class FluidBE extends BlockEntity {

    private float pressure = 0;

    private final int TANK_SIZE = 16000;
    private final String NBT_TANK = "FluidTank";
    private final FluidTank fluidTank = createFluidTank();
    //createFluidTank();
    private final Lazy<FluidTank> fluidHandler = Lazy.of(() -> this.fluidTank);

    public FluidBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.FLUID_BE.get(), pos, blockState);
    }

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

    public FluidTank getTank() { return fluidTank; }

    public int getTankSize() { return this.TANK_SIZE; }

    public int getFluidAmount() { return fluidTank.getFluidAmount(); }

    public float getPressure() { return pressure; }
    /// saving data, in this case Items


    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        saveClientData(tag, registries);
    }

    private void saveClientData(CompoundTag tag, HolderLookup.Provider provider) {
        //: tag.put(NBT_ITEMS, items.serializeNBT(provider));
        tag.put(NBT_TANK, fluidTank.writeToNBT(provider, new CompoundTag()));
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        loadClientData(tag, provider);
    }

    private void loadClientData(CompoundTag tag, HolderLookup.Provider provider) {
        //: if (tag.contains(NBT_ITEMS)) {
        //:     items.deserializeNBT(provider, tag.getCompound(NBT_ITEMS));
        //: }
        if (tag.contains(NBT_TANK)) {
            fluidTank.readFromNBT(provider, tag.getCompound(NBT_TANK));
        }
    }


    // The getUpdateTag()/handleUpdateTag() pair is called whenever the client receives a new chunk
    // it hasn't seen before. i.e. the chunk is loaded
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = super.getUpdateTag(provider);
        saveClientData(tag, provider);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
        if (tag != null) {
            loadClientData(tag, provider);
        }
    }

    // The getUpdatePacket()/onDataPacket() pair is used when a block update happens on the client
    // (a blockstate change or an explicit notification of a block update from the server). It's
    // easiest to implement them based on getUpdateTag()/handleUpdateTag()
    /*
    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

     */




    /// BE behavior logic

    public void tickServer() {

        //assert level != null;
        if (level.getGameTime() % 5 != 0) return;

        pressure = (float) getFluidAmount() / getTankSize() - 0.01f;

        //System.out.println(pressure + " " + getFluidAmount() + " " + getTankSize());

        //assert level != null;
        if(level.getBlockEntity(getBlockPos().west()) instanceof FluidBE be) {

            if(!(be.pressure >= pressure)) {
                be.getTank().setFluid(new FluidStack(getTank().getFluid().getFluid(), be.getFluidAmount() + (int) (getFluidAmount() * pressure)));
                getTank().setFluid(new FluidStack(getTank().getFluid().getFluid(), getFluidAmount() - (int) (getFluidAmount() * pressure)));
            }
        }


        if(level.getBlockEntity(getBlockPos().east()) instanceof FluidBE be) {

            if(!(be.pressure >= pressure)) {
                be.getTank().setFluid(new FluidStack(getTank().getFluid().getFluid(), be.getFluidAmount() + (int) (getFluidAmount() * pressure)));
                getTank().setFluid(new FluidStack(getTank().getFluid().getFluid(), getFluidAmount() - (int) (getFluidAmount() * pressure)));
            }
        }

        if(level.getBlockEntity(getBlockPos().above()) instanceof FluidBE be) {

            pressure = pressure + be.getPressure();
        }
    }

    //: adds items to inventory only if there's enough space
    //: public boolean placeItem(@Nullable Entity entity, ItemStack entityStack) {
    //:     ItemStack invStack = this.items.getStackInSlot(SLOT_INPUT);
    //:     if (invStack.isEmpty() ||
    //:         invStack.getItem().equals(entityStack.getItem()) && invStack.getCount() + entityStack.getCount() <= entityStack.getMaxStackSize()) {
    //:         var newStack = entityStack.consumeAndReturn(entityStack.getCount(), (LivingEntity) entity);
    //:         newStack.setCount(newStack.getCount() + invStack.getCount());
    //:         this.items.setStackInSlot(SLOT_INPUT, newStack);
//:
    //:         this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
    //:         this.markUpdated();
    //:         return true;
    //:     }
//:
    //:     return false;
    //: }

    public boolean fillTank(@Nullable Entity entity, FluidStack fluidStack) {
        FluidStack innerTank = this.fluidTank.getFluid();

        //Musalis.LOGGER.info("filling from: " + innerTank.getAmount());


        if(innerTank.getAmount() + fluidStack.getAmount() <= TANK_SIZE) {
            var newFluid = fluidStack.copyAndClear();
            newFluid.setAmount(newFluid.getAmount() + innerTank.getAmount());
            this.fluidTank.setFluid(newFluid);

            this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(entity, this.getBlockState()));
            this.markUpdated();

            //Musalis.LOGGER.info("new amount: " + fluidTank.getFluidAmount() + ": " + fluidTank.getFluid().getFluid().getFluidType());
            return true;
        }
        return false;
    }

    public boolean emptyTank() {
        //Musalis.LOGGER.info("emptying");
        this.fluidTank.getFluid().copyAndClear();
        return true;
    }


    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}
