package com.elysiasilly.musalis.core.key;

import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.elysiasilly.musalis.common.world.ether.Ether;
import com.elysiasilly.musalis.common.world.ether.EtherCore;
import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class MUResourceKeys {

    public static class registries{

        public static final ResourceKey<Registry<Ether>> ETHER
                = ResourceKey.createRegistryKey(MusicaUniversalis.location("ether"));

        public static final ResourceKey<Registry<Note>> NOTE
                = ResourceKey.createRegistryKey(MusicaUniversalis.location("note"));

        public static final ResourceKey<Registry<HolderLeitmotif>> LEITMOTIF
                = ResourceKey.createRegistryKey(MusicaUniversalis.location("leitmotif"));

        public static final ResourceKey<Registry<Resonance>> RESONANCE
                = ResourceKey.createRegistryKey(MusicaUniversalis.location("resonance"));

        public static final ResourceKey<Registry<EtherCore>> ETHER_CORE
                = ResourceKey.createRegistryKey(MusicaUniversalis.location("ether_core"));

        public static final ResourceKey<Registry<InteractableManager<?>>> INTERACTABLE_MANAGER
                = ResourceKey.createRegistryKey(MusicaUniversalis.location("interactable_manager"));
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
