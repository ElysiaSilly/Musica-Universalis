package com.elysiasilly.musalis.common.world.astrom;

import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;

import java.util.ArrayList;
import java.util.List;

public class AstromMass {

    List<Note> contents = new ArrayList<>();

    public void dissolve(Leitmotif leitmotif) {
        this.contents.addAll(leitmotif.dissolve());
    }
}
