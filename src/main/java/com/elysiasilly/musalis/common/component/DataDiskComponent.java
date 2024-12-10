package com.elysiasilly.musalis.common.component;

import com.elysiasilly.musalis.common.world.resonance.Note;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataDiskComponent {

    // wtf are these for
    @Override
    public boolean equals(Object obj) {
        return obj.equals(this) || obj instanceof DataDiskComponent i && this.notes.equals(i.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.notes);
    }

    public static MapCodec<DataDiskComponent> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Note.codec.CODEC.listOf().fieldOf("ether").forGetter(i -> i.notes)
    ).apply(builder, DataDiskComponent::new));

    // please tell me this works
    public static StreamCodec<ByteBuf, DataDiskComponent> STREAM = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Codec.list(Note.codec.CODEC)), i -> i.notes,
            DataDiskComponent::new
    );

    // capacity maybe idfk we just ballin for now
    final List<Note> notes = new ArrayList<>();;

    public DataDiskComponent(List<Note> notes) {
        this.notes.addAll(notes);
    }

    public DataDiskComponent() {}

    public void insertNotes(List<Note> notes) {
        this.notes.addAll(notes);
    }

    public List<Note> extractNotes() {
        List<Note> copy = this.notes;
        this.notes.clear();
        return copy;
    }

    public List<Note> getNotes() {
        return this.notes;
    }
}
