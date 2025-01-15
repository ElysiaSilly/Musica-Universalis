package com.elysiasilly.musalis.common.world.resonance;

import com.elysiasilly.musalis.core.Musalis;
import com.google.common.base.Predicates;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResonanceData extends SimpleJsonResourceReloadListener {

    // I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS I HATE THIS

    static final String DIR = "musalis/resonance";

    static final Gson GSON = new Gson();

    public static final ResonanceData INSTANCE = new ResonanceData();

    public ResonanceData() {
        super(GSON, DIR);
    }

    /*
    public static class Data {

        final String condition;
        final String namespace;

        Resource config;

        Map<String, Map.Entry<ResourceLocation, Resource>> dirs = new HashMap<>();

        public Data(String condition, String namespace) {
            this.condition = condition;
            this.namespace = namespace;

            //NeoForgeRegistries.
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof String string && string.equals(this.namespace);
        }
    }

     */

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profiler) {
        Musalis.LOGGER.info("Init Resonance Entries");

        Map<String, List<Map.Entry<ResourceLocation, Resource>>> dirs = new HashMap<>();

        Map<ResourceLocation, Resource> entries = resourceManager.listResources(DIR, Predicates.alwaysTrue());

        for(Map.Entry<ResourceLocation, Resource> entry : entries.entrySet()) {
            Musalis.LOGGER.info("Resonance Entry : '{}'", entry.getKey());
            String namespace = entry.getKey().toString().split(":")[0];
            if(!dirs.containsKey(namespace)) dirs.put(namespace, new ArrayList<>());
            dirs.get(namespace).add(entry);
        }

        for(Map.Entry<String, List<Map.Entry<ResourceLocation, Resource>>> entry : dirs.entrySet()) {

            boolean config = false;

            for(Map.Entry<ResourceLocation, Resource> subEntry : entry.getValue()) {
                if(subEntry.getKey().getPath().equals("musalis/resonance/config.json")) {
                    config = true;
                    break;
                }
            }

            if(!config) {
                throw new RuntimeException("Couldn't find 'config.json' for directory '" + entry.getKey() + "'");
            }

            Musalis.LOGGER.info("Resonance Dir '{}' : config = '{}' : entries = {}", entry.getKey(), config, entry.getValue().size());;
        }
    }
}
