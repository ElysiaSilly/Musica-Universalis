package com.elysiasilly.musalis.networking;

import com.elysiasilly.musalis.client.gui.Test;
import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenPayload;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {

    public static void handleDataOnMain(final ComposerScreenPayload data, final IPayloadContext context) {

        ResonanceComposerMenu menu = (ResonanceComposerMenu) context.player().containerMenu;

        menu.getBE().composeDisk(data.index());
    }

}
