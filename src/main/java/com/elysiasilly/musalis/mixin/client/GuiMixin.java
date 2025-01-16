package com.elysiasilly.musalis.mixin.client;

import com.elysiasilly.babel.client.screen.IModifyCameraScreen;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Shadow @Final private Minecraft minecraft;

    @Inject(
            method = "render",
            at = @At(value = "HEAD"),
            cancellable = true
    )

    private void musalis$render(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo ci) {
        if(this.minecraft.screen instanceof IModifyCameraScreen screen) if(screen.hideElements()) ci.cancel();
    }
}
