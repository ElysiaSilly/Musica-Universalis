package com.elysiasilly.musalis.client.gui;

import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.phys.Vec2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RCLeitmotifEntriesWidget extends BabelWidget<RCLeitmotifCatalogueWidget, ResonanceComposerScreen> {

    public RCLeitmotifEntriesWidget(RCLeitmotifCatalogueWidget parent, ResonanceComposerScreen screen) {
        super(parent, screen);
    }

    boolean requestRecalculation = true;

    @Override
    List<BabelWidget> initWidgets() {
        List<BabelWidget> widgets = new ArrayList<>();

        if(this.indexed == null) return widgets;
        int index = 0;
        for(Map.Entry<ResourceKey<HolderLeitmotif>, HolderLeitmotif> entry : this.indexed) {
            RCLeitmotifEntryWidget widget = new RCLeitmotifEntryWidget(this, this.screen, entry);
            widget.boundStart = new Vec2(localBoundStart.x + 2, localBoundStart.y + 5 + ( index * 10));
            widget.boundEnd = new Vec2(localBoundEnd.x + 12, localBoundEnd.y + 15 + ( index * 10));
            widgets.add(widget);
            index++;
        }

        return widgets;
    }

    @Override
    public void initAfter() {
        this.hoverable= true;
        this.draggable = true;
    }

    List<Map.Entry<ResourceKey<HolderLeitmotif>, HolderLeitmotif>> indexed = new ArrayList<>();

    @Override
    void tick() {
        if(this.requestRecalculation) {
            List<BabelWidget> list = new ArrayList<>(subWidgets);
            for(BabelWidget widget : list) widget.destroy();
            this.indexed.clear();

            if(!this.parent.searchBar.string.isBlank()) {
                for(Map.Entry<ResourceKey<HolderLeitmotif>, HolderLeitmotif> entry : this.screen.leitmotifs) {
                    String string = entry.getKey().location().toString();
                    if (string.contains(this.parent.searchBar.string)) {
                        this.indexed.add(entry);
                    }
                }
            } else {
                this.indexed.addAll(this.screen.leitmotifs);
            }

            List<BabelWidget> init = initWidgets();
            this.screen.allWidgets.addAll(init);
            this.subWidgets.addAll(init);
            this.requestRecalculation = false;
        }
    }

    @Override
    public void onScroll(Vec2 mousePos, Vec2 scroll) {
        moveChildren(scroll);
    }

    @Override
    public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
        moveChildren(new Vec2(0, mouseVelocity.y));
    }

    @Override
    public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
        /*
        int index = 0;
        for(Map.Entry<ResourceKey<HolderLeitmotif>, HolderLeitmotif> leitmotif : this.screen.leitmotifs) {

            String string = leitmotif.getKey().location().toString();
            if(MathUtil.withinBounds(new Vec2(localBoundStart.x + 2, localBoundStart.y + 5 + ( index * 10)), boundStart, boundEnd)) {
                if(this.parent.searchBar.string.isEmpty()) {
                    guiGraphics.drawString(Minecraft.getInstance().font, string, (int) localBoundStart.x + 2, (int) localBoundStart.y + 5 + (index * 10), RGBA.colours.WHITE.toDec());
                    index++;

                } else {
                    RGBA rgba = string.contains(this.parent.searchBar.string) ? RGBA.colours.WHITE : new RGBA(.5f);
                    if(string.contains(this.parent.searchBar.string)) {
                        guiGraphics.drawString(Minecraft.getInstance().font, string, (int) localBoundStart.x + 2, (int) localBoundStart.y + 5 + (index * 10), rgba.toDec());
                        index++;
                    }
                }
            }
        }

         */
    }
}
