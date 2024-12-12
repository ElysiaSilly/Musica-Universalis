package com.elysiasilly.musalis.networking.payloads;

import com.elysiasilly.musalis.core.MusicaUniversalis;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ComposerScreenPayload(int index, int id) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ComposerScreenPayload> TYPE = new CustomPacketPayload.Type<>(MusicaUniversalis.location("composer_screen"));

    public static final StreamCodec<ByteBuf, ComposerScreenPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ComposerScreenPayload::index,
            ByteBufCodecs.INT,
            ComposerScreenPayload::id,
            ComposerScreenPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
