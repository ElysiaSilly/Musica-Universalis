package com.elysiasilly.musalis.common.item;

import com.elysiasilly.musalis.common.interactibles.managers;
import com.elysiasilly.musalis.common.world.ether.EtherCoreManager;
import com.elysiasilly.musalis.common.world.ether.EtherCoreObject;
import com.elysiasilly.musalis.core.Musalis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TestStickItem extends Item {
    public TestStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        if(level instanceof ServerLevel serverLevel) {
            EtherCoreManager manager = (EtherCoreManager) managers.MANAGERS.getFirst(); //EtherCoreManager.get(serverLevel);

            if(!player.isShiftKeyDown()) {
                manager.add(new EtherCoreObject(level, Vec3.ZERO, player.position()));
                System.out.println(manager.interactables.size());
            } else {
                manager.interactables.removeLast();
                System.out.println(manager.interactables.size());

            }
        }

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if(level.isClientSide) {
            if(isSelected) {
                Minecraft.getInstance().gameRenderer.loadEffect(Musalis.location("shaders/post/normals.json"));
                PostChain post = Minecraft.getInstance().gameRenderer.currentEffect();

                //if(((awooga) Minecraft.getInstance().levelRenderer).musicaUniversalis$getDepthRenderTarget() == null) return;

                //post.setUniform("DepthBuffer", ((awooga) Minecraft.getInstance().levelRenderer).musicaUniversalis$getDepthRenderTarget().getDepthTextureId());

            } else {
                Minecraft.getInstance().gameRenderer.shutdownEffect();
            }
        }
    }
}
