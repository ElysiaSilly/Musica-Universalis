package com.elysiasilly.musalis.core.key;

import com.elysiasilly.musalis.common.world.ether.Ether;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class MUResourceKeys {

    public static class registries{

        public static final ResourceKey<Registry<Ether>> ETHER
                = ResourceKey.createRegistryKey(MusicaUniversalis.location("ether"));

    }

    /*
    public static final ResourceKey<Registry<String>> STRING
            = ResourceKey.createRegistryKey(MusicaUniversalis.location("string"));

    public static final ResourceKey<Registry<Resonance>> RESONANCE
            = ResourceKey.createRegistryKey(MusicaUniversalis.location("resonance"));

    public static final ResourceKey<Registry<FluidProperties>> FLUID_PROPERTIES
            = ResourceKey.createRegistryKey(MusicaUniversalis.location("fluid_properties"));

    public static final ResourceKey<Registry<Effect>> EFFECT
            = ResourceKey.createRegistryKey(MusicaUniversalis.location("effect"));

     */


}
