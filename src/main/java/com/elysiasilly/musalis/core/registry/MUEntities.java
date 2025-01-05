package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.core.Musalis;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MUEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Musalis.MODID);

    /*
    public static final Supplier<EntityType<EtherCoreEntity>> ETHER_CORE = ENTITIES.register("ether_core", () -> (
            EntityType.Builder.<EtherCoreEntity>of(EtherCoreEntity::new, MobCategory.MISC)
                    .sized(0.125f, 0.125f)
                    .clientTrackingRange(100)
                    .updateInterval(10)
                    .build("ether_core")
            )
    );

     */
}
