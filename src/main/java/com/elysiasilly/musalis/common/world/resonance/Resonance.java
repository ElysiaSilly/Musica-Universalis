package com.elysiasilly.musalis.common.world.resonance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public class Resonance {

    // todo : bonds

    final Item item;
    final Holder<Leitmotif> packedLeitmotif;
    final Leitmotif leitmotif;

    public static class codec{
        public static final Codec<Resonance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(i -> i.item),
                Leitmotif.codec.HOLDER.fieldOf("leitmotif").forGetter(i -> i.packedLeitmotif)
        ).apply(instance, Resonance::new));
    }

    public Resonance(Item item, Holder<Leitmotif> packedLeitmotif) {
        this.item = item;
        this.packedLeitmotif = packedLeitmotif;
        this.leitmotif = packedLeitmotif.value();
    }

    public Item getItem() {
        return this.item;
    }

    public Leitmotif getLeitmotif() {
        return leitmotif;
    }
}
