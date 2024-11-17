package com.elysiasilly.musalis.common.resonance.resonanceassignment;

import com.elysiasilly.musalis.common.resonance.Resonance;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class ResonanceRecipe implements Recipe<ResonanceInput> {

    private final Resonance input;
    private final ItemStack output;

    public ResonanceRecipe(Resonance input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean matches(ResonanceInput input, Level level) {
        return this.input == input.resonance();
    }

    @Override
    public ItemStack assemble(ResonanceInput input, HolderLookup.Provider provider) {
        return this.output.copy();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return false;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return null;
    }
}
