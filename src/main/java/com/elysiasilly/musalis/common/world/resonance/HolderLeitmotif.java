package com.elysiasilly.musalis.common.world.resonance;

import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

import java.util.List;

public class HolderLeitmotif {

    // scary
    final Either<List<Holder<Note>>, List<Holder<HolderLeitmotif>>> leitmotif;

    public static class codec {
        public static Codec<Holder<HolderLeitmotif>> HOLDER;

        public static final Codec<HolderLeitmotif> CODEC = Codec.recursive("leitmotif", (ins) -> RecordCodecBuilder.create(instance -> instance.group(
                Codec.xor(
                        Note.codec.HOLDER.listOf().fieldOf("compound").codec(),
                        HOLDER.listOf().fieldOf("recursive").codec()
                ).fieldOf("leitmotif").forGetter(i -> i.leitmotif)
        ).apply(instance, HolderLeitmotif::new)));

        static { HOLDER = RegistryFileCodec.create(MUResourceKeys.registries.LEITMOTIF, CODEC); }
    }

    private HolderLeitmotif(Either<List<Holder<Note>>, List<Holder<HolderLeitmotif>>> leitmotif) {
        this.leitmotif = leitmotif;
    }

    public Leitmotif.Type type() {
        return this.leitmotif.left().isPresent() ? Leitmotif.Type.COMPOUND : this.leitmotif.right().isPresent() ? Leitmotif.Type.RECURSIVE : Leitmotif.Type.INVALID;
    }

    public boolean isRecursive() {
        return type().equals(Leitmotif.Type.RECURSIVE);
    }

    public boolean isCompound() {
        return type().equals(Leitmotif.Type.COMPOUND);
    }

    public Leitmotif unpack() {

        Leitmotif leitmotif = new Leitmotif(type());

        if(isCompound()) {
            for(Holder<Note> holder : this.leitmotif.left().get()) {
                leitmotif.add(holder.value());
            }
        }

        if(isRecursive()) {
            for(Holder<HolderLeitmotif> holder : this.leitmotif.right().get()) {
                leitmotif.add(holder.value().unpack());
            }
        }

        return leitmotif;
    }
}
