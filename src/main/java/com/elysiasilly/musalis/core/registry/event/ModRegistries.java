package com.elysiasilly.musalis.core.registry.event;

import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.MURegistries;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModRegistries {

    /*
    @SubscribeEvent
    private static void onDataPackRegistryEvent(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(MUResourceKeys.STRING, String.CODEC);
        event.dataPackRegistry(MUResourceKeys.RESONANCE, Resonance.CODEC);

        // todo : this shouldnt be a datapack registry (prolly a datamap)..reloadlistener?
        event.dataPackRegistry(MUResourceKeys.FLUID_PROPERTIES, FluidProperties.CODEC);
    }

    @SubscribeEvent
    private static void onRegisterDataMapTypes(RegisterDataMapTypesEvent event) {
        event.register(ResonanceDataMap.DATAMAP);
    }

    @SubscribeEvent
    static void onNewRegistryEvent(NewRegistryEvent event) {
        event.register(MusicaUniversalis.EFFECT);
    }
     */

    @SubscribeEvent
    static void onNewRegistry(NewRegistryEvent event) {
        event.register(MURegistries.ETHER);
    }

    @SubscribeEvent
    private static void onDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(MUResourceKeys.registries.NOTE, Note.codec.CODEC, Note.codec.CODEC);
        event.dataPackRegistry(MUResourceKeys.registries.LEITMOTIF, HolderLeitmotif.codec.CODEC, HolderLeitmotif.codec.CODEC);
        event.dataPackRegistry(MUResourceKeys.registries.RESONANCE, Resonance.codec.CODEC, Resonance.codec.CODEC);
    }

    /*
    @SubscribeEvent
    private static void onDataPackRegistry(AddReloadListenerEvent event) {
        event.addListener();
    }

     */
}
