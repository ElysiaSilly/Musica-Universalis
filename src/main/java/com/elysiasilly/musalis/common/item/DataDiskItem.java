package com.elysiasilly.musalis.common.item;

import com.elysiasilly.musalis.common.component.DataDiskComponent;
import com.elysiasilly.musalis.core.registry.MUComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DataDiskItem extends Item {
    public DataDiskItem(Properties properties) {
        super(properties.stacksTo(1).component(MUComponents.DATA_DISK.get(), DataDiskComponent.EMPTY));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);


        if(stack.has(MUComponents.DATA_DISK)) {
            DataDiskComponent component = stack.get(MUComponents.DATA_DISK);
            if (component != null){
                player.displayClientMessage(Component.literal(component.hashCode() + " : " + component.getLeitmotif().getLeitmotifs().size()), false);
            }
        }



        return InteractionResultHolder.success(stack);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        DataDiskComponent component = stack.get(MUComponents.DATA_DISK);

        /*
        if(action == ClickAction.SECONDARY) {
            if(slot.hasItem()) {
                ItemStack item = slot.getItem();
                Resonance resonance = RegistryUtil.getResonance(player.level(), item.getItem());

                if(resonance != null) {
                    DataDiskComponent comp = component.insertNotes(resonance.getLeitmotif().getNotes());
                    stack.set(MUComponents.DATA_DISK_REC, comp);
                    return true;
                }
            }
        }

         */

        return false;
    }
}
