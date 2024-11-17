package com.elysiasilly.musalis.common.resonance.resonanceassignment;

import com.elysiasilly.musalis.common.resonance.Resonance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ResonanceInput(Resonance resonance) implements RecipeInput {

    public Resonance getResonance() {
        return this.resonance;
    }

    @Override
    public ItemStack getItem(int i) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
