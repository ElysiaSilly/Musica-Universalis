package com.elysiasilly.musalis.networking;

import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void handleDataOnMain(final ComposerScreenPayload data, final IPayloadContext context) {
        ResonanceComposerMenu menu = (ResonanceComposerMenu) context.player().containerMenu;

        //if(data.id() == 1) menu.getBE().setMode(data.index());
        //if(data.id() == 0) menu.getBE().composeDisk(data.index());
        menu.getBE().composeDisk(data.index());
    }

}
