package com.elysiasilly.musalis.common.data;

import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.MOD)

public class FluidPropertiesData {

    public record FluidProperties(int temperature, int freezingTemp, int boilingTemp, float density, float pressure, float flow) {
        public static final Codec<FluidProperties> CODEC = RecordCodecBuilder.create(instance -> instance.group(

                // in celsius
                Codec.INT.fieldOf("temperature").forGetter(FluidProperties::temperature),

                Codec.INT.fieldOf("freezing_temp").forGetter(FluidProperties::freezingTemp),
                Codec.INT.fieldOf("boiling_temp").forGetter(FluidProperties::boilingTemp),

                // controls how dense a fluid is, 0 would be completely gasious and freeform
                Codec.floatRange(0, 1).fieldOf("density").forGetter(FluidProperties::density),
                // ^ TODO: change this, density now is actually density, state is a new property (liquid, gas...plasma? solid? and etc)

                // how much pressure it exerts, related to pressure in pipes and other fluid storage, also used for how compressible a fluid is
                // 0 would exert no pressure and is very compressible, 1 would exert a ton of pressure and isnt compressible (depending on needs might split these later)
                Codec.floatRange(0, 1).fieldOf("pressure").forGetter(FluidProperties::pressure),

                // controls the flow of fluids in pipes and other fluid storage, 0 would have no flow, effectively a solid, sitting in place and jamming pipes, 0.5 would be goopy
                Codec.floatRange(0, 1).fieldOf("flow").forGetter(FluidProperties::flow)

        ).apply(instance, FluidProperties::new));
    }

    public static final ResourceKey<Registry<FluidProperties>> FLUID_PROPERTIES_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MusicaUniversalis.MODID, "fluid_properties"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                // The registry key.
                FluidPropertiesData.FLUID_PROPERTIES_KEY,
                // The codec of the registry contents.
                FluidProperties.CODEC,
                // The network codec of the registry contents. Often identical to the normal codec.
                // May be a reduced variant of the normal codec that omits data that is not needed on the client.
                // May be null. If null, registry entries will not be synced to the client at all.
                // May be omitted, which is functionally identical to passing null (a method overload
                // with two parameters is called that passes null to the normal three parameter method).
                FluidProperties.CODEC
        );
    }
}
