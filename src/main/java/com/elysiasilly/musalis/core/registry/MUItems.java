package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.component.GPSComponent;
import com.elysiasilly.musalis.common.item.GPSItem;
import com.elysiasilly.musalis.common.item.HexCoreItem;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MUItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MusicaUniversalis.MODID);

    // acquired from burning glowstone, emits particles, bottled like dragon's breath
    public static final Supplier<Item> NOBLE_GASES_BOTTLE = ITEMS.registerSimpleItem("noble_gases_bottle", new Item.Properties());


    public static final Supplier<Item> RIMESTONE_SHARD = ITEMS.registerSimpleItem("rimestone_shard", new Item.Properties());
    public static final Supplier<Item> RIMESTONE_CHUNK = ITEMS.registerSimpleItem("rimestone_chunk", new Item.Properties());


    /*
    public static final Supplier<GPSItem> GPS_ITEM = ITEMS.registerItem(
            "gps", GPSItem::new, new Item.Properties()
                    .stacksTo(1).component(ModComponent.BLOCK_POS, BlockPosComponent.EMPTY)
    );
     */
    public static final DeferredItem<HexCoreItem> CRUDE_CORE =
            ITEMS.register("crude_core", () ->
                    new HexCoreItem(new Item.Properties()
                            .stacksTo(1))
            );

    public static final DeferredItem<HexCoreItem> CREATIVE_CORE =
            ITEMS.register("creative_core", () ->
                    new HexCoreItem(new Item.Properties()
                            .stacksTo(1))
            );

    public static final DeferredItem<GPSItem> GPS_ITEM =
            ITEMS.register("gps", () ->
                    new GPSItem(new Item.Properties()
                            .stacksTo(1)
                            .component(MUComponents.BLOCK_POS, GPSComponent.EMPTY))
            );
}
