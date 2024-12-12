package com.elysiasilly.musalis.client.render.be;

import com.elysiasilly.musalis.common.block.be.CrResonanceMaterialiserBE;
import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.common.world.resonance.Leitmotif;
import com.elysiasilly.musalis.common.world.resonance.Resonance;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.registry.MUComponents;
import com.elysiasilly.musalis.core.util.RegistryUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ResonanceMaterialiserRenderer implements BlockEntityRenderer<CrResonanceMaterialiserBE> {

    public ResonanceMaterialiserRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(CrResonanceMaterialiserBE be, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedlight, int i1) {

        if(be.getDisk() != null) {
            if(!be.getDisk().isEmpty()) {

                poseStack.pushPose();

                poseStack.translate((float) 1 / 16 * 6, (float) 1 / 16 * 7, 0);

                String string = BuiltInRegistries.ITEM.getKey(be.getDisk().getItem()).toString();
                string = string.substring(string.indexOf(":") + 1);

                BakedModel model = Minecraft.getInstance().getModelManager().getModel(ModelResourceLocation.standalone(MusicaUniversalis.location("special/" + string)));

                VertexConsumer consumer = multiBufferSource.getBuffer(RenderType.cutout());

                Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), consumer, null, model.applyTransform(ItemDisplayContext.GROUND, poseStack, false), 255, 255, 255, 200, OverlayTexture.NO_OVERLAY);

                poseStack.popPose();

                DataDiskComponent component = be.getDisk().get(MUComponents.DATA_DISK);

                if(component == null) return;

                Leitmotif leitmotif = component.getLeitmotif();

                if(leitmotif == null) return;

                Resonance resonance = RegistryUtil.getResonance(be.getLevel(), leitmotif);

                if(resonance == null) return;

                ItemStack item = resonance.getItem().getDefaultInstance();

                poseStack.translate(0.5, 1, 0.5);

                poseStack.mulPose(Axis.XP.rotationDegrees(90));
                poseStack.mulPose(Axis.ZP.rotationDegrees(90));

                poseStack.scale(0.6f, 0.6f, 0.6f);

                model = Minecraft.getInstance().getItemRenderer().getModel(item, Minecraft.getInstance().level, null, 1);

                Minecraft.getInstance().getItemRenderer().render(item, ItemDisplayContext.FIXED, false, poseStack, multiBufferSource, 200, OverlayTexture.NO_OVERLAY, model);
            }
        }
    }
}
