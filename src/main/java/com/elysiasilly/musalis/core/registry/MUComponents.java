package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.component.EtherCoreComponent;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MUComponents {

    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(MusicaUniversalis.MODID);

    public static final Supplier<DataComponentType<EtherCoreComponent>> ETHER_CORE = COMPONENTS.registerComponentType("ether_core",
            builder -> builder.persistent(EtherCoreComponent.CODEC.codec()));


}
