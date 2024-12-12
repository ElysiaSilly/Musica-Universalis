package com.elysiasilly.musalis.networking;

import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@SuppressWarnings({"unused"})
@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModRegistries {

    @SubscribeEvent
    public static void onRegisterPayLoadHandlers(final RegisterPayloadHandlersEvent event) {

        final PayloadRegistrar registrar = event.registrar("1");
        registrar.commonBidirectional(ComposerScreenPayload.TYPE, ComposerScreenPayload.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPayloadHandler::handleDataOnMain,
                        ServerPayloadHandler::handleDataOnMain
                )
        );
    }
}
