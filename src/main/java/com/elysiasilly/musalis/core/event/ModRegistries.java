package com.elysiasilly.musalis.core.event;

import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = Musalis.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModRegistries {

    @SubscribeEvent
    static void registries(final NewRegistryEvent event) {
        event.register(Musalis.registries.ETHER);
        event.register(Musalis.registries.ETHER_CORE);
        event.register(Musalis.registries.INTERACTABLE_MANAGER);
    }

    @SubscribeEvent
    static void datapacks(final DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(MUResourceKeys.registries.NOTE, Note.codec.CODEC, Note.codec.CODEC);
        event.dataPackRegistry(MUResourceKeys.registries.LEITMOTIF, HolderLeitmotif.codec.CODEC, HolderLeitmotif.codec.CODEC);
        event.dataPackRegistry(MUResourceKeys.registries.RESONANCE, Resonance.codec.CODEC, Resonance.codec.CODEC);
    }
}
