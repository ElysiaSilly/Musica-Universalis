package com.elysiasilly.musalis.networking;

import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenIndexPayload;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenModeSetterPayload;
import com.elysiasilly.musalis.networking.payloads.RMIScreenPayload;
import com.elysiasilly.musalis.networking.payloads.RimestarChunkWeightPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@SuppressWarnings({"unused"})
@EventBusSubscriber(modid = Musalis.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModRegistries {

    @SubscribeEvent
    public static void onRegisterPayLoadHandlers(final RegisterPayloadHandlersEvent event) {

        final PayloadRegistrar registrar = event.registrar("1");

        registrar.playToServer(ComposerScreenModeSetterPayload.TYPE, ComposerScreenModeSetterPayload.CODEC,
                        ServerPayloadHandler::ComposerScreenModeSetterHandler);
        registrar.playToServer(ComposerScreenIndexPayload.TYPE, ComposerScreenIndexPayload.CODEC,
                ServerPayloadHandler::ComposerScreenIndexHandler);
        registrar.playToServer(RMIScreenPayload.TYPE, RMIScreenPayload.CODEC,
                ServerPayloadHandler::MRIScreenHandler);
        registrar.playToClient(RimestarChunkWeightPayload.TYPE, RimestarChunkWeightPayload.CODEC,
                ClientPayloadHandler::RimestarChunkWeightHandler);
    }
}
