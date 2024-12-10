package com.elysiasilly.musalis.common.item;

import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.core.registry.MUComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DataDiskItem extends Item {
    public DataDiskItem(Properties properties) {
        super(properties.stacksTo(1).component(MUComponents.DATA_DISK.get(), new DataDiskComponent()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack stack = player.getItemInHand(usedHand);
        if(stack.has(MUComponents.DATA_DISK)) {
            DataDiskComponent component = stack.get(MUComponents.DATA_DISK);
            if (component != null) {
                if(player.isShiftKeyDown()) {
                    component.extractNotes();
                } else {
                    player.displayClientMessage(Component.literal(String.valueOf(component.getNotes().size())), false);
                }
            }
        }


        return InteractionResultHolder.success(stack);
    }
}
