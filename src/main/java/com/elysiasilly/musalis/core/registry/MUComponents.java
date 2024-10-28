package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.common.component.GPSComponent;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MUComponents {

    public static final DeferredRegister.DataComponents COMPONENTS = DeferredRegister.createDataComponents(MusicaUniversalis.MODID);


    public static final Supplier<DataComponentType<GPSComponent>> BLOCK_POS = COMPONENTS.registerComponentType("blockpos",
            builder -> builder.persistent(GPSComponent.MAP_CODEC.codec()).networkSynchronized(GPSComponent.STREAM_CODEC));
}
