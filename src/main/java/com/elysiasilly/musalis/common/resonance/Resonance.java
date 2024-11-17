package com.elysiasilly.musalis.common.resonance;

import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.RegistryFileCodec;

import java.util.ArrayList;
import java.util.List;

public class Resonance { // todo : bond

    public static final Codec<Holder<Resonance>> HOLDER_CODEC;

    public static final Codec<Resonance> CODEC = Codec.recursive("resonance", (ins) ->
            RecordCodecBuilder.create((instance) -> instance.group(
                    String.HOLDER_CODEC.listOf().fieldOf("string").forGetter(i -> i.strings),
                    Resonance.HOLDER_CODEC.listOf().fieldOf("resonance").forGetter(i -> i.resonance)
            ).apply(instance, Resonance::new))
    );

    static {
        HOLDER_CODEC = RegistryFileCodec.create(MUResourceKeys.RESONANCE, CODEC);
        //LIST_CODEC = RegistryCodecs.homogeneousList(MUResourceKeys.STRING, CODEC);
    }

    private final List<Holder<String>> strings = new ArrayList<>();
    private final List<Holder<Resonance>> resonance = new ArrayList<>();

    public Resonance(List<Holder<String>> strings, List<Holder<Resonance>> resonance) {
        this.strings.addAll(strings);
        this.resonance.addAll(resonance);
    }

    public List<Holder<String>> crawlStrings() {

        List<Holder<String>> strings = new ArrayList<>(this.strings);

        for(Holder<Resonance> resonance : this.resonance) {
            strings.addAll(resonance.value().crawlStrings());
        }

        return strings;
    }

    public List<Holder<String>> getStrings() {
        return this.strings;
    }

    public List<Holder<Resonance>> getResonance() {
        return this.resonance;
    }
}
