package com.elysiasilly.musalis.client;

import com.elysiasilly.musalis.client.render.be.*;
import com.elysiasilly.musalis.client.screen.RMIScreen;
import com.elysiasilly.musalis.client.screen.composer.ResonanceComposerScreen;
import com.elysiasilly.musalis.client.tooltip.ResonanceClientTooltipComponent;
import com.elysiasilly.musalis.client.tooltip.ResonanceTooltipComponent;
import com.elysiasilly.musalis.core.Musalis;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
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
@EventBusSubscriber(modid = Musalis.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModRegistries {

    @SubscribeEvent
    static void renderers(EntityRenderersEvent.RegisterRenderers event) {
        /// BE
        event.registerBlockEntityRenderer(MUBlockEntities.CREATIVE_ETHER_DISSIPATOR.get(), EtherDissipatorRenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.CREATIVE_RESONANCE_RECORDER.get(), ResonanceRecorderRenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.CREATIVE_RESONANCE_COMPOSER.get(), ResonanceComposerRenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.CREATIVE_RESONANCE_MATERIALISER.get(), ResonanceMaterialiserRenderer::new);

        event.registerBlockEntityRenderer(MUBlockEntities.RMI.get(), RMIBERenderer::new);
        event.registerBlockEntityRenderer(MUBlockEntities.ASTROM.get(), AstromRenderer::new);

        /// ENTITY
    }

    @SubscribeEvent
    static void models(ModelEvent.RegisterAdditional event) {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();
        FileToIdConverter converter = FileToIdConverter.json("models/special");
        converter.listMatchingResources(manager).forEach((location, resource) -> event.register(ModelResourceLocation.standalone(converter.fileToId(location).withPrefix("special/"))));
    }

    @SubscribeEvent
    static void tooltips(RegisterClientTooltipComponentFactoriesEvent event) {
        event.register(ResonanceTooltipComponent.class, ResonanceClientTooltipComponent::new);
    }

    @SubscribeEvent
    static void screens(RegisterMenuScreensEvent event) {
        event.register(MUMenus.RESONANCE_COMPOSER.get(), ResonanceComposerScreen::new);
        event.register(MUMenus.RMI.get(), RMIScreen::new);
    }
}
