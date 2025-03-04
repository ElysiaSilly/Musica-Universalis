package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.core.Musalis;
import net.minecraft.Util;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public abstract class EtherCore {

    final float stability;
    final int capacity;

    EtherStack ether;

    public EtherCore(float stability, int capacity) {
        this.stability = stability;
        this.capacity = capacity;
    }

    // todo
    protected abstract void process();

    public ModelResourceLocation getModel() {
        ResourceLocation location = Musalis.registries.ETHER_CORE.getKey(this);
        String string = location == null ? "unregistered_sadface" : location.toString();
        string = string.substring(string.indexOf(":") + 1);
        return ModelResourceLocation.standalone(Musalis.location("special/" + string));
    }

    public Component getName() {
        return Component.translatable(Util.makeDescriptionId("ether_core", Musalis.registries.ETHER_CORE.getKey(this)));
    };
}
