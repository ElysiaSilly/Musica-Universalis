package com.elysiasilly.musalis.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class EtherCoreItem extends Item {

    public EtherCoreItem(int capacity, float stability, Properties properties) {
        super(properties.stacksTo(1)); //.component(MUComponents.ETHER_CORE, new EtherCoreComponent(capacity, stability)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack stack = player.getItemInHand(usedHand);

        /*
        if(stack.has(MUComponents.ETHER_CORE)) {
            if(!level.isClientSide) {
                EtherCoreComponent component = stack.get(MUComponents.ETHER_CORE);

                Ether effect = MURegistries.ETHER.getRandom(level.getRandom()).orElseThrow().value();
                player.displayClientMessage(effect.getName(), true);

                //if(component != null) component.setEther(new EtherStack(effect, 200));
            }
        }

         */


        return InteractionResultHolder.success(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {

        /*
        if(stack.has(MUComponents.ETHER_CORE) && entity.isShiftKeyDown() && isSelected) {

                EtherCoreComponent component = stack.get(MUComponents.ETHER_CORE);

                if(component == null) return;
                EtherStack ether = component.getEther();

                if(ether == null) return;

                if(entity instanceof Player player) {
                    player.displayClientMessage(Component.literal("Ether is leaking..."), true);
                }

                //ether.getEther().passiveDissipate(stack, entity.getOnPos(), level);

        }

         */


    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        /*
        if(stack.has(MUComponents.ETHER_CORE)) {

            EtherCoreComponent component = stack.get(MUComponents.ETHER_CORE);

            if(component == null) return;
            tooltipComponents.add(Component.literal(component.getEtherCapacity() + " : " + component.getStability()).withStyle(ChatFormatting.GRAY));
            if(component.getEther() == null) return;
            //tooltipComponents.add(Component.literal(component.getEther().getEther().getName().getString() + " : " + component.getEther().getAmount()).withStyle(ChatFormatting.GRAY));

        }

         */
    }
}
