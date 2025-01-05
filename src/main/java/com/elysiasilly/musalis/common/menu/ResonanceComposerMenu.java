package com.elysiasilly.musalis.common.menu;

import com.elysiasilly.musalis.common.blockentity.creative.CrResonanceComposerBE;
import com.elysiasilly.musalis.core.registry.MUMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

public class ResonanceComposerMenu extends AbstractContainerMenu {

    CrResonanceComposerBE be;

    public ResonanceComposerMenu(int containerId, Inventory playerInventory, CrResonanceComposerBE be) {
        super(MUMenus.RESONANCE_COMPOSER.get(), containerId);
        this.be = be;
    }

    public ResonanceComposerMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory, getBE(playerInventory, data));
    }

    private static CrResonanceComposerBE getBE(Inventory playerInventory, FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity be = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (be instanceof CrResonanceComposerBE) {return (CrResonanceComposerBE) be;}
        throw new IllegalStateException("BE is not correct! " + be);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int i) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    public CrResonanceComposerBE getBE() {
        return this.be;
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        //System.out.println(this.test);
    }

    @Override
    public void broadcastChanges() {
        super.broadcastChanges();
    }
}
