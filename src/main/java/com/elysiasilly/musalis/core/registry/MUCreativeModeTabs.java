package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MUCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVETABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MusicaUniversalis.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CALVARIAE = CREATIVETABS.register("musica_universalis", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.musica_universalis"))
            //.icon(() -> new ItemStack(ModBlocks.VELVET_MOSS_BLOCK.get()))
            .displayItems((parameters, output) -> {
                // gets all items in BlockRegistry
                for (DeferredHolder<Item, ? extends Item> item : MUBlocks.BLOCKITEMS.getEntries()) {
                    output.accept(item.get());
                }
                for (DeferredHolder<Item, ? extends Item> item : MUItems.ITEMS.getEntries()) {
                    output.accept(item.get());
                }
            })
            .build()
    );

}
