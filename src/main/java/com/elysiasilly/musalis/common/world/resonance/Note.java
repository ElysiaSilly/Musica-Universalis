package com.elysiasilly.musalis.common.world.resonance;

import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class Note {

    final NoteEnum type;
    final float loudness;
    final float pitch;
    final float timbre;

    public static class codec{
        public static final Codec<Note> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                StringRepresentable.fromEnum(NoteEnum::values).fieldOf("type").orElse(NoteEnum.NATURAL).forGetter(i -> i.type),
                Codec.floatRange(-1, 1).fieldOf("loudness").forGetter(S -> S.loudness),
                Codec.floatRange(-1, 1).fieldOf("pitch").forGetter(S -> S.pitch),
                Codec.floatRange(-1, 1).fieldOf("timbre").forGetter(S -> S.timbre)
        ).apply(instance, Note::new));

        public static final RegistryFileCodec<Note> HOLDER = RegistryFileCodec.create(MUResourceKeys.registries.NOTE, CODEC);
    }

    public Note(NoteEnum type, float loudness, float pitch, float timbre) {
        this.type = type;
        this.loudness = loudness;
        this.pitch = pitch;
        this.timbre = timbre;
    }

    public NoteEnum getType() {
        return this.type;
    }

    public float getLoudness() {
        return this.loudness;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getTimbre() {
        return this.timbre;
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

        return MusicaUniversalis.location("note/" + string);
    }
}
