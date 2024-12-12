package com.elysiasilly.musalis.client.tooltip;

import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.util.RegistryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.joml.Matrix4f;

public class ResonanceClientTooltipComponent implements ClientTooltipComponent {

    private final ResonanceTooltipComponent component;

    public ResonanceClientTooltipComponent(ResonanceTooltipComponent component) {
        this.component = component;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth(Font font) {
        return 0;
    }

    @Override
    public void renderText(Font font, int mouseX, int mouseY, Matrix4f matrix, MultiBufferSource.BufferSource bufferSource) {
        ClientTooltipComponent.super.renderText(font, mouseX, mouseY, matrix, bufferSource);
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics guiGraphics) {

        if(component.stack == null) return;
        if(component.stack.isEmpty()) return;

        Level level = Minecraft.getInstance().level;

        if(level == null) return;

        /*
        if(component.stack.has(MUComponents.DATA_DISK_REC)) {
            DataDiskComponentRec data = component.stack.get(MUComponents.DATA_DISK_REC);
            if(data == null) return;

            if(data.getNotes().isEmpty()) return;

            int index = 0;
            for(Note note : data.getNotes()) {

                Note test = RegistryUtil.getNote(level, note);
                if(test == null) return;
                ResourceLocation location = test.location(level);
                if(location == null) return;
                guiGraphics.blitSprite(location, x + (index * 11), (int) (y + (test.pitch() * 1.5)), 10, 10);
                index++;
            }
        }

         */

        Resonance resonance = RegistryUtil.getResonance(level, component.stack.getItem());

        if(resonance == null) return;

        int index = 0;
        for(Note note : resonance.unpack().getNotes()) {
            ResourceLocation location = note.location(level);

            guiGraphics.blitSprite(location, x + (index * 11), (int) (y + (note.pitch() * 1.5)), 10, 10);
            index++;
        }
    }
}
