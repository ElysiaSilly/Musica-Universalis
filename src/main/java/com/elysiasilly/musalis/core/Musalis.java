package com.elysiasilly.musalis.core;

import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.elysiasilly.musalis.common.world.ether.Ether;
import com.elysiasilly.musalis.common.world.ether.EtherCore;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.elysiasilly.musalis.core.registry.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Musalis.MODID)
public class Musalis {

    public static final boolean DEV = true;

    public static final String SHORTID = "musalis";
    public static final String MODID = "musica_universalis";

    public static ResourceLocation location(String string) { return ResourceLocation.fromNamespaceAndPath(MODID, string); }

    public static final Logger LOGGER = LogManager.getLogger(SHORTID.toUpperCase());

    public Musalis(final IEventBus bus, final ModContainer modContainer) {

        MUBlockEntities.BLOCKENTITES.register(bus);
        MUBlocks.BLOCKITEMS.register(bus);
        MUBlocks.BLOCKS.register(bus);
        MUCreativeTabs.TABS.register(bus);
        MUItems.ITEMS.register(bus);
        MUComponents.COMPONENTS.register(bus);
        MUEther.ETHER.register(bus);
        MUMenus.MENUS.register(bus);
        MUEntities.ENTITIES.register(bus);
        MUEtherCores.CORES.register(bus);
        MUManagers.INTERACTABLES.register(bus);
        MUAttachments.ATTACHMENTS.register(bus);

    }

    public static class registries {
        public static final Registry<Ether> ETHER = new RegistryBuilder<>(MUResourceKeys.registries.ETHER).create();

        public static final Registry<EtherCore> ETHER_CORE = new RegistryBuilder<>(MUResourceKeys.registries.ETHER_CORE).create();

        public static final Registry<InteractableManager<?>> INTERACTABLE_MANAGER = new RegistryBuilder<>(MUResourceKeys.registries.INTERACTABLE_MANAGER).create();
    }
}
