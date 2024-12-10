package com.elysiasilly.musalis.common.component;

import com.elysiasilly.musalis.common.world.resonance.Note;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;

public class DataDiskComponent {

    // wtf are these for
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static MapCodec<DataDiskComponent> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            Note.codec.CODEC.listOf().fieldOf("ether").forGetter(i -> i.notes)
    ).apply(builder, DataDiskComponent::new));

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
