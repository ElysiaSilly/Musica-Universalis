package com.elysiasilly.musalis.util;

import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
            if(resonance.getItem().equals(item)) output.set(resonance);

        });

        return output.get() == null ? null : output.get();
    }

    public static Note getNote(Level level, Note note) {
        AtomicReference<Note> output = new AtomicReference<>();

        Optional<Registry<Note>> registry = level.registryAccess().registry(MUResourceKeys.registries.NOTE);

        if(registry.isEmpty()) return null;

        registry.get().stream().forEach(stream -> {
            if(stream.equals(note)) output.set(stream);
        });

        return output.get() == null ? null : output.get();
    }

    public static Resonance getResonance(Level level, Leitmotif leitmotif) {
        AtomicReference<Resonance> output = new AtomicReference<>();

        Optional<Registry<Resonance>> registry = level.registryAccess().registry(MUResourceKeys.registries.RESONANCE);

        if(registry.isEmpty()) return null;

        registry.get().stream().forEach(stream -> {
            if(stream.match(leitmotif)) output.set(stream);
        });

        return output.get() == null ? null : output.get();
    }

    public static ResourceLocation getLeitmotif(Level level, Leitmotif leitmotif) {
        AtomicReference<ResourceLocation> output = new AtomicReference<>();

        Optional<Registry<HolderLeitmotif>> registry = level.registryAccess().registry(MUResourceKeys.registries.LEITMOTIF);

        if(registry.isEmpty()) return null;

        registry.get().stream().forEach(stream -> {
            if(stream.unpack().equals(leitmotif)) output.set(registry.get().getKey(stream));
        });

        return output.get() == null ? null : output.get();
    }
}
