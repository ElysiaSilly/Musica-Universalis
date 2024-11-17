package com.elysiasilly.musalis.client.render;

import com.elysiasilly.musalis.common.block.fluid.FluidBlockRenderer;
import com.elysiasilly.musalis.common.block.nodeBasedPipes.PipeNodeRenderer;
import com.elysiasilly.musalis.common.block.ropeblock.RopeBERenderer;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@SuppressWarnings({"unused", "deprecation"})
@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ClientEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(MUBlockEntities.FLUID_BE.get(), FluidBlockRenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.PIPE_NODE_BE.get(), PipeNodeRenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.ROPE_BE.get(), RopeBERenderer::new);
    }
}
