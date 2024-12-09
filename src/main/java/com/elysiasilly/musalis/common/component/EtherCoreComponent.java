package com.elysiasilly.musalis.common.component;

import com.elysiasilly.musalis.common.world.ether.EtherStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class EtherCoreComponent {

    // wtf are these for
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static MapCodec<EtherCoreComponent> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            EtherStack.CODEC.fieldOf("ether").forGetter(i -> i.etherStack),
            Codec.INT.fieldOf("capacity").forGetter(i -> i.etherCapacity),
            Codec.FLOAT.fieldOf("stability").forGetter(i -> i.stability)
    ).apply(builder, EtherCoreComponent::new));

    EtherStack etherStack;

    final int etherCapacity;
    final float stability;

    public EtherCoreComponent(EtherStack stack, int etherCapacity, float stability) {
        this.etherStack = stack;
        this.etherCapacity = etherCapacity;
        this.stability = stability;
    }

    public EtherCoreComponent(int etherCapacity, float stability) {
        //this.etherStack = new EtherStack(Ether.EMPTY.get());
        this.etherCapacity = etherCapacity;
        this.stability = stability;
    }

    public EtherStack getEther() {
        return this.etherStack;
    }

    public float getStability() {
        return stability;
    }

    public int getEtherCapacity() {
        return etherCapacity;
    }

    public boolean isCreative() {
        return this.etherCapacity <= 0;
    }

    public void setEther(EtherStack stack) {
        this.etherStack = stack;
    }

    public EtherStack extract(int amount) {
        if(isCreative()) return new EtherStack(this.etherStack.getEther(), 0);
        return this.etherStack.extract(amount);
    }
}
