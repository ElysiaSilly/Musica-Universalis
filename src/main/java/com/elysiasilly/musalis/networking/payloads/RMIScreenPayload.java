package com.elysiasilly.musalis.networking.payloads;

import com.elysiasilly.musalis.core.Musalis;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record RMIScreenPayload(int id, int extra) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<RMIScreenPayload> TYPE = new CustomPacketPayload.Type<>(Musalis.location("mri_screen"));

    public static final StreamCodec<ByteBuf, RMIScreenPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            RMIScreenPayload::id,
            ByteBufCodecs.INT,
            RMIScreenPayload::extra,
            RMIScreenPayload::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
