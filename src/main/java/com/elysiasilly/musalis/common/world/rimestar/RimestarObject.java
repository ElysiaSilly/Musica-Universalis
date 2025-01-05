package com.elysiasilly.musalis.common.world.rimestar;

import com.elysiasilly.musalis.common.block.RimeStoneBlock;
import com.elysiasilly.musalis.common.interactibles.Interactable;
import com.elysiasilly.musalis.core.registry.MUBlocks;
import com.elysiasilly.musalis.util.Conversions;
import com.elysiasilly.musalis.util.MathUtil;
import com.elysiasilly.musalis.util.RenderUtil;
import com.elysiasilly.musalis.util.Structure;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.util.Map;
import java.util.UUID;

public class RimestarObject extends Interactable<RimestarManager> {

    final Structure RIMESTAR;
    final UUID ID;

    Vec3 position;
    Vec3 velocity;
    Vec3 rotation = Vec3.ZERO;

    public static class codec{
        public static final Codec<RimestarObject> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Structure.codec.CODEC.fieldOf("structure").forGetter(i -> i.RIMESTAR),
                UUIDUtil.CODEC.fieldOf("id").forGetter(i -> i.ID),
                Vec3.CODEC.fieldOf("pos").forGetter(i -> i.position),
                Vec3.CODEC.fieldOf("vel").forGetter(i -> i.velocity),
                Vec3.CODEC.fieldOf("rot").forGetter(i -> i.rotation)
        ).apply(instance, RimestarObject::new));
    }

    private RimestarObject(Structure rimestar, UUID ID, Vec3 pos, Vec3 vel, Vec3 rot) {
        this.RIMESTAR = rimestar;
        this.ID = ID;
        this.position = pos;
        this.velocity = vel;
        this.rotation = rot;

        this.startPos = pos;
        this.endPos = pos.add(0, 20, 10);
    }

    public RimestarObject(Vec3 pos, RandomSource random) {
        this.position = pos;
        this.ID = UUID.randomUUID();

        this.velocity = MathUtil.vectors.random(random);

        this.RIMESTAR = new Structure(Map.of());

        for(Vec3 vec3 : MathUtil.shapes.sphere(random.nextIntBetweenInclusive(5, 10), .9f)) {
            this.RIMESTAR.put(Conversions.vector.blockPos(vec3), MUBlocks.RIMESTONE.get().defaultBlockState().setValue(RimeStoneBlock.TILE, random.nextInt(1,9)));
        }

        this.RIMESTAR.compute();

        this.startPos = pos;
        this.endPos = pos.add(0, 20, 10);
    }

    Vec3 startPos;
    Vec3 endPos;

    @Override
    public void render(RenderLevelStageEvent event) {

        if(RenderUtil.stage(event, RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS)) return;
        if(Minecraft.getInstance().player == null) return;

        PoseStack stack = event.getPoseStack();
        stack.pushPose();

        MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();
        Camera cam = Minecraft.getInstance().gameRenderer.getMainCamera();

        stack.translate(position.x - cam.getPosition().x, position.y - cam.getPosition().y, position.z - cam.getPosition().z);

        float angleX = 0.001f * (Minecraft.getInstance().player.tickCount + event.getPartialTick().getGameTimeDeltaPartialTick(true)) * 360;

        stack.mulPose(Axis.ZN.rotationDegrees(angleX));
        stack.mulPose(Axis.XN.rotationDegrees(angleX));

        this.RIMESTAR.render(stack, source);

        stack.popPose();

    }

    @Override
    public void tick(Level level) {
        double delta = 0.01;
        if(MathUtil.withinBounds(this.position, this.endPos.add(.05, .05, .05), this.endPos.subtract(.05, .05, .05))) {
            this.position = this.position.lerp(this.startPos, delta);
        } else {
            this.position = this.position.lerp(this.endPos, delta);
        }
    }

}
