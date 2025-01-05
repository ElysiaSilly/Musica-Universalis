package com.elysiasilly.musalis.common.world.resonance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Leitmotif {

    /// todo : figure out a smarter way to handle this
    /// leitmotifs must always either be compound or recursive, they cant be both

    public enum Type { COMPOUND, RECURSIVE, INVALID }

    private final List<Note> notes = new ArrayList<>(); /// compound
    private final List<Leitmotif> leitmotifs = new ArrayList<>(); /// recursive

    final Type TYPE;

    public static class C {
        public static final Codec<Leitmotif> CODEC = Codec.recursive("leitmotif", (ins) -> RecordCodecBuilder.create(instance -> instance.group(
                Note.codec.CODEC.listOf().fieldOf("notes").forGetter(i -> i.notes),
                Leitmotif.C.CODEC.listOf().fieldOf("leitmotifs").forGetter(i -> i.leitmotifs)
                ).apply(instance, Leitmotif::new))
        );
    }


    //public Leitmotif(List<Note> notes) {
    //    this.notes = notes;
    //    this.leitmotifs = List.of();
    //    this.TYPE = type();
    //}

    public Leitmotif(List<Leitmotif> leitmotifs) {
        this.leitmotifs.addAll(leitmotifs);
        this.TYPE = type();
    }

    private Leitmotif(List<Note> notes, List<Leitmotif> leitmotifs) {
        this.notes.addAll(notes);
        this.leitmotifs.addAll(leitmotifs);
        this.TYPE = type();
    }

    public Leitmotif(Type type) {
        this.TYPE = type;
    }

    public List<Leitmotif> getLeitmotifs() {
        return this.leitmotifs;
    }

    public List<Note> getNotes() {
        return this.notes;
    }

    public List<Note> dissolve() {
        List<Note> notes = new ArrayList<>();

        if(isCompound()) {
            notes.addAll(this.notes);
        }

        if(isRecursive()) {
            for(Leitmotif leitmotif : this.leitmotifs) {
                notes.addAll(leitmotif.dissolve());
            }
        }

        return notes;
    }

    public void add(Note...notes) {
        if(isCompound()) this.notes.addAll(List.of(notes));
    }

    public void add(Leitmotif...leitmotifs) {
        if(isRecursive()) this.leitmotifs.addAll(List.of(leitmotifs));
    }

    private Type type() {
        return this.notes.isEmpty() ? this.leitmotifs.isEmpty() ? Type.INVALID : Type.RECURSIVE : Type.COMPOUND;
    }

    public boolean isRecursive() {
        return type().equals(Type.RECURSIVE) && this.TYPE.equals(Type.RECURSIVE);
    }

    public boolean isCompound() {
        return type().equals(Type.COMPOUND) && this.TYPE.equals(Type.COMPOUND);
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
        return obj instanceof Leitmotif leitmotif && leitmotif.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        return isCompound() ? Objects.hash(notes.toArray()) : isRecursive() ? Objects.hash(leitmotifs.toArray()) : 0;
    }


}
