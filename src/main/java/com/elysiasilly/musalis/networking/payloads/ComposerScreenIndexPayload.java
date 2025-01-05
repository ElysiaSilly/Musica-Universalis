package com.elysiasilly.musalis.networking.payloads;

import com.elysiasilly.musalis.core.Musalis;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ComposerScreenIndexPayload(int index) implements CustomPacketPayload {

    public static final Type<ComposerScreenIndexPayload> TYPE = new Type<>(Musalis.location("composer_screen_index"));

    public static final StreamCodec<ByteBuf, ComposerScreenIndexPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ComposerScreenIndexPayload::index,
            ComposerScreenIndexPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
