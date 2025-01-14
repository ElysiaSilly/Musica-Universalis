package com.elysiasilly.musalis.common.world.resonance;

import com.elysiasilly.musalis.core.Musalis;
import com.google.common.base.Predicates;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.fml.ModList;

import java.util.*;

public class ResonanceData extends SimpleJsonResourceReloadListener {

    static final String DIR = "musalis/resonance";

    static final Gson GSON = new Gson();

    public static final ResonanceData INSTANCE = new ResonanceData();

    public ResonanceData() {
        super(GSON, DIR);
    }

    /*
    public static class codec {

        public static class Config {

            final ConfigCondition CONDITION;

            final List<ConfigEntry> MAPPING = new ArrayList<>();

            public Config(ConfigCondition condition, List<ConfigEntry> mapping) {
                this.CONDITION = condition;
                this.MAPPING.addAll(mapping);
            }

            public static class ConfigEntry {

                public final String DIR;
                public final String REGISTRY;

                public final ConfigCondition CONDITION;

                public ConfigEntry(String dir, String registry, ConfigCondition condition) {
                    DIR = dir;
                    REGISTRY = registry;
                    CONDITION = condition;
                }

                public static final Codec<ConfigEntry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        Codec.STRING.fieldOf("dir").forGetter(i -> i.DIR),
                        Codec.STRING.fieldOf("registry").forGetter(i -> i.REGISTRY),
                        ConfigCondition.CODEC
                ).apply(instance, ConfigEntry::new));
            }

            public static class ConfigCondition {

                /// "#NONE" no condition, but may error upon verifying (eg a registry isn't present)
                /// "<modid>" only loads if mod with matching modid is present
                /// "#FORCE" forces to load, skips verifying, only to be used for debug purposes
                public final String CONDITION;

                public ConfigCondition(String condition) {
                    CONDITION = condition;
                }

                public static final Codec<ConfigCondition> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        Codec.STRING.fieldOf("condition").orElse("#NONE").forGetter(i -> i.CONDITION)
                ).apply(instance, ConfigCondition::new));

                public boolean isForceLoad() {
                    return this.CONDITION.contains("#FORCE");
                }

                public boolean passesCondition() {
                    return this.CONDITION.contains("#NONE") || ModList.get().isLoaded(this.CONDITION) || this.CONDITION.contains("#FORCE");
                }
            }
        }

        public static final Codec<Config> CONFIG = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.fieldOf("condition").orElse("#NONE").forGetter(i -> i.CONDITION),
                Codec.BOOL.fieldOf("forceLoad").orElse(false).forGetter(i -> i.FORCE),
                Codec.list(Codec.pair(Codec.STRING, Codec.STRING)).fieldOf("mappings").forGetter(i -> i.MAPPING)
        ).apply(instance, Config::new));
    }

     */

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler) {
        Musalis.LOGGER.info("Init Resonance Entries");

        List<String> namespaces = new ArrayList<>();

        Map<ResourceLocation, Resource> entries = resourceManager.listResources(DIR, Predicates.alwaysTrue());


        List<String> files = new ArrayList<>();

        for(Map.Entry<ResourceLocation, Resource> entry : entries.entrySet()) {
            Musalis.LOGGER.info("Resonance Entry: {}", entry.getKey());

            String namespace = entry.getKey().toString().split(":")[0];
            files.add(entry.getKey().toString());

            if(!namespaces.contains(namespace)) namespaces.add(namespace);
        }

        for(String namespace : namespaces) {

            if(!files.contains(namespace + ":musalis/resonance/config.json")) {
                throw new RuntimeException("Couldn't find 'config.json' for directory '" + namespace.toUpperCase() + "'");
            }
        }
    }
}
