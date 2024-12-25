package com.elysiasilly.musalis.common.block;

import com.elysiasilly.babel.common.block.BabelEntityBlock;
import com.elysiasilly.musalis.common.block.be.RMIBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RMIBlock extends BabelEntityBlock {

    public RMIBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new RMIBE(blockPos, blockState);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {

        if(level.getBlockEntity(pos) instanceof RMIBE be) {
            player.openMenu(be, pos);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if(level.getBlockEntity(pos) instanceof RMIBE be) {
            return RMIBlock.box(0, 0, 0, 16, 16 + 16 * be.getPosition(), 16);
        }
        return RMIBlock.box(0, 0, 0, 16, 16, 16);
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
        if(level.getBlockEntity(pos) instanceof RMIBE be) {
            return RMIBlock.box(0, 0, 0, 16, 16 + 16 * be.getPosition(), 16);
        }
        return RMIBlock.box(0, 0, 0, 16, 16, 16);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }
}
