package com.elysiasilly.musalis.common.block.ropeblock;

import com.elysiasilly.musalis.common.block.base.NetworkingBE;
import com.elysiasilly.musalis.common.physics.rope.Rope;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class RopeBE extends NetworkingBE {

    private Rope rope;

    public RopeBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.ROPE_BE.get(), pos, blockState);
    }

    public void tick() {
        if(rope != null) rope.tick();

        this.setChanged();
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    @Override
    public void setLevel(Level setLevel) {
        super.setLevel(setLevel);

        //assert level != null;
        if(level.isClientSide) return;

        this.rope = new Rope(level, new Vec3(0, 0, 0));

        for(int iterate = 0; iterate < 2; iterate++) {
               rope.addSegment(0.5f);
        }

        this.setChanged();
        this.getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    public Rope getRope() {
        return this.rope;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
    }
}
