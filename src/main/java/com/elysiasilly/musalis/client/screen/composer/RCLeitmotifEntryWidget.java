package com.elysiasilly.musalis.client.screen.composer;

import com.elysiasilly.babel.client.screen.BabelWidget;
import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import net.minecraft.resources.ResourceKey;

import java.util.List;
import java.util.Map;

public class RCLeitmotifEntryWidget extends BabelWidget<RCLeitmotifEntriesWidget, ResonanceComposerScreen> {

    public RCLeitmotifEntryWidget(RCLeitmotifEntriesWidget parent, ResonanceComposerScreen screen, Map.Entry<ResourceKey<HolderLeitmotif>, HolderLeitmotif> leitmotif) {
        super(parent, screen, null);
        this.leitmotif = leitmotif;
    }

    final Map.Entry<ResourceKey<HolderLeitmotif>, HolderLeitmotif> leitmotif;

    @Override
    public List<BabelWidget<?, ?>> initWidgets() {
        return List.of();
    }

    //@Override
    //public void initAfter() {
    //    this.draggable = true;
    //    this.hoverable = true;
    //}

    /*
    @Override
    public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
        if(this.parent == null) {
            this.move(mouseVelocity);
        } else {
            //if(mouseVelocity.x > mouseVelocity.y) {
                RCLeitmotifEntryWidget widget = new RCLeitmotifEntryWidget(null, this.screen, this.leitmotif);
                widget.boundStart = this.boundStart;
                widget.boundEnd = this.boundEnd;
                widget.bounds.position = this.bounds.position;

                //this.screen.children.add(widget);
                //this.screen.descendants.add(widget);

                this.screen.draggedWidget = widget;
            //}
        }
    }

     */

    //@Override
    //public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
    //    if(this.parent != null) {
    //        if (MathUtil.withinBounds(new Vec2(localBoundStart.x, localBoundStart.y), this.parent.localBoundStart, this.parent.localBoundEnd)) {
    //            guiGraphics.drawString(Minecraft.getInstance().font, leitmotif.getKey().location().toString(), (int) localBoundStart.x + 2, (int) localBoundStart.y, RGBA.colours.WHITE.dec());
    //        }
    //    } else {
    //        guiGraphics.drawString(Minecraft.getInstance().font, leitmotif.getKey().location().toString(), (int) localBoundStart.x + 2, (int) localBoundStart.y, RGBA.colours.WHITE.dec());
    //    }
    //}
}
