package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.core.Musalis;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MUComponents {

    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Musalis.MODID);

    //public static final Supplier<DataComponentType<EtherCoreComponent>> ETHER_CORE = COMPONENTS.registerComponentType("ether_core",
    //        builder -> builder.persistent(EtherCoreComponent.CODEC.codec()));

    public static final Supplier<DataComponentType<DataDiskComponent>> DATA_DISK = COMPONENTS.registerComponentType("data_disk_rec",
            builder -> builder.persistent(DataDiskComponent.CODEC.codec()).networkSynchronized(DataDiskComponent.STREAM));
}
