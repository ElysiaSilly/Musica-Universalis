package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.core.MURegistries;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class EtherStack {

    public static final Codec<EtherStack> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            MURegistries.ETHER.byNameCodec().fieldOf("ether").forGetter(i -> i.ether),
            Codec.INT.fieldOf("amount").forGetter(i -> i.amount)
    ).apply(instance, EtherStack::new));

    final Ether ether;
    int amount;

    public EtherStack(Ether ether, int amount) {
        this.ether = ether;
        this.amount = amount;
    }

    public EtherStack(Ether ether) {
        this.ether = ether;
        this.amount = 1;
    }

    public int getAmount() {
        return this.amount;
    }

    public Ether getEther() {
        return this.ether;
    }

    public EtherStack copy() {
        return new EtherStack(this.ether, this.amount);
    }

    /// returns the extracted stack
    public EtherStack extract(int amount) {
        if(this.amount == 0) return new EtherStack(this.ether, 0);
        if(this.amount < amount) {
            int possibleAmount = amount - this.amount;
            this.amount = possibleAmount;
            return new EtherStack(this.ether, possibleAmount);
        } else {
            this.amount = this.amount - amount;
            return new EtherStack(this.ether, amount);
        }
    }
}
