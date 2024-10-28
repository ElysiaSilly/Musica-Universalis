package com.elysiasilly.musalis.common.component;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;

public record GPSComponent(BlockPos blockPos) {

    public static final GPSComponent EMPTY = new GPSComponent(BlockPos.ZERO);

    public static MapCodec<GPSComponent> MAP_CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            BlockPos.CODEC.fieldOf("blockpos").forGetter(GPSComponent::blockPos)
    ).apply(builder, GPSComponent::new));

    public static StreamCodec<ByteBuf, GPSComponent> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            GPSComponent::blockPos,
            GPSComponent::new
    );

    public GPSComponent setBlockPos(BlockPos blockPos) {
        return new GPSComponent(blockPos);
    }

}
