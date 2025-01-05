package com.elysiasilly.musalis.common.component;

import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record DataDiskComponent(Leitmotif leitmotif) {

    public static final DataDiskComponent EMPTY = new DataDiskComponent(new Leitmotif(Leitmotif.Type.RECURSIVE));

    public static final MapCodec<DataDiskComponent> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Leitmotif.C.CODEC.fieldOf("leitmotif").forGetter(i -> i.leitmotif)
    ).apply(builder, DataDiskComponent::new));

    public static final StreamCodec<ByteBuf, DataDiskComponent> STREAM = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Leitmotif.C.CODEC), i -> i.leitmotif,
            DataDiskComponent::new
    );


    public DataDiskComponent setLeitmotif(Leitmotif leitmotif) {
        return new DataDiskComponent(leitmotif);
    }

    public Leitmotif getLeitmotif() {
        return this.leitmotif;
    }
}
