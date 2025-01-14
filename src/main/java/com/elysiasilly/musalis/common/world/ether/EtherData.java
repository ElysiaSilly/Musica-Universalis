package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.core.Musalis;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.HashMap;
import java.util.Map;

public class EtherData extends SimpleJsonResourceReloadListener {

    static final Gson GSON = new Gson();

    public static final EtherData INSTANCE = new EtherData();

    public EtherData() {
        super(GSON, "resonance/ether");
    }

    public static class codec{
        public static Codec<Pair<Ether, Holder<HolderLeitmotif>>> CODEC = Codec.pair(
                Musalis.registries.ETHER.byNameCodec().fieldOf("ether").codec(),
                HolderLeitmotif.codec.HOLDER.fieldOf("leitmotif").codec()
        );
    }

    static final Map<Ether, Leitmotif> DATA = new HashMap<>();

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        DATA.clear();

        object.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "resonance/ether");

            DataResult<Pair<Ether, Holder<HolderLeitmotif>>> result = codec.CODEC.parse(this.makeConditionalOps(), json);//.getOrThrow();

            Musalis.LOGGER.info(key);

            result.getOrThrow();

            //Pair<Ether, Holder<HolderLeitmotif>> pair = codec.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow();

            //DATA.put(pair.getFirst(), pair.getSecond().value().unpack());
        });
    }

    public Map<Ether, Leitmotif> get() {
        return DATA;
    }
}
