package com.elysiasilly.musalis.common.resonance;

import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

public record String(int frequency, int energy, int stability) {

    //public static final Codec<String> DIRECT_CODEC;
    public static final Codec<Holder<String>> HOLDER_CODEC;
    public static final Codec<HolderSet<String>> LIST_CODEC;

    public static final Codec<String> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.intRange(0, 255).fieldOf("frequency").forGetter(String::frequency),
            //Codec.INT.fieldOf("capacity").forGetter(S -> S.spectrum),
            Codec.intRange(0, 255).fieldOf("energy").forGetter(String::energy),
            Codec.intRange(0, 255).fieldOf("stability").forGetter((String::stability))
    ).apply(instance, String::new));

    static {
        HOLDER_CODEC = RegistryFileCodec.create(MUResourceKeys.STRING, CODEC);
        LIST_CODEC = RegistryCodecs.homogeneousList(MUResourceKeys.STRING, CODEC);
    }
}
