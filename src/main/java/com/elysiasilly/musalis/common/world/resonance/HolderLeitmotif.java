package com.elysiasilly.musalis.common.world.resonance;

import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

import java.util.ArrayList;
import java.util.List;

public class HolderLeitmotif {

    // scary
    //final Either<List<Holder<Leitmotif>>, List<Holder<Note>>> packed;
    final List<Holder<Note>> notesPacked;
    final List<Holder<HolderLeitmotif>> leitmotifsPacked;

    public static class codec{ // todo : bonds for recursive leitmotifs
        public static final Codec<Holder<HolderLeitmotif>> HOLDER;

        public static final Codec<HolderLeitmotif> CODEC = Codec.recursive("leitmotif", (ins) -> RecordCodecBuilder.create(instance -> instance.group(
                codec.HOLDER.listOf().fieldOf("leitmotifs").forGetter(i -> i.leitmotifsPacked),
                Note.codec.HOLDER.listOf().fieldOf("notes").forGetter(i -> i.notesPacked)

                //Codec.xor(
                //        codec.HOLDER.listOf(),
                //        Note.codec.HOLDER.listOf()
                //).fieldOf("map").forGetter(i -> i.packed)
        ).apply(instance, HolderLeitmotif::new)));

        static {HOLDER = RegistryFileCodec.create(MUResourceKeys.registries.LEITMOTIF, CODEC);}
    }

    public HolderLeitmotif(List<Holder<HolderLeitmotif>> leitmotifs, List<Holder<Note>> notes) {//Either<List<Holder<Leitmotif>>, List<Holder<Note>>> packed) {
        this.notesPacked = notes;
        this.leitmotifsPacked = leitmotifs;
    }

    public Leitmotif unpack() {
        if(!this.notesPacked.isEmpty()) {
            List<Note> unpacked = new ArrayList<>();
            for(Holder<Note> note : this.notesPacked) unpacked.add(note.value());
            return new Leitmotif(unpacked, List.of());
        }
        if(!this.leitmotifsPacked.isEmpty()) {
            List<Leitmotif> unpacked = new ArrayList<>();
            for(Holder<HolderLeitmotif> leitmotif : this.leitmotifsPacked) unpacked.add(leitmotif.value().unpack());
            return new Leitmotif(List.of(), unpacked);
        }
        return new Leitmotif(List.of(), List.of());
    }
}
