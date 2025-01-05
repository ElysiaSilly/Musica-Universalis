package com.elysiasilly.musalis.client;

import com.elysiasilly.musalis.client.tooltip.ResonanceTooltipComponent;
import com.elysiasilly.musalis.core.Musalis;
import com.mojang.datafixers.util.Either;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;

@SuppressWarnings({"unused"})
@EventBusSubscriber(modid = Musalis.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class GameRegistries {

    @SubscribeEvent
    public static void tooltips(RenderTooltipEvent.GatherComponents event) {
        event.getTooltipElements().addLast(Either.right(new ResonanceTooltipComponent(event.getItemStack())));
    }

}