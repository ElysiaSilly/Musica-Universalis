package com.elysiasilly.musalis.client.gui;

import com.elysiasilly.babel.client.gui.BabelScreen;
import com.elysiasilly.babel.client.gui.BabelWidget;
import com.elysiasilly.babel.client.gui.IModifyCameraScreen;
import com.elysiasilly.musalis.client.gui.rmi.RMIGimbalWidget;
import com.elysiasilly.musalis.common.block.container.RMIMenu;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import com.elysiasilly.musalis.core.util.MathUtil;
import com.elysiasilly.musalis.networking.payloads.RMIScreenPayload;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@EventBusSubscriber(modid = MusicaUniversalis.MODID, value = Dist.CLIENT)
public class RMIScreen extends BabelScreen<RMIMenu> implements IModifyCameraScreen {

    public RMIScreen(RMIMenu menu, Inventory playerInventory, Component title) {
        super(menu, title);
    }

    @Override
    public @Nullable ResourceLocation mousePointerTexture() {
        return null;
    }

    @Override
    public void initBefore() {
        this.minecraft.gameRenderer.loadEffect(MusicaUniversalis.location("shaders/post/rmi.json"));
    }

    @Override
    public void initAfter() {

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if(keyCode == GLFW.GLFW_KEY_LEFT_SHIFT) PacketDistributor.sendToServer(new RMIScreenPayload(1, -1));
        if(keyCode == GLFW.GLFW_KEY_SPACE) PacketDistributor.sendToServer(new RMIScreenPayload(1, 1));

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        this.minecraft.gameRenderer.shutdownEffect();
        super.onClose();
    }

    @Override
    public List<BabelWidget> initWidgets() {

        RMIGimbalWidget gimbal = new RMIGimbalWidget(null, this);
        gimbal.boundEnd = new Vec2(32, 32);
        gimbal.position = new Vec2(32, 32);

        return List.of(gimbal);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, float partialTick) {
        guiGraphics.blitSprite(MusicaUniversalis.location("frame"), 0, 0, this.width, this.height);
        guiGraphics.blitSprite(MusicaUniversalis.location("frame_extra"), 0, 0, this.width, this.height);

    }

    @Override
    public void camera(Camera camera) {

        // i love vector math
        Vec3 centre = MathUtil.blockPosToVec3(this.menu.getBE().getBlockPos()).add(.5f, this.menu.getBE().getPosition(), .5f);
        Vec2 adjust = MathUtil.rotateAroundPoint(new Vec2((float) centre.x, (float) centre.z), new Vec2((float) centre.x, (float) centre.z - 2), -this.menu.getBE().getYRot());
        Vec3 adjusted = new Vec3(adjust.x, centre.y, adjust.y);

        camera.setPosition(adjusted);
    }

    @Override
    public boolean hideElements() {
        return true;
    }

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        if(Minecraft.getInstance().screen instanceof RMIScreen screen) {
            event.setPitch(-screen.menu.getBE().getXRot());
            event.setYaw(-screen.menu.getBE().getYRot() + 180);
        }
    }
}
