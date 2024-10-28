package com.elysiasilly.musalis.client.render;

import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class MusicaRenderTypes {

    protected static final RenderStateShard.TransparencyStateShard ADDITIVE_TRANSPARENCY = new RenderStateShard.TransparencyStateShard(
            "additive_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    }, () -> {
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
    });

    protected static final RenderStateShard.ShaderStateShard TESTING_RENDERTYPE = new RenderStateShard.ShaderStateShard(MusicaShaders::getTestingShader);

    private static final Function<ResourceLocation, RenderType> TESTING = Util.memoize((resourceLocation) -> {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(TESTING_RENDERTYPE)
                //.setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false))
                .setTextureState(BLOCK_SHEET)
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
        return RenderType.create("testing", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, compositeState);
    });

    public static RenderType getTestingShader() {
        return TESTING.apply(TextureAtlas.LOCATION_PARTICLES);
    }
}
