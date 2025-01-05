package com.elysiasilly.musalis.common.blockentity;

import com.elysiasilly.babel.common.block.BabelBE;
import com.elysiasilly.musalis.common.menu.RMIMenu;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RMIBE extends BabelBE implements MenuProvider {

    int position = 0;
    float xRot = 90;
    float yRot = 0;

    public RMIBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.RMI.get(), pos, blockState);
    }

    @Override
    public CompoundTag serialize(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putInt("pos", this.position);
        tag.putFloat("x", this.xRot);
        tag.putFloat("y", this.yRot);
        return tag;
    }

    @Override
    public void deserialize(CompoundTag tag, HolderLookup.Provider registries) {
        this.position = tag.getInt("pos");
        this.xRot = tag.getFloat("x");
        this.yRot = tag.getFloat("y");
    }

    public void addPosition(int amount) {
        this.position = this.position + amount;
        //if(temp > 1) this.position = temp;
        setDirty();
    }

    public int getPosition() {
        return this.position;
    }

    public void addXRot(float num) {
        float temp = this.xRot + num;
        if(temp > 80 || temp < -40) return;
        this.xRot = temp;
        setDirty();
    }

    public float getXRot() {
        return this.xRot;
    }

    public void addYRot(float num) {
        float temp = this.yRot + num;
        if(temp > 360) temp = 0;
        if(temp < 0) temp = 360;
        this.yRot = temp;
        setDirty();
    }

    public void centreYRot() {
        this.yRot = MathUtil.numbers.closest(this.yRot, 0, 90, 180, 270, 360);
        setDirty();
    }

    public float getYRot() {
        return this.yRot;
    }

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new RMIMenu(id, inventory, this);
    }
}
