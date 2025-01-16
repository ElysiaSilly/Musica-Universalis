package com.elysiasilly.musalis.client.screen.composer;

import com.elysiasilly.babel.client.gui.BabelCursor;
import com.elysiasilly.babel.client.gui.BabelScreen;
import com.elysiasilly.babel.client.gui.BabelWidget;
import com.elysiasilly.musalis.common.menu.ResonanceComposerMenu;
import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("rawtypes")
public class ResonanceComposerScreen extends BabelScreen<ResonanceComposerMenu, BabelCursor> {

    public enum Options {
        NONE,
        DELETE,
        BIND
    }

    Options selected = Options.NONE;

    public ResonanceComposerScreen(ResonanceComposerMenu menu, Inventory playerInventory, Component title) {
        super(menu);
    }

    List<Map.Entry<ResourceKey<Note>, Note>> notes = new ArrayList<>();
    List<Map.Entry<ResourceKey<HolderLeitmotif>, HolderLeitmotif>> leitmotifs = new ArrayList<>();

    public static final ResourceLocation POINTER = Musalis.location("textures/gui/sprites/composer/pointer.png");
    public static final ResourceLocation POINTER_CLICK = Musalis.location("composer/pointer_click");
    public static final ResourceLocation POINTER_DRAG = Musalis.location("composer/pointer_drag");

    @Override
    public void initBefore() {
        cacheNotes();
        cacheLeitmotifs();
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
    public List<BabelWidget<?, ?>> initWidgets() {
        List<BabelWidget> widgets = new ArrayList<>();

        RCOptionsWidget options = new RCOptionsWidget(this);
        //options.boundStart = new Vec2(3, 3);
       // options.boundEnd = new Vec2(91, 33);

        RCLeitmotifCatalogueWidget leitmotifCatalogue = new RCLeitmotifCatalogueWidget(this);
       // leitmotifCatalogue.boundStart = new Vec2(3, 36);
        //leitmotifCatalogue.boundEnd = new Vec2(91, 190);

        return List.of(options, leitmotifCatalogue);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, float partialTick) {
        guiGraphics.fill(0, 0, this.width, this.height, -804253680);
    }
}
