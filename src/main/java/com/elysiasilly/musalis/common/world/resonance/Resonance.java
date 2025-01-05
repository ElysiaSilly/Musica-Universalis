package com.elysiasilly.musalis.common.world.resonance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;

public record Resonance(Item item, Holder<HolderLeitmotif> leitmotif) {

    public static class codec{
        public static final Codec<Resonance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(i -> i.item),
                HolderLeitmotif.codec.HOLDER.fieldOf("leitmotif").forGetter(i -> i.leitmotif)
        ).apply(instance, Resonance::new));
    }

    public Item getItem() {
        return this.item;
    }

    public Leitmotif getLeitmotif() {
        return this.leitmotif.value().unpack();
    }

    public boolean match(Leitmotif leitmotif) {
        return this.leitmotif.value().unpack().equals(leitmotif);
    }
}
