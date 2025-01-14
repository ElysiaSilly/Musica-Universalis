package com.elysiasilly.musalis.core.event;

import com.elysiasilly.musalis.common.world.ether.EtherData;
import com.elysiasilly.musalis.common.world.resonance.ResonanceData;
import com.elysiasilly.musalis.common.world.rimestar.RimestarCommand;
import com.elysiasilly.musalis.core.Musalis;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = Musalis.MODID, bus = EventBusSubscriber.Bus.GAME)
public class GameRegistries {

    @SubscribeEvent
    static void commands(final RegisterCommandsEvent event) {
        RimestarCommand.create(event.getDispatcher(), event.getBuildContext());
    }

    @SubscribeEvent
    private static void reloadListener(AddReloadListenerEvent event) {
        event.addListener(EtherData.INSTANCE);
        event.addListener(ResonanceData.INSTANCE);


        //event.addListener(NoteData.INSTANCE);
    }
}
