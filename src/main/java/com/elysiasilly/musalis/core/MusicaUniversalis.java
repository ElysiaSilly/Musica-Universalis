package com.elysiasilly.musalis.core;

import com.elysiasilly.musalis.core.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@SuppressWarnings("unused")
@Mod(MusicaUniversalis.MODID)
public class MusicaUniversalis {

    public static final String MODID = "musica_universalis";
    public static String prefix(String string) { return MODID + ":" + string; }
    public static ResourceLocation location(String string) { return ResourceLocation.fromNamespaceAndPath(MODID, string); }

    public static final Logger LOGGER = LogUtils.getLogger();

    public MusicaUniversalis(IEventBus bus, ModContainer modContainer) {

        MUBlockEntities.BLOCKENTITES.register(bus);
        MUBlocks.BLOCKITEMS.register(bus);
        MUBlocks.BLOCKS.register(bus);
        MUCreativeTabs.CREATIVETABS.register(bus);
        MUItems.ITEMS.register(bus);
        MUComponents.COMPONENTS.register(bus);
        MUEther.ETHER.register(bus);
        MUMenus.MENUS.register(bus);
        MUEntities.ENTITIES.register(bus);
        MUEtherCores.CORES.register(bus);

    }
}
