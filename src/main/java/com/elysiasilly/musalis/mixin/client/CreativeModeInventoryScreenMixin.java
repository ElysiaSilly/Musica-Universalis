package com.elysiasilly.musalis.mixin.client;

import com.elysiasilly.musalis.common.MUCreativeTab;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.client.gui.CreativeTabsScreenPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin {

    @Shadow private static CreativeModeTab selectedTab;

    @Shadow protected abstract void renderTabButton(GuiGraphics guiGraphics, CreativeModeTab creativeModeTab);

    @Shadow public abstract CreativeTabsScreenPage getCurrentPage();

    @Shadow private CreativeTabsScreenPage currentPage;

    @Shadow protected abstract void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY);

    @Shadow protected abstract void selectTab(CreativeModeTab tab);

    @Inject(
            method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/EffectRenderingInventoryScreen;render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"),
            cancellable = true
    )

    // todo

    private void musalis$render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
        if(selectedTab instanceof MUCreativeTab) {
            //CreativeTab tab = new CreativeTab();
            //tab.tab = selectedTab;
            //Minecraft.getInstance().setScreen(tab);
        }
    }
}
