package com.elysiasilly.musalis.common.interactibles;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.ArrayList;
import java.util.List;

public abstract class InteractableManager<INTERACTABLE extends Interactable> extends SavedData {

    public final List<INTERACTABLE> interactables = new ArrayList<>();

    public void addInteractable (INTERACTABLE interactable) {
        this.interactables.add(interactable);
    }

    public void render() {
        for(INTERACTABLE interactable : this.interactables) {
            interactable.render();
        }
    }

    public void tick(Level level) {
        for(INTERACTABLE interactable : this.interactables) {
            interactable.tick(level);
        }
    }

    public void destroy(INTERACTABLE interactable) {
        this.interactables.remove(interactable);
    }
}
