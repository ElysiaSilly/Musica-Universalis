package com.elysiasilly.musalis.core;

import com.elysiasilly.musalis.core.registry.*;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

@SuppressWarnings("unused")
@Mod(MusicaUniversalis.MODID)

public class MusicaUniversalis {

    public static final String MODID = "musica_universalis";
    public static String prefix(String string) { return MODID + ":" + string; }

    public static final Logger LOGGER = LogUtils.getLogger();

    public MusicaUniversalis(IEventBus bus, ModContainer modContainer) {

        MUBlockEntities.BLOCKENTITES.register(bus);
        MUBlocks.BLOCKITEMS.register(bus);
        MUBlocks.BLOCKS.register(bus);
        MUCreativeModeTabs.CREATIVETABS.register(bus);
        MUItems.ITEMS.register(bus);
        MUComponents.COMPONENTS.register(bus);

        bus.addListener(this::itemCapabilities);
    }

    @SubscribeEvent
    private void itemCapabilities(RegisterCapabilitiesEvent e) {
        e.registerBlockEntity(Capabilities.ItemHandler.BLOCK, MUBlockEntities.ITEM_BE.get(), (o, direction) -> o.getItemHandler());

        e.registerBlockEntity(Capabilities.FluidHandler.BLOCK, MUBlockEntities.FLUID_BE.get(), (be, ctx) -> be.getTank());
        e.registerBlockEntity(Capabilities.FluidHandler.BLOCK, MUBlockEntities.FLUID_PIPE_BE.get(), (be, ctx) -> be.getTank());
        e.registerBlockEntity(Capabilities.FluidHandler.BLOCK, MUBlockEntities.PIPE_NODE_BE.get(), (be, ctx) -> be.getFluidHandler());

    }
}
