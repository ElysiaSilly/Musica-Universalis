package com.elysiasilly.musalis.common.world.astrom;

import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;

public class AstromMass {

    List<Note> contents = new ArrayList<>();

    static class codec {
       public static final Codec<AstromMass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
               Note.codec.CODEC.listOf().fieldOf("notes").forGetter(i -> i.contents)
       ).apply(instance, AstromMass::new));
    }

    public AstromMass() {}

    private AstromMass(List<Note> notes) {
        this.contents.addAll(notes);
    }

    public void dissolve(Leitmotif leitmotif) {
        this.contents.addAll(leitmotif.dissolve());
    }



    public void tick() {}
}
