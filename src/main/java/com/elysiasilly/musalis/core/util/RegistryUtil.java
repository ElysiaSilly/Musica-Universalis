package com.elysiasilly.musalis.core.util;

import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class RegistryUtil {

    public static Holder<Enchantment> getEnchantment(Level level, ResourceKey<Enchantment> resourceKey) {
        return level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(resourceKey);
    }

    public static Resonance getResonance(Level level, Item item) {
        AtomicReference<Resonance> output = new AtomicReference<>();

        Optional<Registry<Resonance>> registry = level.registryAccess().registry(MUResourceKeys.registries.RESONANCE);

        if(registry.isEmpty()) return null;

        registry.get().stream().forEach(resonance -> {
            if(resonance.getItem().equals(item)) {
                output.set(resonance);
            }
        });

        return output.get() == null ? null : output.get();
    }
}
