package com.elysiasilly.musalis.client.gui;

import com.elysiasilly.musalis.client.render.MURenderTypes;
import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.common.world.resonance.HolderLeitmotif;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.common.world.resonance.Note;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.elysiasilly.musalis.core.registry.MUComponents;
import com.elysiasilly.musalis.core.util.MathUtil;
import com.elysiasilly.musalis.core.util.RGBA;
import com.elysiasilly.musalis.core.util.RegistryUtil;
import com.elysiasilly.musalis.core.util.RenderUtil;
import com.elysiasilly.musalis.networking.payloads.ComposerScreenIndexPayload;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.jline.reader.Widget;
import org.joml.Matrix4f;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("rawtypes")
public class ResonanceComposerScreen extends BabelScreen<ResonanceComposerMenu> {

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
        if(isSomethingDragging()) return POINTER_DRAG;
        return isNothingInteracted() ? POINTER : POINTER_CLICK;
    }

    @Override
    void initBefore() {
        cacheNotes();
        cacheLeitmotifs();
    }

    @Override
    void initAfter() {
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

        int index = 0;
        for(Map.Entry<ResourceKey<Note>, Note> note : this.notes) {
            DuplicateableNoteWidget widget = new DuplicateableNoteWidget(this, note.getKey().location());
            widget.position = new Vec2(10, 90 + (index * 20));
            widgets.add(widget);
            index++;
        }

        LeitmotifEditorWidget editorWidget = new LeitmotifEditorWidget(this);
        editorWidget.position = new Vec2((float) this.width / 2, (float) this.height / 2);
        editorWidget.depth = 0;
        widgets.add(editorWidget);

        return widgets;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    static class LeitmotifCatalogueWidget extends BabelWidget<BabelWidget, ResonanceComposerScreen> {

        public LeitmotifCatalogueWidget(ResonanceComposerScreen screen) {
            super(screen);
        }

        @Override
        public void initBefore() {

        }

        @Override
        public void initAfter() {

        }

        @Override
        List<BabelWidget> initWidgets() {
            return List.of();
        }

        @Override
        public void onClick(Vec2 mousePos, int button) {

        }

        @Override
        public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {

        }

        @Override
        void tick() {

        }

        @Override
        public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {

        }

        static class DuplicateableLeitmotifWidget extends BabelWidget<BabelWidget, ResonanceComposerScreen> {

            public DuplicateableLeitmotifWidget(ResonanceComposerScreen screen, @Nullable BabelWidget parent) {
                super(screen, parent);
            }

            @Override
            public void initBefore() {

            }

            @Override
            public void initAfter() {

            }

            @Override
            List<BabelWidget> initWidgets() {
                return List.of();
            }

            @Override
            public void onClick(Vec2 mousePos, int button) {

            }

            @Override
            public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {

            }

            @Override
            void tick() {

            }

            @Override
            public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {

            }
        }
    }

    static class DuplicateableNoteWidget extends BabelWidget<BabelWidget, ResonanceComposerScreen> {

        final ResourceLocation location;

        public DuplicateableNoteWidget(ResonanceComposerScreen screen, ResourceLocation location) {
            super(screen);
            this.location = location;
        }

        boolean duplicated = false;

        @Override
        public void initBefore() {

        }

        @Override
        public void initAfter() {
            this.clickable = true;

            this.boundStart = new Vec2(0, 0);
            this.boundEnd = new Vec2(16, 16);
        }

        @Override
        List<BabelWidget> initWidgets() {
            return List.of();
        }

        @Override
        public void onClick(Vec2 mousePos, int button) {
            if(!duplicated) {
                DuplicateableNoteWidget clone = new DuplicateableNoteWidget(this.screen, this.location);
                clone.duplicated = true;
                clone.parent = this;
                clone.depth = this.depth + .1f;
                clone.position = this.position;
                clone.hoverable = true;
                clone.draggable = true;
                clone.clickable = false;

                this.screen.widgets.add(clone);
                this.screen.draggedWidget = clone;
            }
        }

        @Override
        public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
            if(duplicated) {
                move(mouseVelocity);
            }
        }

        @Override
        void tick() {

        }

        @Override
        public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
            Matrix4f matrix4f = guiGraphics.pose().last().pose();
            MultiBufferSource bufferSource = guiGraphics.bufferSource();
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.gui());

            RGBA colour = isDragging() || isHovering() ? RGBA.colours.WHITE : new RGBA(.3f);

            RenderUtil.drawOutlineRectangle(consumer, matrix4f, MathUtil.vec2ToVec3(this.localBoundStart), MathUtil.vec2ToVec3(this.localBoundEnd), 1, colour);

            guiGraphics.blitSprite(this.location, (int) this.localBoundStart.x, (int) this.localBoundStart.y, 16, 16);
        }
    }

    static class LeitmotifEditorWidget extends BabelWidget<BabelWidget, ResonanceComposerScreen> {

        boolean requestRecalculation = false;
        //Leitmotif leitmotif;

        List<Pair<ResourceLocation, Leitmotif>> leitmotifs = new ArrayList<>();
        Leitmotif leitmotif;

        public LeitmotifEditorWidget(ResonanceComposerScreen screen) {
            super(screen);
        }

        @Override
        public void initBefore() {
            parseLeitmotifs();
        }

        public void parseLeitmotifs() {
            List<Pair<ResourceLocation, Leitmotif>> leitmotifs = new ArrayList<>();

            DataDiskComponent component = this.screen.menu.getBE().getDisk().get(MUComponents.DATA_DISK);
            if(component == null) return;
            Leitmotif leitmotif = component.getLeitmotif();
            if(leitmotif == null) return;
            this.leitmotif = leitmotif;
            if(leitmotif.type().equals(Leitmotif.type.RECURSIVE)) {
                for(Leitmotif recursive : leitmotif.getLeitmotifs()) {
                    ResourceLocation location = RegistryUtil.getLeitmotif(this.screen.menu.getBE().getLevel(), recursive);
                    leitmotifs.add(new Pair<>(location, recursive));
                }
            }

            this.leitmotifs = leitmotifs; // todo : leitmotifs field for some reason is always null
        }

        @Override
        public void initAfter() {
            this.boundStart = new Vec2(-50, -50);
            this.boundEnd = new Vec2(50, 50);
            this.draggable = true;
            this.ticks = true;
        }

        @Override
        List<BabelWidget> initWidgets() {
            List<BabelWidget> widgets = new ArrayList<>();

            int size = this.leitmotifs.size();
            int index = 0;
            for(Pair<ResourceLocation, Leitmotif> leitmotif : this.leitmotifs) {

                Vec2 position = MathUtil.getPointOnCircle(80, index + 1, size).add(new Vec2((float) this.screen.width / 2, (float) this.screen.height / 2));

                LeitmotifInstanceWidget widget = new LeitmotifInstanceWidget(this.screen, this, leitmotif);
                widget.position = position;
                widget.depth = this.depth + .1f;
                widgets.add(widget);
                index++;
            }

            return widgets;
        }

        @Override
        public void onClick(Vec2 mousePos, int button) {

        }

        @Override
        public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
            if(button == 0) {
                move(mouseVelocity);
                for(BabelWidget widget : this.subWidgets) {
                   widget.move(mouseVelocity);
                }
            }
            //if(button == 1) {
            //    this.boundEnd = new Vec2(mousePos.x / 2, mousePos.y / 2); // todo : fix mouse position (to screenspace)
            //}
        }

        @Override
        void tick() {
            if(this.requestRecalculation) {
                this.ticks = false;
                List<BabelWidget> list = new ArrayList<>(subWidgets);
                for(BabelWidget widget : list) {
                    widget.destroy();
                }
                parseLeitmotifs();
                List<BabelWidget> init = initWidgets();
                this.screen.allWidgets.addAll(init);
                this.subWidgets.addAll(init);
                this.requestRecalculation = false;
                this.ticks = true;
            }
        }

        @Override
        public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
            Matrix4f matrix4f = guiGraphics.pose().last().pose();
            MultiBufferSource bufferSource = guiGraphics.bufferSource();
            VertexConsumer consumer = bufferSource.getBuffer(RenderType.gui());

            RGBA colour = isHovering() || isDragging() ? RGBA.colours.WHITE : new RGBA(.3f);

            //RenderUtil.drawOutlineRectangle(consumer, matrix4f, MathUtil.vec2ToVec3(this.localBoundStart), MathUtil.vec2ToVec3(this.localBoundEnd), 1, colour);

            RenderUtil.drawResonance(this.leitmotif, (int) ((int) localBoundStart.x - localBoundEnd.x), guiGraphics.pose().last().pose(), bufferSource, MathUtil.vec2ToVec3(this.localBoundStart), MathUtil.vec2ToVec3(this.localBoundEnd));
        }

        static class LeitmotifInstanceWidget extends BabelWidget<LeitmotifEditorWidget, ResonanceComposerScreen> {

            final Pair<ResourceLocation, Leitmotif> leitmotif;

            public LeitmotifInstanceWidget(ResonanceComposerScreen screen, @Nullable LeitmotifEditorWidget parent, Pair<ResourceLocation, Leitmotif> leitmotif) {
                super(screen, parent);
                this.leitmotif = leitmotif;
            }

            @Override
            public void initBefore() {

            }

            @Override
            public void initAfter() {
                this.draggable = true;
                this.boundStart = new Vec2(-8, -8);
                this.boundEnd = new Vec2(8, 8);
                this.hoverable = true;
                this.ticks = true;
                this.clickable = true;
            }

            @Override
            List<BabelWidget> initWidgets() {
                return List.of();
            }

            @Override
            public void onClick(Vec2 mousePos, int button) {
                if(button == 1) {
                    PacketDistributor.sendToServer(new ComposerScreenIndexPayload(leitmotif.getB().hashCode()));
                    this.parent.requestRecalculation = true;
                }
            }

            @Override
            public void onDrag(Vec2 mousePos, int button, Vec2 mouseVelocity) {
                if(button == 0) {
                    //if(MathUtil.withinBounds(this.position.add(this.screen.getMouseVelocity()), this.parent.localBoundStart.add(8), this.parent.localBoundEnd.add(-8))) {
                    move(mouseVelocity);
                }
               // }
            }

            @Override
            void tick() {
                if(!this.parent.hasBoundaries()) return;
                if(this.position.x < this.parent.localBoundStart.x) {
                    //this.position = new;
                }
            }

            @Override
            public void render(GuiGraphics guiGraphics, Vec2 mousePos, float partialTick) {
                Matrix4f matrix4f = guiGraphics.pose().last().pose();
                MultiBufferSource bufferSource = guiGraphics.bufferSource();
                VertexConsumer consumer = bufferSource.getBuffer(RenderType.gui());

                RGBA colour = isHovering() || isDragging() ? RGBA.colours.WHITE : new RGBA(.3f);

                //RenderUtil.drawOutlineRectangle(consumer, matrix4f, MathUtil.vec2ToVec3(this.localBoundStart), MathUtil.vec2ToVec3(this.localBoundEnd), 1, colour);

                RenderUtil.drawLineThatIsActuallyARectangle(consumer, matrix4f, MathUtil.vec2ToVec3(this.position), MathUtil.vec2ToVec3(this.parent.position), 1, colour);

                String string = this.leitmotif.getA() == null ? "???" : this.leitmotif.getA().toString();

                if(leitmotif.getA() != null) string = string.substring(string.indexOf(":") + 1);

                guiGraphics.drawString(Minecraft.getInstance().font, string, (int) localBoundStart.x, (int) localBoundStart.y - 10, RGBA.colours.WHITE.toDec());

                RenderUtil.drawResonance(this.leitmotif.getB(), (int) ((int) localBoundStart.x - localBoundEnd.x), guiGraphics.pose().last().pose(), bufferSource, MathUtil.vec2ToVec3(this.localBoundStart), MathUtil.vec2ToVec3(this.localBoundEnd));

            }
        }
    }
}
