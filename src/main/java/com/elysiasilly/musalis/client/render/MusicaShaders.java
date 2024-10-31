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

public class MusicaShaders {

    private static ShaderInstance testingShader;

    public static ShaderInstance getTestingShader() {
        return testingShader;
    }

    @SubscribeEvent
    public static void onRegisterShadersEvent(RegisterShadersEvent e) throws IOException {
        e.registerShader(new ShaderInstance(
                        e.getResourceProvider(),
                        ResourceLocation.fromNamespaceAndPath(MusicaUniversalis.MODID, "testing"),
                        DefaultVertexFormat.POSITION),
                shaderInstance -> testingShader = shaderInstance
        );
    }

}
