package com.elysiasilly.musalis.networking.payloads;

import com.elysiasilly.musalis.core.Musalis;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record RimestarChunkWeightPayload(int x, int z, float weight) implements CustomPacketPayload {

    public static final Type<RimestarChunkWeightPayload> TYPE = new Type<>(Musalis.location("rimestar_chunk_weight"));

    public static final StreamCodec<ByteBuf, RimestarChunkWeightPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            RimestarChunkWeightPayload::x,
            ByteBufCodecs.INT,
            RimestarChunkWeightPayload::z,
            ByteBufCodecs.FLOAT,
            RimestarChunkWeightPayload::weight,
            RimestarChunkWeightPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
