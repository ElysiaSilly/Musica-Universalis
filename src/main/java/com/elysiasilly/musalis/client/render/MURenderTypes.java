package com.elysiasilly.musalis.client.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

import static net.minecraft.client.renderer.RenderStateShard.*;

public class MURenderTypes {

    protected static final TransparencyStateShard ADDITIVE_TRANSPARENCY = new TransparencyStateShard(
            "additive_transparency", () -> {
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
    }, () -> {
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
    });

    protected static final ShaderStateShard RESONANCE_VISUALISER = new ShaderStateShard(MUShaders::getResonanceVisualiser);

    private static final Function<ResourceLocation, RenderType> TESTING = Util.memoize((resourceLocation) -> {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder()
                .setShaderState(RESONANCE_VISUALISER)
                .setTextureState(MultiTextureStateShard.builder().add(TheEndPortalRenderer.END_SKY_LOCATION, false, false).build())
                .setTextureState(BLOCK_SHEET)
                .setTransparencyState(ADDITIVE_TRANSPARENCY)
                .setCullState(NO_CULL)
                .setLightmapState(LIGHTMAP)
                .setOverlayState(OVERLAY)
                .createCompositeState(true);
        return RenderType.create("resonance_visualiser", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 256, false, false, compositeState);
    });

    public static RenderType getTestingShader() {
        return TESTING.apply(TextureAtlas.LOCATION_PARTICLES);
    }
}
