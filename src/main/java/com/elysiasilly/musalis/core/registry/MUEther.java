package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.ether.*;
import com.elysiasilly.musalis.common.world.ether.Ether;
import com.elysiasilly.musalis.core.MURegistries;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MUEther {

    public static final DeferredRegister<Ether> ETHER = DeferredRegister.create(MURegistries.ETHER, MusicaUniversalis.MODID);

    public static final DeferredHolder<Ether, Empty> EMPTY =
            ETHER.register("empty", Empty::new);

    public static final DeferredHolder<Ether, Energy> ENERGY =
            ETHER.register("energy", Energy::new);

    public static final DeferredHolder<Ether, Flow> FLOW =
            ETHER.register("flow", Flow::new);

    public static final DeferredHolder<Ether, Force> FORCE =
            ETHER.register("force", Force::new);

    public static final DeferredHolder<Ether, Inertia> INERTIA =
            ETHER.register("inertia", Inertia::new);

    public static final DeferredHolder<Ether, Mass> MASS =
            ETHER.register("mass", Mass::new);

    public static final DeferredHolder<Ether, Motion> MOTION =
            ETHER.register("motion", Motion::new);

    public static final DeferredHolder<Ether, Temperature> TEMPERATURE =
            ETHER.register("temperature", Temperature::new);

}
