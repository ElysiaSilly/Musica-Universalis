package com.elysiasilly.musalis.networking;

import com.elysiasilly.musalis.client.gui.Test;
import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenPayload;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {

    public static void handleDataOnMain(final ComposerScreenPayload data, final IPayloadContext context) {
        // todo : instanceof casts

        context.enqueueWork(() -> {
            ResonanceComposerMenu menu = (ResonanceComposerMenu) context.player().containerMenu;

            if(data.id() == 1) menu.getBE().setMode(data.index());
            if(data.id() == 0) menu.getBE().composeDisk(data.index());
        });

    }

}
