package com.elysiasilly.musalis.client.gui;

import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import com.elysiasilly.musalis.core.util.MathUtil;
import com.elysiasilly.musalis.core.util.RGBA;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.phys.Vec2;

import java.util.List;
import java.util.Map;

public class RCLeitmotifEntryWidget extends BabelWidget<RCLeitmotifEntriesWidget, ResonanceComposerScreen> {

    public RCLeitmotifEntryWidget(RCLeitmotifEntriesWidget parent, ResonanceComposerScreen screen, Map.Entry<ResourceKey<HolderLeitmotif>, HolderLeitmotif> leitmotif) {
        super(parent, screen);
        this.leitmotif = leitmotif;
    }

    final Map.Entry<ResourceKey<HolderLeitmotif>, HolderLeitmotif> leitmotif;

    @Override
    List<BabelWidget> initWidgets() {
        return List.of();
    }

    @Override
    void tick() {

    }

    @Override
    public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
        if(MathUtil.withinBounds(new Vec2(localBoundStart.x, localBoundStart.y), this.parent.localBoundStart, this.parent.localBoundEnd)) {
            guiGraphics.drawString(Minecraft.getInstance().font, leitmotif.getKey().location().toString(), (int) localBoundStart.x + 2, (int) localBoundStart.y, RGBA.colours.WHITE.toDec());
        }
    }
}
