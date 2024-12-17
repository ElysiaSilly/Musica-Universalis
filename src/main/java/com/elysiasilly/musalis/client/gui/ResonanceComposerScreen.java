package com.elysiasilly.musalis.client.gui;

import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("rawtypes")
public class ResonanceComposerScreen extends BabelScreen<ResonanceComposerMenu> {

    public enum Options {
        NONE,
        DELETE,
        BIND
    }

    Options selected = Options.NONE;

    public ResonanceComposerScreen(ResonanceComposerMenu menu, Inventory playerInventory, Component title) {
        super(menu, title);
    }

    List<Map.Entry<ResourceKey<Note>, Note>> notes = new ArrayList<>();
    List<Map.Entry<ResourceKey<HolderLeitmotif>, HolderLeitmotif>> leitmotifs = new ArrayList<>();

    public static final ResourceLocation POINTER = MusicaUniversalis.location("composer/pointer");
    public static final ResourceLocation POINTER_CLICK = MusicaUniversalis.location("composer/pointer_click");
    public static final ResourceLocation POINTER_DRAG = MusicaUniversalis.location("composer/pointer_drag");

    @Override
    ResourceLocation mousePointerTexture() {
        return null;
        //if(isSomethingDragging()) return POINTER_DRAG;
        //return isNothingInteracted() ? POINTER : POINTER_CLICK;
    }

    @Override
    void initBefore() {
        cacheNotes();
        cacheLeitmotifs();
    }

    @Override
    void initAfter() {
        this.renderDebug = true;
    }

    public void cacheNotes() {
        Level level = Minecraft.getInstance().level;
        if(level == null) return;
        Optional<Registry<Note>> registry = level.registryAccess().registry(MUResourceKeys.registries.NOTE);
        if(registry.isEmpty()) return;
        this.notes = registry.get().entrySet().stream().toList();
    }

    public void cacheLeitmotifs() {
        Level level = Minecraft.getInstance().level;
        if(level == null) return;
        Optional<Registry<HolderLeitmotif>> registry = level.registryAccess().registry(MUResourceKeys.registries.LEITMOTIF);
        if(registry.isEmpty()) return;
        this.leitmotifs = registry.get().entrySet().stream().toList();
    }

    @Override
    List<BabelWidget> initWidgets() {
        List<BabelWidget> widgets = new ArrayList<>();

        RCOptionsWidget options = new RCOptionsWidget(this);
        options.boundStart = new Vec2(3, 3);
        options.boundEnd = new Vec2(91, 33);

        RCLeitmotifCatalogueWidget leitmotifCatalogue = new RCLeitmotifCatalogueWidget(this);
        leitmotifCatalogue.boundStart = new Vec2(3, 36);
        leitmotifCatalogue.boundEnd = new Vec2(91, 190);

        return List.of(options, leitmotifCatalogue);
    }

    @Override
    void renderBackground(GuiGraphics guiGraphics, float partialTick) {
        guiGraphics.fill(0, 0, this.width, this.height, -804253680);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
