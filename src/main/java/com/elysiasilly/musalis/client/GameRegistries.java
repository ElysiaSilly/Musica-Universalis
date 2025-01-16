package com.elysiasilly.musalis.client;

import com.elysiasilly.musalis.client.screen.CreativeTab;
import com.elysiasilly.musalis.client.tooltip.ResonanceTooltipComponent;
import com.elysiasilly.musalis.core.Musalis;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings({"unused"})
@EventBusSubscriber(modid = Musalis.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class GameRegistries {

    @SubscribeEvent
    public static void tooltips(RenderTooltipEvent.GatherComponents event) {
        event.getTooltipElements().addLast(Either.right(new ResonanceTooltipComponent(event.getItemStack())));
    }

    @SubscribeEvent
    public static void keyPress(ScreenEvent.KeyPressed.Post event) {
        if(!Musalis.DEV) return;
        if(event.getKeyCode() == GLFW.GLFW_KEY_N) {
            Minecraft.getInstance().setScreen(new CreativeTab());
        }
    }

}