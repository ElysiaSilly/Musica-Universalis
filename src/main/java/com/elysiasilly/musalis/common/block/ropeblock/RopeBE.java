package com.elysiasilly.musalis.common.block.ropeblock;

import com.elysiasilly.musalis.common.physics.rope.Rope;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.util.SerializeUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RopeBE extends BlockEntity {

    private Rope rope;

    @OnlyIn(Dist.CLIENT)
    private final List<Vec3> ropeSegmentsCLIENT = new ArrayList<>();

    @OnlyIn(Dist.CLIENT)
    public List<Vec3> getRopeSegmentsCLIENT() {
        return ropeSegmentsCLIENT;
    }

    public RopeBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.ROPE_BE.get(), pos, blockState);
    }

    public void tick() {
        //if(rope != null) rope.tick();

        //markUpdated();
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);

        if(level.isClientSide) return;

        float test = level.random.nextFloat();

        rope = new Rope(level, new Vec3(.5f, 0, .5f));

        for(int iterate = 0; iterate <= level.random.nextIntBetweenInclusive(4, 16); iterate++) {
            rope.addSegment(test);
        }

        rope.tick();
        markUpdated();

            /*
        System.out.println("RUN !!!!!!!!!!!!!!!!!!!!!");

        rope = new Rope(level, new Vec3(.5f, 0, .5f));

        for(int iterate = 0; iterate < 10; iterate++) {
            rope.addSegment(.3f);
        }

        rope.tick();

        markUpdated();

         */
    }

    public Rope getRope() {
        return this.rope;
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();

        if(rope.segments == null) return tag;
        if(rope.segments.isEmpty()) return tag;

        for(int iterate = 0; iterate <= rope.segments.size();) {
            if(iterate == rope.segments.size()) break;
            Rope.RopeSegment segment = rope.segments.get(iterate);
            tag.putString(String.format("segment_position_%s", iterate), SerializeUtil.packVec3(segment.getPosition()));
            iterate++;
        }

        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
        super.handleUpdateTag(tag, provider);

        getRopeSegmentsCLIENT().clear();

        int iterate = 0;
        boolean isNull = false;

        while(!isNull) {

            String string = tag.getString(String.format("segment_position_%s", iterate));
            iterate++;

            if(string.isEmpty()) {
                isNull = true;
            } else {
                getRopeSegmentsCLIENT().add(SerializeUtil.unpackVec3(string));
            }

        }
    }

    private void markUpdated() {
        this.setChanged();
        assert level != null;
        level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}
