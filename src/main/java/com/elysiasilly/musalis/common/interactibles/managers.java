package com.elysiasilly.musalis.common.interactibles;

import com.elysiasilly.musalis.core.Musalis;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Musalis.MODID, bus = EventBusSubscriber.Bus.GAME)
@SuppressWarnings({"rawtypes"})
public class managers {

    public static final List<InteractableManager> MANAGERS = new ArrayList<>();

    @SubscribeEvent
    public static void init(ServerStartingEvent event) {
        List<InteractableManager<?>> managers = Musalis.registries.INTERACTABLE_MANAGER.stream().toList();

        Musalis.LOGGER.info("Found {} Interactable Manager(s)", managers.size());

        for(InteractableManager manager : Musalis.registries.INTERACTABLE_MANAGER.stream().toList()) {
            Musalis.LOGGER.info("Initialized '{}' Manager!", manager.NAME.toUpperCase());
            MANAGERS.add(manager.get(event.getServer().overworld()));
        }
    }

    @SubscribeEvent
    public static void deInit(ServerStoppingEvent event) {
        MANAGERS.clear();
    }

    @SubscribeEvent
    public static void tick(LevelTickEvent.Pre event) {
        for(InteractableManager manager : MANAGERS) {
            if(manager.TICK.equals(InteractableManager.Tick.PRE)) manager.tick(event.getLevel());
        }
    }

    @SubscribeEvent
    public static void tick(LevelTickEvent.Post event) {
        for(InteractableManager manager : MANAGERS) {
            if(manager.TICK.equals(InteractableManager.Tick.POST)) manager.tick(event.getLevel());
        }
    }

    @SubscribeEvent
    static void render(RenderLevelStageEvent event) {
        for(InteractableManager manager : MANAGERS) {
            manager.render(event);
        }
    }

}
