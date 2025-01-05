package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.common.interactibles.Interactable;
import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class EtherCoreObject extends Interactable<EtherCoreManager> {

    private final EtherCore core;

    private Vec3 rotation;
    private Vec3 position;

    private Vec3 velocity = Vec3.ZERO;

    public static class codec{
        public static final Codec<EtherCoreObject> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Musalis.registries.ETHER_CORE.byNameCodec().fieldOf("core").forGetter(i -> i.core),
                Vec3.CODEC.fieldOf("rotation").forGetter(i -> i.rotation),
                Vec3.CODEC.fieldOf("position").forGetter(i -> i.position)
        ).apply(instance, EtherCoreObject::new));
    }

    public EtherCoreObject(EtherCore core, Vec3 rotation, Vec3 position) {
        this.core = core;
        this.rotation = rotation;
        this.position = position;
    }

    public EtherCoreObject(Level level, Vec3 rotation, Vec3 position) {
        this.core = level.registryAccess().registry(MUResourceKeys.registries.ETHER_CORE).flatMap(registry -> registry.getRandom(level.random)).get().value();
        this.rotation = rotation;
        this.position = position;
    }

    public EtherCore core() {
        return this.core;
    }

    public Vec3 rot() {
        return this.rotation;
    }

    public void rot(Vec3 rotation) {
        this.rotation = rotation;
    }

    public Vec3 pos() {
        return this.position;
    }

    public void pos(Vec3 position) {
        this.position = position;
    }

    public void move(Vec3 direction) {
        this.pos(position.add(direction));
    }

    public void vel(Vec3 velocity) {
        this.velocity = velocity;
    }


    @Override
    public void render(RenderLevelStageEvent event) {

        if(true) return;

        if(!(event.getStage() == RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS)) return;

        PoseStack stack = event.getPoseStack();
        stack.pushPose();

        Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();
        stack.translate(position.x - cam.getPosition().x, position.y - cam.getPosition().y, position.z - cam.getPosition().z);

        DebugRenderer.renderFilledBox(stack, Minecraft.getInstance().renderBuffers().bufferSource(), getShape().bounds(), 1, 1, 1, .5f);
        stack.popPose();
    }

    @Override
    public void tick(Level level) {

    }
}
