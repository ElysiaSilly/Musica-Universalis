package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.elysiasilly.musalis.common.world.astrom.AstromMassManager;
import com.elysiasilly.musalis.common.world.ether.EtherCoreManager;
import com.elysiasilly.musalis.common.world.rimestar.RimestarManager;
import com.elysiasilly.musalis.core.Musalis;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MUInteractableManagers {
    public static final DeferredRegister<InteractableManager<?>> INTERACTABLES = DeferredRegister.create(Musalis.registries.INTERACTABLE_MANAGER, Musalis.MODID);

    public static final DeferredHolder<InteractableManager<?>, EtherCoreManager> ETHER_CORES =
            INTERACTABLES.register("ether_cores", EtherCoreManager::new);

    public static final DeferredHolder<InteractableManager<?>, RimestarManager> RIMESTARS =
            INTERACTABLES.register("rimestars", RimestarManager::new);

    //public static final DeferredHolder<InteractableManager<?>, AstromMassManager> ASTROM_MASSES =
    //        INTERACTABLES.register("astrom_masses", AstromMassManager::new);
}
