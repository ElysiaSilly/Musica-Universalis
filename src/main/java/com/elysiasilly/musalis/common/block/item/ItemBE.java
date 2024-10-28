package com.elysiasilly.musalis.common.block.item;

import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemBE extends BlockEntity {

    private final ItemStackHandler itemStackHandler = createItemHandler();
    private final Lazy<IItemHandler> itemHandler = Lazy.of(() -> itemStackHandler);

    BlockState blockstate;
    BlockPos position;

    public static final String ITEMS_TAG = "Inventory";

    public static int SLOT_COUNT = 1;
    public static int SLOT = 0;

    public ItemBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.ITEM_BE.get(), pos, blockState);
        this.blockstate = blockState;
        this.position = pos;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
    }

    @Nonnull
    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(SLOT_COUNT) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged(); /// call this every time there's a change
                assert level != null;
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
            }
        };
    }

    public IItemHandler getItemHandler() {
        return this.itemHandler.get();
    }

    private void saveClientData(CompoundTag tag, HolderLookup.Provider provider) {
        tag.put(ITEMS_TAG, itemStackHandler.serializeNBT(provider));
    }

    private void loadClientData(CompoundTag tag, HolderLookup.Provider provider) {
        if (tag.contains(ITEMS_TAG)) {
            itemStackHandler.deserializeNBT(provider, tag.getCompound(ITEMS_TAG));
        }
    }

    // BE behavior logic

    public void tickServer() {
        if (level.getGameTime() % 20 == 0) {
            ItemStack stack = itemStackHandler.getStackInSlot(SLOT);
            if (!stack.isEmpty()) {
                if (stack.isDamageableItem()) {
                    // Increase durability of item
                    int value = stack.getDamageValue();
                    if (value > 0) {
                        stack.setDamageValue(value - 1);
                        BlockPos pos = worldPosition;
                        ((ServerLevel)level).sendParticles(ParticleTypes.SMOKE, pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5, 10, 0, 0, 0, 0.15);
                    } else {
                        ejectItem();
                    }
                } else {
                    ejectItem();
                }
            }
        }
    }

    private void ejectItem() {
        BlockPos pos = worldPosition.relative(Direction.UP);
        assert level != null;
        Block.popResource(level, pos, itemStackHandler.extractItem(SLOT, 1, false));
    }

    public boolean placeItem(@Nullable Entity pEntity, ItemStack pStack) {
        ItemStack itemstack = this.itemStackHandler.getStackInSlot(SLOT);
        if (itemstack.isEmpty()) {
            this.itemStackHandler.setStackInSlot(SLOT, pStack.split(1));
            assert this.level != null;
            this.level.gameEvent(GameEvent.BLOCK_CHANGE, this.getBlockPos(), GameEvent.Context.of(pEntity, this.getBlockState()));
            this.markUpdated();
            return true;
        }

        return false;
    }

    private void markUpdated() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}
