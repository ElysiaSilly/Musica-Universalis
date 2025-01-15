package com.elysiasilly.musalis.mixin.client;

import com.elysiasilly.musalis.client.screen.CreativeTab;
import com.elysiasilly.musalis.common.MUCreativeTab;
import com.elysiasilly.musalis.core.Musalis;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.client.gui.CreativeTabsScreenPage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collection;

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
            CreativeTab tab = new CreativeTab();
            tab.tab = selectedTab;
            Minecraft.getInstance().setScreen(tab);

            /*
            guiGraphics.fillGradient(0, 0, 10000, 10000, -1072689136, -804253680);


            this.renderTabButton(guiGraphics, selectedTab);

            Collection<ItemStack> items = selectedTab.getDisplayItems();

            PoseStack pose = guiGraphics.pose();

            int X = 0;
            int Y = 0;
            for(ItemStack stack : items) {
                if(X % 9 == 0) {
                    Y++;
                    X = 0;
                }

                pose.pushPose();

                pose.rotateAround(Axis.YP.rotationDegrees(5 * X), 28 + (20 * X), 28 + (20 * Y), 0);
                pose.rotateAround(Axis.XP.rotationDegrees(-5), 28 + (20 * X), 28 + (20 * Y), 0);

                guiGraphics.renderItem(stack, 20 + (20 * X), 20 + (20 * Y));

                X++;
                pose.popPose();
            }

            ci.cancel();

             */
        }
    }
}
