package com.elysiasilly.musalis.common.item;

import com.elysiasilly.musalis.client.gui.composer.ResonanceComposerScreen;
import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.elysiasilly.musalis.common.world.ether.EtherCoreManager;
import com.elysiasilly.musalis.core.util.MCUtil;
import com.elysiasilly.musalis.core.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
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
            EtherCoreManager manager = EtherCoreManager.get(serverLevel);

            if(!player.isShiftKeyDown()) {
                manager.addCore(level, player.position());
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
        if(entity instanceof Player player) {
            if(isSelected) {
                Vec3 ray = MCUtil.raycast.shittyRayCast(player, 10, .01f);

                level.addParticle(ParticleTypes.LAVA, ray.x, ray.y, ray.z, 0, 0, 0);
            }
        }

    }
}
