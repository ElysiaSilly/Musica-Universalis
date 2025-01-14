package com.elysiasilly.musalis.mixin.client;

import com.elysiasilly.musalis.client.render.IDepthRenderTarget;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.pipeline.TextureTarget;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin implements IDepthRenderTarget {


    @Shadow @Final private Minecraft minecraft;

    @Unique private com.mojang.blaze3d.pipeline.RenderTarget depthRenderTarget;

    @Unique // ??
    private void musalis$copyDepthBuffer() {
        if(depthRenderTarget == null) {
            depthRenderTarget = new TextureTarget(this.minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight(), true, Minecraft.ON_OSX);
        }
        com.mojang.blaze3d.pipeline.RenderTarget main = minecraft.getMainRenderTarget();
        if(depthRenderTarget.width != main.width || depthRenderTarget.height != main.height) {
            depthRenderTarget.resize(main.width, main.height, false);
        }
        if(main.isStencilEnabled()) {
            depthRenderTarget.enableStencil();
        }
        else if(depthRenderTarget.isStencilEnabled()) {
            depthRenderTarget.destroyBuffers();
            depthRenderTarget = new TextureTarget(main.width, main.height, true, Minecraft.ON_OSX);
        }

        depthRenderTarget.setClearColor(0, 0, 0, 0);
        depthRenderTarget.copyDepthFrom(main);
        main.bindWrite(false);
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PostChain;process(F)V", ordinal = 1))
    private void musalis$renderLevelPreFabulous(DeltaTracker deltaTracker, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f frustumMatrix, Matrix4f projectionMatrix, CallbackInfo ci) {
        //because of COURSE it does Minecraft nukes the depth buffer in fabulous graphics before rendering the "transparency" post shader.
        //This copies the Depth buffer before it's nuked to a new render target
        //only done on fabulous graphics
        //if(transparencyChain != null) {
        musalis$copyDepthBuffer();
        //}

    }

    @Override
    public RenderTarget getDepthRenderTarget() {
        return depthRenderTarget;
    }
}
