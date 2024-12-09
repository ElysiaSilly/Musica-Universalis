package com.elysiasilly.musalis.core.registry.event;

import com.elysiasilly.musalis.core.MURegistries;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.NewRegistryEvent;

@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModRegistries {

    /*
    @SubscribeEvent
    private static void onDataPackRegistryEvent(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(MUResourceKeys.STRING, String.CODEC);
        event.dataPackRegistry(MUResourceKeys.RESONANCE, Resonance.CODEC);

        // todo : this shouldnt be a datapack registry (prolly a datamap)
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
}
