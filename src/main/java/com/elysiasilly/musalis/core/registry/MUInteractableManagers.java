package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.elysiasilly.musalis.common.world.ether.EtherCoreManager;
import com.elysiasilly.musalis.core.MURegistries;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MUInteractableManagers {
    public static final DeferredRegister<InteractableManager<?>> INTERACTABLES = DeferredRegister.create(MURegistries.INTERACTABLE_MANAGER, MusicaUniversalis.MODID);

    public static final DeferredHolder<InteractableManager<?>, EtherCoreManager> ETHER_CORE =
            INTERACTABLES.register("ether_core", EtherCoreManager::new);
}
