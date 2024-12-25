package com.elysiasilly.musalis.networking.payloads;

import com.elysiasilly.musalis.common.block.be.creative.CrResonanceComposerBE;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.StringRepresentable;

public record ComposerScreenModeSetterPayload(CrResonanceComposerBE.modes mode) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ComposerScreenModeSetterPayload> TYPE = new CustomPacketPayload.Type<>(MusicaUniversalis.location("composer_screen_mode_setter"));

    public static final StreamCodec<ByteBuf, ComposerScreenModeSetterPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(StringRepresentable.fromEnum(CrResonanceComposerBE.modes::values)),
            ComposerScreenModeSetterPayload::mode,
            ComposerScreenModeSetterPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
