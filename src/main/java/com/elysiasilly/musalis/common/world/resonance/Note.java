package com.elysiasilly.musalis.common.world.resonance;

import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Optional;

public record Note(Type type, float loudness, float pitch, float timbre) {

    public static class codec {
        public static RegistryFileCodec<Note> HOLDER;

        public static final Codec<Note> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                StringRepresentable.fromEnum(Type::values).fieldOf("type").orElse(Type.NATURAL).forGetter(i -> i.type),
                Codec.floatRange(-1, 1).fieldOf("loudness").forGetter(S -> S.loudness),
                Codec.floatRange(-1, 1).fieldOf("pitch").forGetter(S -> S.pitch),
                Codec.floatRange(-1, 1).fieldOf("timbre").forGetter(S -> S.timbre)
        ).apply(instance, Note::new));

        static { HOLDER = RegistryFileCodec.create(MUResourceKeys.registries.NOTE, CODEC); }
    }

    // todo : fix
    public ResourceKey<Note> key(Level level) {
        Optional<ResourceKey<Note>> note = level.registryAccess().registry(MUResourceKeys.registries.NOTE).flatMap(registry -> registry.getResourceKey(this));
        return note.orElse(null);
    }

    public ResourceLocation location(Level level) {
        Optional<ResourceKey<Note>> note = level.registryAccess().registry(MUResourceKeys.registries.NOTE).flatMap(registry -> registry.getResourceKey(this));

        if(note.isEmpty()) return null;

        String string = note.get().location().toString();
        string = string.substring(string.indexOf(":") + 1);

        return Musalis.location("note/" + string);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Note note) return note.type == this.type && note.loudness == this.loudness && note.pitch == this.pitch && note.timbre == this.timbre;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, loudness, pitch, timbre);
    }

    public enum Type implements StringRepresentable {
        NATURAL("natural"),
        WILD("wild"),
        VIVID("vivid"),
        EXOTIC("exotic");

        private final String name;

        Type(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return this.name;
        }
    }
}
