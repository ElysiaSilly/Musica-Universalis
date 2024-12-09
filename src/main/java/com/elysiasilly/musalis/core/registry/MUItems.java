package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.item.EtherCoreItem;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MUItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MusicaUniversalis.MODID);

    public static final DeferredItem<Item> RIMESTONE_SHARD =
            ITEMS.registerSimpleItem("rimestone_shard");
    public static final DeferredItem<Item> RIMESTONE_CHUNK =
            ITEMS.registerSimpleItem("rimestone_chunk");

    public static final DeferredItem<EtherCoreItem> FLAWED_CORE =
            ITEMS.register("flawed_ether_core", () ->
                    new EtherCoreItem(100, 0.5f)
            );

    public static final DeferredItem<EtherCoreItem> FLAWLESS_CORE =
            ITEMS.register("flawless_ether_core", () ->
                    new EtherCoreItem(100, 0.8f)
            );

    public static final DeferredItem<EtherCoreItem> CREATIVE_CORE =
            ITEMS.register("creative_ether_core", () ->
                    new EtherCoreItem(0, 1f)
            );
}
