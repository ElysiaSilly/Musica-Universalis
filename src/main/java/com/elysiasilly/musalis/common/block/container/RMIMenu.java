package com.elysiasilly.musalis.common.block.container;

import com.elysiasilly.musalis.common.block.be.RMIBE;
import com.elysiasilly.musalis.core.registry.MUMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

public class RMIMenu extends AbstractContainerMenu {

    RMIBE be;

    public RMIMenu(int containerId, Inventory playerInventory, RMIBE be) {
        super(MUMenus.RMI.get(), containerId);
        this.be = be;
    }

    public RMIMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory, getBE(playerInventory, data));
    }

    private static RMIBE getBE(Inventory playerInventory, FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity be = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (be instanceof RMIBE) {return (RMIBE) be;}
        throw new IllegalStateException("BE is not correct! " + be);
    }

    public RMIBE getBE() {
        return this.be;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
