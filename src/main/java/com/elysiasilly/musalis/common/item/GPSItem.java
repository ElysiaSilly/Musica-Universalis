package com.elysiasilly.musalis.common.item;

import com.elysiasilly.musalis.common.block.nodeBasedPipes.NodeConnectionResult;
import com.elysiasilly.musalis.common.block.nodeBasedPipes.PipeNodeBE;
import com.elysiasilly.musalis.core.registry.MUComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.List;

public class GPSItem extends Item {

    public GPSItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {

        //if(context.getLevel().isClientSide) return InteractionResult.FAIL;

        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if(level.getBlockEntity(context.getClickedPos()) instanceof PipeNodeBE node) {

            if(context.getPlayer().isShiftKeyDown()) {
                stack.set(MUComponents.BLOCK_POS, (stack.get(MUComponents.BLOCK_POS)).setBlockPos(pos));

                context.getPlayer().displayClientMessage(Component.literal("Saved Coordinates:")
                                .append(" ").append(Integer.toString(pos.getX()))
                                .append(" ").append(Integer.toString(pos.getY()))
                                .append(" ").append(Integer.toString(pos.getZ())),
                        true
                );

            } else {
                if(stack.get(MUComponents.BLOCK_POS).blockPos() == null) return InteractionResult.SUCCESS;

                BlockPos savedPos = stack.get(MUComponents.BLOCK_POS).blockPos();

                NodeConnectionResult i = node.addConnection(savedPos);

                if(i == NodeConnectionResult.CONNECTION_ALREADY_PRESENT) {
                    i = node.removeConnection(savedPos);
                }

                printText(context, savedPos, i.getName());
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag tooltipFlag) {

        String string;

        BlockPos pos = stack.get(MUComponents.BLOCK_POS).blockPos();

        if (pos != null) {
            string = String.format( "Saved Coordinates: %s %s %s", pos.getX(), pos.getY(), pos.getZ());
        } else {
            string = "No connection saved";
        }

        tooltip.add(Component.literal(string).withStyle(ChatFormatting.DARK_GRAY));
    }

    public void printText(UseOnContext context, BlockPos savedPos, String string) {
        context.getPlayer().displayClientMessage(Component.literal(
                String.format( string + " : %s %s %s", savedPos.getX(), savedPos.getY(), savedPos.getZ())),
                true
        );
    }
}
