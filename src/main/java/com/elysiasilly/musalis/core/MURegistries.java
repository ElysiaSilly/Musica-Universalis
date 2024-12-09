package com.elysiasilly.musalis.core;

import com.elysiasilly.musalis.common.world.ether.Ether;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class MURegistries {

    public static final Registry<Ether> ETHER = new RegistryBuilder<>(MUResourceKeys.registries.ETHER)
            .defaultKey(MusicaUniversalis.location("empty")).create();

}
