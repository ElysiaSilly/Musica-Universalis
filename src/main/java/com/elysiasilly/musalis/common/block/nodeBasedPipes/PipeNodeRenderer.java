package com.elysiasilly.musalis.common.block.nodeBasedPipes;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Dictionary;
import java.util.Enumeration;

public class PipeNodeRenderer implements BlockEntityRenderer<PipeNodeBE> {

    public PipeNodeRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PipeNodeBE pipeNodeBE, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {

        BlockPos nodePos = pipeNodeBE.getBlockPos();

        Dictionary<BlockPos, NodeConnectionData> connections = pipeNodeBE.getConnectedNodes();


        if(!connections.isEmpty()) {
            Enumeration<BlockPos> k = connections.keys();

            while (k.hasMoreElements()) {

                BlockPos key = k.nextElement();

                VertexConsumer con = multiBufferSource.getBuffer(RenderType.lines());

                poseStack.pushPose();

                float x_0 = key.getX() - nodePos.getX() + 0.5f; //-8F;
                float y_0 = key.getY() - nodePos.getY() + 0.5f; //57F;
                float z_0 = key.getZ() - nodePos.getZ() + 0.5f; //16F;
                Vector3f initial = new Vector3f(x_0, y_0, z_0);

                float x_f = 0.5f;
                float y_f = 0.5f;
                float z_f = 0.5f;

                Vector3f finalPos = new Vector3f(x_f, y_f, z_f);

                PoseStack.Pose pose = poseStack.last();
                float red = 1F;
                float green = 0F;
                float blue = 0F;
                float alpha = 1F;
                Vector3f normal = initial.sub(finalPos).normalize();

                con.addVertex(pose, x_0, y_0, z_0).setColor(red, green, blue, alpha).setNormal(pose, normal.x, normal.y, normal.z);
                con.addVertex(pose, x_f, y_f, z_f).setColor(red, green, blue, alpha).setNormal(pose, normal.x, normal.y, normal.z);

                poseStack.popPose();


                //

                NodeConnectionData data = pipeNodeBE.getConnectedNodes().get(key);

                int dist = data.distance();

                for(int a = 1; a < dist; a++) {
                    VertexConsumer con1 = multiBufferSource.getBuffer(RenderType.lines());

                    float x_01 = ((float) (key.getX() - nodePos.getX()) / ((float) dist / a)) + 0.5f; //-8F;

                    float y_01 = ((float) (key.getY() - nodePos.getY()) + 1.0f); //57F;

                    float z_01 = ((float) (key.getZ() - nodePos.getZ())  / ((float) dist / a)) + 0.5f; //16F;
                    Vector3f initial1 = new Vector3f(x_0, y_0, z_0);

                    float x_f1 = ((float) (key.getX() - nodePos.getX()) / ((float) dist / a)) + 0.5f;

                    float y_f1 = ((float) (key.getY() + data.heightDifference()/a));

                    float z_f1 = ((float) (key.getZ() - nodePos.getZ()) / ((float) dist / a)) + 0.5f;

                    Vector3f finalPos1 = new Vector3f(x_f1, y_f1, z_f1);

                    PoseStack.Pose pose1 = poseStack.last();
                    float red1 = 1F;
                    float green1 = 0F;
                    float blue1 = 0F;
                    float alpha1 = 1F;
                    Vector3f normal1 = initial1.sub(finalPos1).normalize();

                    con1.addVertex(pose, x_01, y_01, z_01).setColor(red1, green1, blue1, alpha1).setNormal(pose1, normal1.x, normal1.y, normal1.z);
                    con1.addVertex(pose, x_f1, y_f1, z_f1).setColor(red1, green1, blue1, alpha1).setNormal(pose1, normal1.x, normal1.y, normal1.z);
                }
            }
        }

        VertexConsumer con = multiBufferSource.getBuffer(RenderType.lines());

        float x_0 = 0.5f; //-8F;
        float y_0 = (float) (pipeNodeBE.getFluidHandler().getFluidAmount() * 0.0001 + 2); //57F;
        float z_0 = 0.5f; //16F;
        Vector3f initial = new Vector3f(x_0, y_0, z_0);

        float x_f = 0.5f;
        float y_f = 0f;
        float z_f = 0.5f;

        Vector3f finalPos = new Vector3f(x_f, y_f, z_f);

        PoseStack.Pose pose = poseStack.last();
        float red = 1F;
        float green = 1F;
        float blue = 1F;
        float alpha = 1F;
        Vector3f normal = initial.sub(finalPos).normalize();

        con.addVertex(pose, x_0, y_0, z_0).setColor(red, green, blue, alpha).setNormal(pose, normal.x, normal.y, normal.z);
        con.addVertex(pose, x_f, y_f, z_f).setColor(red, green, blue, alpha).setNormal(pose, normal.x, normal.y, normal.z);

        //if(!connections.isEmpty()) {
            //Enumeration<BlockPos> k = connections.keys();

            //while (k.hasMoreElements()) {

                Matrix4f matrix4f = poseStack.last().pose();

                matrix4f.scale(0.015f, 0.015f, 0.015f);
                matrix4f.rotate(Axis.ZP.rotationDegrees(180));
                matrix4f.translate(-33f, -75f, 0.5f);

                String text = String.valueOf(pipeNodeBE.getConnectedNodes().size() + " : " + pipeNodeBE.getFluidHandler().getFluidAmount());

                float f2 = (float)(-Minecraft.getInstance().font.width(text) / 2);

                Minecraft.getInstance().font.drawInBatch(text, f2, 0, -1, true, matrix4f, multiBufferSource, Font.DisplayMode.NORMAL, -1, i);
            //}
        //}

    }

    @Override
    public boolean shouldRenderOffScreen(PipeNodeBE blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(PipeNodeBE blockEntity, Vec3 cameraPos) {
        return true;
    }


}

