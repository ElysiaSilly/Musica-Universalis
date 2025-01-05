package com.elysiasilly.musalis.common.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class RimeStoneBlock extends Block {

    public static final IntegerProperty TILE = IntegerProperty.create("tile", 1, 9);

    public RimeStoneBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(TILE, 1)
        );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {

        int X = Math.abs(context.getClickedPos().getX());
        int Y = Math.abs(context.getClickedPos().getY());
        int Z = Math.abs(context.getClickedPos().getZ());


        int test = (Y % 3) + (Z % 3) + 1;

        System.out.println(test);
        return this.defaultBlockState().setValue(TILE, test);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
       builder.add(TILE);
    }
}
