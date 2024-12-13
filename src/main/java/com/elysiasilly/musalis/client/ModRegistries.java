package com.elysiasilly.musalis.client;

import com.elysiasilly.musalis.client.gui.ResonanceComposerScreen;
import com.elysiasilly.musalis.client.render.be.EtherDissipatorRenderer;
import com.elysiasilly.musalis.client.render.be.ResonanceComposerRenderer;
import com.elysiasilly.musalis.client.render.be.ResonanceMaterialiserRenderer;
import com.elysiasilly.musalis.client.render.be.ResonanceRecorderRenderer;
import com.elysiasilly.musalis.client.render.entity.EtherCoreEntityRenderer;
import com.elysiasilly.musalis.client.tooltip.ResonanceClientTooltipComponent;
import com.elysiasilly.musalis.client.tooltip.ResonanceTooltipComponent;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import com.elysiasilly.musalis.core.registry.MUEntities;
import com.elysiasilly.musalis.core.registry.MUMenus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.server.packs.resources.ResourceManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@SuppressWarnings({"unused"})
@EventBusSubscriber(modid = MusicaUniversalis.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModRegistries {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        /// BE
        event.registerBlockEntityRenderer(MUBlockEntities.CREATIVE_ETHER_DISSIPATOR.get(), EtherDissipatorRenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.CREATIVE_RESONANCE_RECORDER.get(), ResonanceRecorderRenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.CREATIVE_RESONANCE_COMPOSER.get(), ResonanceComposerRenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.CREATIVE_RESONANCE_MATERIALISER.get(), ResonanceMaterialiserRenderer::new);
        /// ENTITY
        event.registerEntityRenderer(MUEntities.ETHER_CORE.get(), EtherCoreEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterAdditionalModel(ModelEvent.RegisterAdditional event) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        FileToIdConverter converter = FileToIdConverter.json("models/special");
        converter.listMatchingResources(manager).forEach((location, resource) -> event.register(ModelResourceLocation.standalone(converter.fileToId(location).withPrefix("special/"))));
    }

    @SubscribeEvent // what a mouthful
    public static void onRegisterClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(ResonanceTooltipComponent.class, ResonanceClientTooltipComponent::new);
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(MUMenus.RESONANCE_COMPOSER.get(), ResonanceComposerScreen::new);
    }
}
