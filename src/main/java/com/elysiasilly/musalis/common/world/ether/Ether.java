package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.core.Musalis;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;

public abstract class Ether {

    /// runs every tick when dissipating
    public abstract void passiveDissipate();

    /// runs once when aggravated
    public abstract void volatileDissipate();

    public Component getName() {
        return Component.translatable(Util.makeDescriptionId("ether", Musalis.registries.ETHER.getKey(this)));
    }
}
