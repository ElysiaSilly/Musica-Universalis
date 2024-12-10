package com.elysiasilly.musalis.common.world.resonance;

import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

import java.util.ArrayList;
import java.util.List;

public class Leitmotif {

    final List<Holder<Note>> notesPacked;
    final List<Note> notes = new ArrayList<>();

    public static class codec{
        public static final Codec<Leitmotif> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.list(Note.codec.HOLDER).fieldOf("notes").forGetter(i -> i.notesPacked)
        ).apply(instance, Leitmotif::new));

        public static final RegistryFileCodec<Leitmotif> HOLDER = RegistryFileCodec.create(MUResourceKeys.registries.LEITMOTIF, CODEC);
    }

    public Leitmotif(List<Holder<Note>> notesPacked) {
        this.notesPacked = notesPacked;
        for(Holder<Note> note : notesPacked) {
            this.notes.add(note.value());
        }
    }

    public List<Note> getNotes() {
        return notes;
    }

    /// whether input leitmotif contains the same notes as current leitmotif
    public boolean compareLeitmotifs() {
        return false;
    }

    // made up of notes, or other leitmotifs bonded together
}
