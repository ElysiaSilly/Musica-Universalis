package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.client.resources.model.ModelResourceLocation;

public class EtherCore {

    final float stability;
    final int capacity;

    EtherStack ether;

    public EtherCore(float stability, int capacity) {
        this.stability = stability;
        this.capacity = capacity;
    }

    public ModelResourceLocation getCoreModel() {
        String string = "lol:failure";//BuiltInRegistries.ITEM.getKey(be.getCore().getItem()).toString();
        string = string.substring(string.indexOf(":") + 1);

        return ModelResourceLocation.standalone(MusicaUniversalis.location("special/" + string));
    }
}
