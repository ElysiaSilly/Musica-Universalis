package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.component.EtherCoreComponent;
import com.elysiasilly.musalis.common.world.ether.Ether;
import com.elysiasilly.musalis.common.world.ether.EtherStack;
import com.elysiasilly.musalis.core.MURegistries;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class MUCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVETABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MusicaUniversalis.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB = CREATIVETABS.register("musalis", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.musalis"))
            .icon(() -> new ItemStack(MUItems.RIMESTONE_CHUNK.get()))
            .displayItems((parameters, output) -> output.acceptAll(list(
                    MUBlocks.ASTROM,
                    MUBlocks.COREHOLDER,

                    MUItems.FLAWED_CORE,
                    MUItems.FLAWLESS_CORE,
                    MUItems.CREATIVE_CORE,

                    MUItems.RIMESTONE_CHUNK,
                    MUItems.RIMESTONE_SHARD
            )))
            .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TAB_ETHER = CREATIVETABS.register("musalis_ether", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.musalis_ether"))
            .icon(() -> new ItemStack(MUItems.CREATIVE_CORE.get()))
            .displayItems((parameters, output) -> output.acceptAll(ether()))
            .build()
    );

    private static List<ItemStack> list(Holder<?>...entries) {
        List<ItemStack> list = new ArrayList<>();
        for(Holder<?> entry : entries) {
            if(entry.value() instanceof Item item) {
                list.add(item.getDefaultInstance());
            }
            if(entry.value() instanceof Block block) {
                list.add(block.asItem().getDefaultInstance());
            }
        }
        return list;
    }

    private static List<ItemStack> ether() {
        List<ItemStack> list = new ArrayList<>();
        for(Ether ether : MURegistries.ETHER.stream().toList()) {
            ItemStack stack = MUItems.CREATIVE_CORE.get().getDefaultInstance();
            stack.set(MUComponents.ETHER_CORE, new EtherCoreComponent(new EtherStack(ether, 99), -1, 1f));
            list.add(stack);
        }
        return list;
    }

}
