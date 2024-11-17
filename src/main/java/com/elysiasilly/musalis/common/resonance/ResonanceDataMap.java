package com.elysiasilly.musalis.common.resonance;

import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class ResonanceDataMap {

    public static final Codec<ResonanceDataMap> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Resonance.HOLDER_CODEC.fieldOf("resonance").forGetter(CanisterData -> CanisterData.resonance)
    ).apply(instance, ResonanceDataMap::new));

    private final Holder<Resonance> resonance;

    public ResonanceDataMap(Holder<Resonance> resonance) {
        this.resonance = resonance;
    }

    public Holder<Resonance> getResonance() {
        return resonance;
    }

    public static final DataMapType<Item, ResonanceDataMap> DATAMAP = DataMapType.builder(
            MusicaUniversalis.resource("resonance_definition"),
            Registries.ITEM,
            CODEC
    ).build();
}
