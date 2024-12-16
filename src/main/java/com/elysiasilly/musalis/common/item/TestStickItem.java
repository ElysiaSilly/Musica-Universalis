package com.elysiasilly.musalis.common.item;

import com.elysiasilly.musalis.client.gui.ResonanceComposerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TestStickItem extends Item {
    public TestStickItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        if(level.isClientSide) Minecraft.getInstance().setScreen(new ResonanceComposerScreen(null, player.getInventory(), Component.literal("lol")));

        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}
