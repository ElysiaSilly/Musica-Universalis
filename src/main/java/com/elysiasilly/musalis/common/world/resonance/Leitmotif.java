package com.elysiasilly.musalis.common.world.resonance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Leitmotif {

    // todo : figure out a smarter way to handle this but leitmotifs should always either only be recursive or made of notes
    final List<Note> notes;
    final List<Leitmotif> leitmotifs;
    boolean isRecursive;

    public static class codec{
        public static final Codec<Leitmotif> CODEC = Codec.recursive("leitmotif", (ins) -> RecordCodecBuilder.create(instance -> instance.group(
                Note.codec.CODEC.listOf().fieldOf("notes").forGetter(i -> i.notes),
                Leitmotif.codec.CODEC.listOf().fieldOf("leitmotifs").forGetter(i -> i.leitmotifs)
                ).apply(instance, Leitmotif::new)));
    }

    public Leitmotif(List<Note> notes, List<Leitmotif> leitmotifs) {
        this.notes = notes;
        this.leitmotifs = leitmotifs;
        if(!this.leitmotifs.isEmpty()) isRecursive = true;
    }

    public List<Leitmotif> getLeitmotifs() {
        return this.leitmotifs;
    }

    public List<Note> getNotes() {
        return this.notes;
    }

    public boolean isRecursive() {
        return this.isRecursive;
    }

    /// im not sure how good this works lmao
    /// whether input leitmotif contains the same notes as current leitmotif
    public boolean compareLeitmotifs(List<Note> notes) {
        if(notes.isEmpty()) return false;

        List<Note> temp = new ArrayList<>(List.copyOf(notes));

        for(Note note : this.notes) {
            if(temp.contains(note)) {
                temp.remove(note);
            } else {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Leitmotif other) return other.hashCode() == this.hashCode();
        return false;
    }

    @Override
    public int hashCode() {
        if(!this.notes.isEmpty()) return Objects.hash(notes.toArray());
        if(!this.leitmotifs.isEmpty()) return Objects.hash(leitmotifs.toArray());
        return 0;
    }
}
