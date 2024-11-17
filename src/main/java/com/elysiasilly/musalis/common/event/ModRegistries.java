package com.elysiasilly.musalis.common.event;

import com.elysiasilly.musalis.common.fluid.FluidProperties;
import com.elysiasilly.musalis.common.resonance.Resonance;
import com.elysiasilly.musalis.common.resonance.ResonanceDataMap;
import com.elysiasilly.musalis.common.resonance.String;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModRegistries {

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
}
