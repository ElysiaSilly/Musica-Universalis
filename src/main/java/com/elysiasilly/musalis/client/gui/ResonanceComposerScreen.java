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
    }

    List<Vec2> seed = new ArrayList<>();
    List<Vec2> seedInception = new ArrayList<>();

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        if(menu.getBE() == null) return;
        if(menu.getBE().getDisk() == null) return;

        DataDiskComponent component = menu.getBE().getDisk().get(MUComponents.DATA_DISK);

        if(component == null) return;

        Leitmotif leitmotif = component.getLeitmotif();

        Resonance resonance = RegistryUtil.getResonance(menu.getBE().getLevel(), leitmotif);
        ResourceLocation location = RegistryUtil.getLeitmotif(menu.getBE().getLevel(), leitmotif);

        if(location == null) return;

        guiGraphics.drawString(Minecraft.getInstance().font, location.toString(), this.width / 2 - (Minecraft.getInstance().font.width(location.toString()) / 2), this.height / 2, new RGBA(255, 255, 255, 255).toDec());

        String string = "???";

        if(resonance != null) {
            string = "Resonance matches that of: ";
        }

        guiGraphics.drawString(Minecraft.getInstance().font, string, this.width / 2 - (Minecraft.getInstance().font.width(string) / 2), 10, new RGBA(255, 255, 255, 255).toDec());

        if(resonance == null) return;

        guiGraphics.renderItem(resonance.getItem().getDefaultInstance(), this.width / 2 + (Minecraft.getInstance().font.width(string) / 2), 6);

        if(leitmotif.isRecursive()) {
            int index = 0;
            for(Leitmotif recursive : leitmotif.getLeitmotifs()) {
                location = RegistryUtil.getLeitmotif(menu.getBE().getLevel(), recursive);

                string = "???";
                if(location != null) string = location.toString();

                if(this.seed.size() < index + 1) {
                    this.seed.add(new Vec2(menu.getBE().getLevel().random.nextInt(this.width), menu.getBE().getLevel().random.nextInt(this.height)));
                }

                Vec2 position = this.seed.get(index);

                Matrix4f vert = guiGraphics.pose().last().pose();
                MultiBufferSource bufferSource = guiGraphics.bufferSource();
                VertexConsumer consumer = bufferSource.getBuffer(RenderType.gui());

                RenderUtil.drawLineThatIsActuallyARectangle(consumer, vert, new Vec3((double) this.width / 2, (double) this.height / 2, 0), new Vec3(position.x + ((double) Minecraft.getInstance().font.width(string) / 2), position.y, 0), 1, ColourUtil.white());


                guiGraphics.drawString(Minecraft.getInstance().font, string, (int) position.x, (int) position.y, new RGBA(100, 100, 100, 255).toDec());

                index++;

                if(recursive.isRecursive()) {
                    int indexInception = 0;
                    for(Leitmotif inception : recursive.getLeitmotifs()) {
                        location = RegistryUtil.getLeitmotif(menu.getBE().getLevel(), inception);

                        string = "???";
                        if(location != null) string = location.toString();

                        if(this.seedInception.size() < indexInception + index + 1) {
                            this.seedInception.add(new Vec2(menu.getBE().getLevel().random.nextInt(this.width), menu.getBE().getLevel().random.nextInt(this.height)));
                        }

                        Vec2 positionInception = this.seedInception.get(indexInception);

                        vert = guiGraphics.pose().last().pose();
                        bufferSource = guiGraphics.bufferSource();
                        consumer = bufferSource.getBuffer(RenderType.gui());

                        RenderUtil.drawLineThatIsActuallyARectangle(
                                consumer,
                                vert,
                                new Vec3(position.x + ((double) Minecraft.getInstance().font.width(string) / 2), position.y, 0),
                                new Vec3(positionInception.x + ((double) Minecraft.getInstance().font.width(string) / 2), positionInception.y, 0),
                                1,
                                new RGBA(100, 100, 100, 255)
                        );

                        guiGraphics.drawString(Minecraft.getInstance().font, string, (int) positionInception.x, (int) positionInception.y, new RGBA(50, 50, 50, 255).toDec());

                        indexInception++;
                    }
                }
            }
        }
    }

    /*
    int finalIndex = indexInception;
                        if(false) this.addRenderableWidget(new ImageButton(this.width / 2 - (Minecraft.getInstance().font.width(string) / 2) - 20, 140 + (indexInception * 10), 10, 10, spritesInception, (e) -> PacketDistributor.sendToServer(new ComposerScreenPayload(finalIndex))));
     */

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
