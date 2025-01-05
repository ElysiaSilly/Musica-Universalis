package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.ethercore.CreativeEtherCore;
import com.elysiasilly.musalis.common.ethercore.FlawedEtherCore;
import com.elysiasilly.musalis.common.ethercore.FlawlessEtherCore;
import com.elysiasilly.musalis.common.world.ether.EtherCore;
import com.elysiasilly.musalis.core.Musalis;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MUEtherCores {
    public static final DeferredRegister<EtherCore> CORES = DeferredRegister.create(Musalis.registries.ETHER_CORE, Musalis.MODID);

    public static final DeferredHolder<EtherCore, CreativeEtherCore> CREATIVE =
            CORES.register("creative", CreativeEtherCore::new);

    public static final DeferredHolder<EtherCore, FlawedEtherCore> FLAWED =
            CORES.register("flawed", FlawedEtherCore::new);

    public static final DeferredHolder<EtherCore, FlawlessEtherCore> FLAWLESS =
            CORES.register("flawless", FlawlessEtherCore::new);
}
