package com.elysiasilly.musalis.client.render;

import com.elysiasilly.musalis.common.block.fluid.FluidBlockRenderer;
import com.elysiasilly.musalis.common.block.nodeBasedPipes.PipeNodeRenderer;
import com.elysiasilly.musalis.common.block.ropeblock.RopeBERenderer;
import com.elysiasilly.musalis.common.data.testing.MultiblockData;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@SuppressWarnings({"unused", "deprecation"})
@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(MUBlockEntities.FLUID_BE.get(), FluidBlockRenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.PIPE_NODE_BE.get(), PipeNodeRenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.ROPE_BE.get(), RopeBERenderer::new);


    }

    @SubscribeEvent
    private static void onRegisterDataMapTypes(RegisterDataMapTypesEvent event) {
        //event.register(FluidPropertiesData.DATA);
    }

    @SubscribeEvent
    public static void datapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(MultiblockData.REGISTRY, MultiblockData.Multiblock.CODEC, MultiblockData.Multiblock.CODEC);
    }
}
