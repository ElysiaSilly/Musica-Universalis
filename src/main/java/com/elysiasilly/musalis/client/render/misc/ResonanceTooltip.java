package com.elysiasilly.musalis.client.render.misc;

import com.elysiasilly.musalis.common.resonance.Resonance;
import com.elysiasilly.musalis.common.resonance.ResonanceDataMap;
import com.elysiasilly.musalis.common.resonance.String;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

@SuppressWarnings({"unused"})
@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ResonanceTooltip {


    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {

        List<Component> toolTip = event.getToolTip();
        ItemStack stack = event.getItemStack();

        Holder<Item> item = stack.getItemHolder();
        ResonanceDataMap data = item.getData(ResonanceDataMap.DATAMAP);
        if(data != null) {
            /*
            boolean bool = event.getFlags().hasShiftDown();
            if(bool) {
                List<Holder<String>> strings = data.getResonance().value().crawlStrings();
                int index = 1;

                for(Holder<String> string : strings) {
                    event.getToolTip().add(index, Component.literal(string.value().toString()));
                    index++;
                }
                bool = false;
            } else {
                List<Holder<String>> strings = data.getResonance().value().crawlStrings();
                int index = 1;

                event.getToolTip().add(index, Component.literal(data.getResonance().getRegisteredName()));
                index++;
                for(Holder<String> string : strings) {
                    event.getToolTip().add(index, Component.literal("- " + string.getRegisteredName()));
                    index++;
                }
            }
             */
            int index = 1;
            boolean bool = event.getFlags().hasShiftDown();

            if(bool) {
                toolTip.add(index, Component.literal(data.getResonance().getRegisteredName()));
                index++;

                List<Holder<com.elysiasilly.musalis.common.resonance.String>> strings = data.getResonance().value().getStrings();

                for (Holder<String> string : strings) {
                    toolTip.add(index, Component.literal("- " + string.value()).withStyle(ChatFormatting.GRAY));
                    index++;
                }

                List<Holder<Resonance>> resonances = data.getResonance().value().getResonance();

                for (Holder<Resonance> resonance : resonances) {
                    toolTip.add(index, Component.literal("- " + resonance.getRegisteredName()));
                    index++;
                    strings = resonance.value().getStrings();

                    for (Holder<String> string : strings) {
                        toolTip.add(index, Component.literal("-- " + string.value()).withStyle(ChatFormatting.GRAY));
                        index++;
                    }
                }
            } else {
                toolTip.add(index, Component.literal(data.getResonance().getRegisteredName()));
                index++;

                List<Holder<com.elysiasilly.musalis.common.resonance.String>> strings = data.getResonance().value().getStrings();

                for (Holder<String> string : strings) {
                    toolTip.add(index, Component.literal("- " + string.getRegisteredName()).withStyle(ChatFormatting.GRAY));
                    index++;
                }

                List<Holder<Resonance>> resonances = data.getResonance().value().getResonance();

                for (Holder<Resonance> resonance : resonances) {
                    toolTip.add(index, Component.literal("- " + resonance.getRegisteredName()));
                    index++;
                    strings = resonance.value().getStrings();

                    for (Holder<String> string : strings) {
                        toolTip.add(index, Component.literal("-- " + string.getRegisteredName()).withStyle(ChatFormatting.GRAY));
                        index++;
                    }
                }
            }
        }
    }
}
