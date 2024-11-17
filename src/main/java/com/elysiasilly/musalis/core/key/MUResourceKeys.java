package com.elysiasilly.musalis.core.key;

import com.elysiasilly.musalis.common.fluid.FluidProperties;
import com.elysiasilly.musalis.common.resonance.Effect;
import com.elysiasilly.musalis.common.resonance.Resonance;
import com.elysiasilly.musalis.common.resonance.String;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class MUResourceKeys {

    public static final ResourceKey<Registry<String>> STRING
            = ResourceKey.createRegistryKey(MusicaUniversalis.resource("string"));

    public static final ResourceKey<Registry<Resonance>> RESONANCE
            = ResourceKey.createRegistryKey(MusicaUniversalis.resource("resonance"));

    public static final ResourceKey<Registry<FluidProperties>> FLUID_PROPERTIES
            = ResourceKey.createRegistryKey(MusicaUniversalis.resource("fluid_properties"));

    public static final ResourceKey<Registry<Effect>> EFFECT
            = ResourceKey.createRegistryKey(MusicaUniversalis.resource("effect"));
}
