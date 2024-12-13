package com.elysiasilly.musalis.client.gui;

import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.registry.MUComponents;
import com.elysiasilly.musalis.core.util.*;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenPayload;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class ResonanceComposerScreen extends AbstractContainerScreen<ResonanceComposerMenu> {

    public ResonanceComposerScreen(ResonanceComposerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void init() {
        super.init();
        createButtons();
    }

    public static final WidgetSprites DELETE_BUTTON = new WidgetSprites(MusicaUniversalis.location("composer/delete"), MusicaUniversalis.location("composer/delete_active"));
    public static final WidgetSprites LOOKUP_BUTTON = new WidgetSprites(MusicaUniversalis.location("composer/lookup"), MusicaUniversalis.location("composer/lookup_active"));
    public static final WidgetSprites SELECT_BUTTON = new WidgetSprites(MusicaUniversalis.location("composer/select"), MusicaUniversalis.location("composer/select_active"));

    public void createButtons() {
        this.addRenderableWidget(new ImageButton(10, 10, 16, 16, DELETE_BUTTON, (e) -> PacketDistributor.sendToServer(new ComposerScreenPayload(1, 1))));
        this.addRenderableWidget(new ImageButton(10, 30, 16, 16, LOOKUP_BUTTON, (e) -> PacketDistributor.sendToServer(new ComposerScreenPayload(2, 1))));
        this.addRenderableWidget(new ImageButton(10, 50, 16, 16, SELECT_BUTTON, (e) -> PacketDistributor.sendToServer(new ComposerScreenPayload(3, 1))));
    }

    public void renderMouse(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        /*
        Matrix4f matrix4f = guiGraphics.pose().last().pose();
        MultiBufferSource bufferSource = guiGraphics.bufferSource();
        VertexConsumer consumer = bufferSource.getBuffer(RenderType.gui());

        RenderUtil.drawLineThatIsActuallyARectangle(consumer, matrix4f, new Vec3(mouseX, 0, 0), new Vec3(mouseX, this.height, 0), .5f, new RGBA(100, 100, 100, 255));

        consumer = bufferSource.getBuffer(RenderType.gui());
        RenderUtil.drawLineThatIsActuallyARectangle(consumer, matrix4f, new Vec3(0, mouseY, 0), new Vec3(this.width, mouseY, 0), .5f, new RGBA(100, 100, 100, 255));
         */
    }


    // todo : cleanup

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderMouse(guiGraphics, mouseX, mouseY, partialTick);

        if(menu.getBE() == null) return;
        if(menu.getBE().getDisk() == null) return;

        DataDiskComponent component = menu.getBE().getDisk().get(MUComponents.DATA_DISK);

        if(component == null) return;

        Leitmotif leitmotif = component.getLeitmotif();

        Resonance resonance = RegistryUtil.getResonance(menu.getBE().getLevel(), leitmotif);
        ResourceLocation location = RegistryUtil.getLeitmotif(menu.getBE().getLevel(), leitmotif);

        String string = "???";
        if(location != null) {
            string = location.toString();
            string = string.substring(string.indexOf(":") + 1);
        }

        guiGraphics.drawString(Minecraft.getInstance().font, string, this.width / 2 - (Minecraft.getInstance().font.width(string) / 2), this.height / 2 - (Minecraft.getInstance().font.lineHeight / 2), ColourUtil.white().toDec());

        if(resonance != null) {
            string = "Resonance matches that of: ";
            guiGraphics.drawString(Minecraft.getInstance().font, string, this.width / 2 - (Minecraft.getInstance().font.width(string) / 2), 10 - (Minecraft.getInstance().font.lineHeight / 2), new RGBA(255, 255, 255, 255).toDec());
            guiGraphics.renderItem(resonance.getItem().getDefaultInstance(), (this.width / 2) - 2 + (Minecraft.getInstance().font.width(string) / 2),  2);
        }

        if(leitmotif.isRecursive()) {
            int index = 0;
            for(Leitmotif recursive : leitmotif.getLeitmotifs()) {
                location = RegistryUtil.getLeitmotif(menu.getBE().getLevel(), recursive);

                string = "???";
                if(location != null) {
                    string = location.toString();
                    string = string.substring(string.indexOf(":") + 1);
                }

                Vec2 position = MathUtil.getPointOnCircle(80, index + 1, leitmotif.getLeitmotifs().size());

                Vec2 adjusted = position.add(new Vec2((float) this.width / 2, (float) this.height / 2));

                Matrix4f vert = guiGraphics.pose().last().pose();
                MultiBufferSource bufferSource = guiGraphics.bufferSource();
                VertexConsumer consumer = bufferSource.getBuffer(RenderType.gui());

                RenderUtil.drawLineThatIsActuallyARectangle(consumer, vert, new Vec3((double) this.width / 2, (double) this.height / 2, 0), new Vec3(adjusted.x, adjusted.y, 0), 1, new RGBA(100, 100, 100, 255));

                WidgetSprites spritesInception = RecipeBookComponent.RECIPE_BUTTON_SPRITES;
                int finalIndex = index;
                this.addRenderableWidget(new ImageButton((int) adjusted.x, (int) adjusted.y, 10, 10, spritesInception, (e) -> PacketDistributor.sendToServer(new ComposerScreenPayload(finalIndex, 0))));

                index++;

                /*
                if(!recursive.getLeitmotifs().isEmpty()) {
                    int indexInception = 0;
                    for(Leitmotif inception : recursive.getLeitmotifs()) {
                        location = RegistryUtil.getLeitmotif(menu.getBE().getLevel(), inception);

                        String strings = "???";
                        if(location != null) {
                            strings = location.toString();
                            strings = strings.substring(strings.indexOf(":") + 1);
                        }

                        Vec2 positionInception = MathUtil.getPointOnCircle(60, indexInception + 1, recursive.getLeitmotifs().size());

                        positionInception = positionInception.add(adjusted);

                        vert = guiGraphics.pose().last().pose();
                        bufferSource = guiGraphics.bufferSource();
                        consumer = bufferSource.getBuffer(RenderType.gui());

                        RenderUtil.drawLineThatIsActuallyARectangle(
                                consumer,
                                vert,
                                new Vec3(adjusted.x, adjusted.y, 0),
                                new Vec3(positionInception.x, positionInception.y, 0),
                                1,
                                new RGBA(50, 50, 50, 255)
                        );

                        guiGraphics.drawString(Minecraft.getInstance().font, strings, (int) positionInception.x - (Minecraft.getInstance().font.width(strings) / 2), (int) positionInception.y - (Minecraft.getInstance().font.lineHeight / 2), new RGBA(70, 70, 70, 255).toDec(), false);

                        indexInception++;
                    }
                }

                 */

                guiGraphics.drawString(Minecraft.getInstance().font, string, (int) adjusted.x - (Minecraft.getInstance().font.width(string) / 2), (int) adjusted.y - (Minecraft.getInstance().font.lineHeight / 2), new RGBA(160, 160, 160).toDec(), false);

            }
        }
    }

    ///

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.renderBlurredBackground(partialTick);
        super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {}

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {}
}
