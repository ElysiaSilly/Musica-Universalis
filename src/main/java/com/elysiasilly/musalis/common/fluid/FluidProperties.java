package com.elysiasilly.musalis.common.fluid;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

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
