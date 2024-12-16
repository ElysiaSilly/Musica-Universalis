package com.elysiasilly.musalis.core.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class ItemUtil {

    public static boolean isEmpty(ItemStack...stacks) {
        for(ItemStack stack : stacks) {
            if(stack == null) return true;
            if(stack.isEmpty()) return true;
        }
        return false;
    }

    public static void serialize(String id, ItemStack stack, CompoundTag tag, HolderLookup.Provider registries) {
        if(isEmpty(stack)) return;
        tag.put(id, stack.save(registries, tag));
    }

    public static ItemStack deserialize(String id, CompoundTag tag, HolderLookup.Provider registries) {
        Tag t = tag.get(id);
        if(t == null) return ItemStack.EMPTY;
        Optional<ItemStack> stack = ItemStack.parse(registries, t);
        return stack.orElse(ItemStack.EMPTY);
    }

    // todo : check for if amount is larger than stack count
    public static ItemStack splitAndCopy(ItemStack stack, int amount) {
        ItemStack copy = stack.copyWithCount(amount);
        stack.shrink(amount);
        return copy;
    }

}
