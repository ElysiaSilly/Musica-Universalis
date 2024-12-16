package com.elysiasilly.musalis.networking;

import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenIndexPayload;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenModeSetterPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {

    public static void ComposerScreenModeSetterHandler(final ComposerScreenModeSetterPayload data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if(context.player().containerMenu instanceof ResonanceComposerMenu menu) {
                menu.getBE().setMode(data.mode());
            }
        });
    }

    public static void ComposerScreenIndexHandler(final ComposerScreenIndexPayload data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if(context.player().containerMenu instanceof ResonanceComposerMenu menu) {
                menu.getBE().composeDisk(data.index());
            }
        });
    }

}
