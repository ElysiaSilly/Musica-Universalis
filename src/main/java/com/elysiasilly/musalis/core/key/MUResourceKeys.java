package com.elysiasilly.musalis.core.key;

import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.elysiasilly.musalis.common.world.ether.Ether;
import com.elysiasilly.musalis.common.world.ether.EtherCore;
import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.Musalis;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class MUResourceKeys {

    public static class registries{

        public static final ResourceKey<Registry<Ether>> ETHER
                = ResourceKey.createRegistryKey(Musalis.location("ether"));

        public static final ResourceKey<Registry<Note>> NOTE
                = ResourceKey.createRegistryKey(Musalis.location("note"));

        public static final ResourceKey<Registry<HolderLeitmotif>> LEITMOTIF
                = ResourceKey.createRegistryKey(Musalis.location("leitmotif"));

        public static final ResourceKey<Registry<Resonance>> RESONANCE
                = ResourceKey.createRegistryKey(Musalis.location("resonance"));

        public static final ResourceKey<Registry<EtherCore>> ETHER_CORE
                = ResourceKey.createRegistryKey(Musalis.location("ether_core"));

        public static final ResourceKey<Registry<InteractableManager<?>>> INTERACTABLE_MANAGER
                = ResourceKey.createRegistryKey(Musalis.location("interactable_manager"));
    }
}
