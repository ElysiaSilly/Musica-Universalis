package com.elysiasilly.musalis.client.render;

import com.elysiasilly.musalis.core.Musalis;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

import static net.minecraft.client.renderer.RenderStateShard.*;

@EventBusSubscriber(modid = Musalis.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MUCoreShaders {

    @SubscribeEvent
    static void register(RegisterShadersEvent event) throws IOException {
        resonanceShader.instance(event);
        depthShader.instance(event);
    }

    public static class resonanceShader {
        static final String NAME = "resonance_visualiser";

        static final ShaderStateShard SHARD = new ShaderStateShard(resonanceShader::instance);

        static final RenderType.CompositeState COMPOSITE = RenderType.CompositeState.builder()
                    .setShaderState(SHARD)
                    .setTransparencyState(ADDITIVE_TRANSPARENCY)
                    .setDepthTestState(LEQUAL_DEPTH_TEST)
                    .setWriteMaskState(COLOR_WRITE)
                    .setCullState(NO_CULL)
                    .createCompositeState(false);

        static final RenderType RENDERTYPE = RenderType.create(NAME, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, COMPOSITE);

        public static RenderType renderType() {
            return RENDERTYPE;
        }

        static ShaderInstance INSTANCE;

        public static ShaderInstance instance() {
            return INSTANCE;
        }

        static void instance(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(
                            event.getResourceProvider(),
                            Musalis.location(NAME),
                            DefaultVertexFormat.POSITION_COLOR),
                    shaderInstance -> INSTANCE = shaderInstance
            );
        }
    }

    public static class depthShader {
        static final String NAME = "depth";

        static final ShaderStateShard SHARD = new ShaderStateShard(depthShader::instance);

        static final RenderType.CompositeState COMPOSITE = RenderType.CompositeState.builder()
                .setShaderState(SHARD)
                .setDepthTestState(LEQUAL_DEPTH_TEST)
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setLightmapState(LIGHTMAP)
                .setCullState(NO_CULL)
                .setWriteMaskState(COLOR_WRITE)
                .createCompositeState(true);

        static final RenderType RENDERTYPE = RenderType.create(NAME, DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, COMPOSITE);

        public static RenderType get() {
            return RENDERTYPE;
        }

        static ShaderInstance INSTANCE;

        public static ShaderInstance instance() {
            return INSTANCE;
        }

        static void instance(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(
                            event.getResourceProvider(),
                            Musalis.location(NAME),
                            DefaultVertexFormat.POSITION_COLOR),
                    shaderInstance -> INSTANCE = shaderInstance
            );
        }
    }
}
