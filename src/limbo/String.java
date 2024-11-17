package com.elysiasilly.musalis.common.resonance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class String {

    public static final Codec<String> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.intRange(0, 255).fieldOf("frequency").forGetter(S -> S.frequency),
            //Codec.INT.fieldOf("capacity").forGetter(S -> S.spectrum),
            Codec.intRange(0, 255).fieldOf("energy").forGetter(S -> S.energy),
            Codec.intRange(0, 255).fieldOf("stability").forGetter((S -> S.stability))
    ).apply(instance, String::new));

    private final int frequency;
    //private final RGBA spectrum;
    private final int energy;
    private final int stability;

    public String(int frequency, int energy, int stability) {
        this.frequency = frequency;
        //this.spectrum = spectrum;
        this.energy = energy;
        this.stability = stability;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getEnergy() {
        return energy;
    }

    public float getStability() {
        return stability;
    }

    public String copy() {
        return this;
    }
}
