package com.elysiasilly.musalis.common.block.nodeBasedPipes;

import com.elysiasilly.musalis.core.util.SerializeUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Enumeration;

public class PipeNodeBlock extends BaseEntityBlock {

    public static final BooleanProperty LOCKED = BooleanProperty.create("locked");

    public PipeNodeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(LOCKED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PipeNodeBE(blockPos, blockState);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if(level.isClientSide) return ItemInteractionResult.SUCCESS;

        if(level.getBlockEntity(pos) instanceof PipeNodeBE node && !node.getConnectedNodes().isEmpty()) {

            System.out.println("Connected Nodes: " + node.getConnectedNodes().size());

            Enumeration<BlockPos> k = node.getConnectedNodes().keys();

            while (k.hasMoreElements()) {

                BlockPos key = k.nextElement();

                NodeConnectionData bruh = node.getConnectedNodes().get(key);

                System.out.println(key + " : " + bruh.distance() + " : " + bruh.heightDifference());
            }

            System.out.println(node.getPressure() + " : " + node.getFluidHandler().getCapacity() + " : " + node.getFluidHandler().getFluidAmount());

            if(stack.getItem() instanceof BucketItem bucketItem && bucketItem.content.getFluidType() != Fluids.EMPTY.getFluidType()) {

                node.getFluidHandler().fill(new FluidStack(bucketItem.content, 1000), IFluidHandler.FluidAction.EXECUTE);

            }
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide) return null;

        return (lvl, pos, st, blockEntity) -> {
            if (blockEntity instanceof PipeNodeBE be) {
                be.tickServer();
            }
        };
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LOCKED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(LOCKED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (level.isClientSide) return;

        boolean flag = state.getValue(LOCKED);
        if (flag != level.hasNeighborSignal(pos)) {
            if (flag) {
                level.scheduleTick(pos, this, 4);
                // unpowered
                if(level.getBlockEntity(pos) instanceof PipeNodeBE node) {
                }
            } else {
                level.setBlock(pos, state.cycle(LOCKED), 2);
                // powered
            }
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(LOCKED) && !level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.cycle(LOCKED), 2);
        }
    }
}
