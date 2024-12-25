package com.elysiasilly.musalis.networking;

import com.elysiasilly.musalis.common.block.container.RMIMenu;
import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenIndexPayload;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenModeSetterPayload;
import com.elysiasilly.musalis.networking.payloads.RMIScreenPayload;
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

    public static void MRIScreenHandler(final RMIScreenPayload data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if(context.player().containerMenu instanceof RMIMenu menu) {
                if(data.id() == 1) menu.getBE().addPosition(data.extra());
                if(data.id() == 2) menu.getBE().addXRot(data.extra());
                if(data.id() == 3) menu.getBE().addYRot(data.extra());
                if(data.id() == 4) menu.getBE().centreYRot();

            }
        });
    }

}
