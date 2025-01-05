package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.common.world.resonance.Bond;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EtherStack {

    private Bond bond;
    public final List<Leitmotif> leitmotifs = new ArrayList<>();

    public enum Purity { PURE, DILUTED }

    public static class codec {
        public static final Codec<EtherStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.list(Leitmotif.C.CODEC).fieldOf("leitmotif").forGetter(i -> i.leitmotifs)
        ).apply(instance, EtherStack::new));
    }

    public EtherStack(List<Leitmotif> leitmotifs) {
        this.leitmotifs.addAll(leitmotifs);
    }

    public Purity getPurity() {
        return null;
    }

    public List<Ether> ether() {
        return null;

    }

    public Map<Ether, Integer> percentageMap() {
        return null;

    }

    public Map<Ether, Integer> volumeMap() {
        return null;

    }

    public void add(List<Leitmotif> leitmotifs) {
        this.leitmotifs.addAll(leitmotifs);
    }

    public void add(Leitmotif leitmotif) {
        this.leitmotifs.add(leitmotif);
    }

    public List<Leitmotif> getContents() {
        return this.leitmotifs;
    }

    public EtherStack copy() {
        return new EtherStack(this.leitmotifs);
    }

    public int getVolume() {
        return 0;
    }

    public void clean(int percentageCeiling) {

    }

    public EtherStack subtract(Ether ether) {
        return null;

    }

    public EtherStack subtract(Ether ether, int amount) {
        return null;

    }

    public void passiveDissipate() {
        for(Ether ether : ether()) {
            ether.passiveDissipate();
        }
    }

    public void volatileDissipate() {
        for(Ether ether : ether()) {
            ether.volatileDissipate();
        }
    }
}
