package com.elysiasilly.musalis.client.render;

import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MUShaders {

    static ShaderInstance ResonanceVisualiser;
    public static ShaderInstance getResonanceVisualiser() { return ResonanceVisualiser; }

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(
                        event.getResourceProvider(),
                        MusicaUniversalis.location("resonance_visualiser"),
                        DefaultVertexFormat.POSITION),
                shaderInstance -> ResonanceVisualiser = shaderInstance
        );
    }

}
