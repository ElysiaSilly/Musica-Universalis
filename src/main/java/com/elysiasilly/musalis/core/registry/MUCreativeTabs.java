package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.MUCreativeTab;
import com.elysiasilly.musalis.common.world.ether.Ether;
import com.elysiasilly.musalis.core.Musalis;
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

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Musalis.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MATERIAL = TABS.register("musalis_materials", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.musalis_material"))
            .icon(() -> new ItemStack(MUItems.RIMESTONE_CHUNK.get()))
            .displayItems((parameters, output) -> output.acceptAll(list(
                    MUBlocks.ASTROM,

                    MUItems.FLAWED_CORE,
                    MUItems.FLAWLESS_CORE,
                    MUItems.CREATIVE_CORE,
                    MUItems.CREATIVE_DATA_DISK,

                    MUBlocks.RIMESTONE,
                    MUItems.RIMESTONE_CHUNK,
                    MUItems.RIMESTONE_SHARD,
                    MUBlocks.RIMESTONE_BLOCK,

                    MUItems.COLD_STEEL
            )))
            .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MACHINE = TABS.register("musalis_machines", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.musalis_machine"))
            .icon(() -> new ItemStack(MUBlocks.CREATIVE_ETHER_DISSIPATOR.get()))
            .displayItems((parameters, output) -> output.acceptAll(list(
                    MUBlocks.CREATIVE_ETHER_DISSIPATOR,
                    MUBlocks.CREATIVE_RESONANCE_DISSOLVER,
                    MUBlocks.CREATIVE_RESONANCE_COMPOSER,
                    MUBlocks.CREATIVE_NOTE_TANK,
                    MUBlocks.CREATIVE_RESONANCE_MATERIALISER,
                    MUBlocks.CREATIVE_RESONANCE_RECORDER,
                    MUBlocks.RMI
            )))
            .build()
    );

    public static final DeferredHolder<CreativeModeTab, MUCreativeTab> ETHER = TABS.register("musalis_ether", () -> new MUCreativeTab(
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.musalis_ether"))
                    .icon(() -> new ItemStack(MUItems.CREATIVE_CORE.get()))
                    .displayItems((parameters, output) -> output.acceptAll(ether()))
    ));

    //public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ETHER = TABS.register("musalis_ether", () -> MUCreativeTab.builder()
    //        .title(Component.translatable("itemGroup.musalis_ether"))
    //        .icon(() -> new ItemStack(MUItems.CREATIVE_CORE.get()))
    //        .displayItems((parameters, output) -> output.acceptAll(ether()))
    //        .build()
    //);

    private static List<ItemStack> list(Holder<?>...entries) {
        List<ItemStack> list = new ArrayList<>();
        for(Holder<?> entry : entries) {
            if(entry.value() instanceof Item item) list.add(item.getDefaultInstance());
            if(entry.value() instanceof Block block) list.add(block.asItem().getDefaultInstance());
        }
        return list;
    }

    private static List<ItemStack> ether() {
        List<ItemStack> list = new ArrayList<>();
        for(Ether ether : Musalis.registries.ETHER.stream().toList()) {
            ItemStack stack = MUItems.CREATIVE_CORE.get().getDefaultInstance();
            //stack.set(MUComponents.ETHER_CORE, new EtherCoreComponent(new EtherStack(ether, 99), -1, 1f));
            list.add(stack);
        }
        return list;
    }

}
