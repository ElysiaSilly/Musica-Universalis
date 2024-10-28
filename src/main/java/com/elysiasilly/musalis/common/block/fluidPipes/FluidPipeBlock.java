package com.elysiasilly.musalis.common.block.fluidPipes;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidPipeBlock extends BaseEntityBlock {
    //private static final Direction[] DIRECTIONS = Direction.values();
    protected static final VoxelShape SHAPE = Block.box(2.0, 2.0, 2.0, 14.0, 14.0, 14.0);

    //public static final BooleanProperty NODE = BooleanProperty.create("node");

    public static final EnumProperty<PipeConnection> NORTH = EnumProperty.create("north", PipeConnection.class);
    public static final EnumProperty<PipeConnection> EAST  = EnumProperty.create("east",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> SOUTH = EnumProperty.create("south", PipeConnection.class);
    public static final EnumProperty<PipeConnection> WEST  = EnumProperty.create("west",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> UP    = EnumProperty.create("up",    PipeConnection.class);
    public static final EnumProperty<PipeConnection> DOWN  = EnumProperty.create("down",  PipeConnection.class);

    /*
    public static final EnumProperty<PipeConnection> NORTH_EAST = EnumProperty.create("down",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> EAST_SOUTH = EnumProperty.create("down",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> SOUTH_WEST = EnumProperty.create("down",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> WEST_NORTH = EnumProperty.create("down",  PipeConnection.class);

    public static final EnumProperty<PipeConnection> NORTH_UP = EnumProperty.create("down",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> EAST_UP  = EnumProperty.create("down",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> SOUTH_UP = EnumProperty.create("down",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> WEST_UP  = EnumProperty.create("down",  PipeConnection.class);

    public static final EnumProperty<PipeConnection> NORTH_DOWN = EnumProperty.create("down",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> EAST_DOWN  = EnumProperty.create("down",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> SOUTH_DOWN = EnumProperty.create("down",  PipeConnection.class);
    public static final EnumProperty<PipeConnection> WEST_DOWN  = EnumProperty.create("down",  PipeConnection.class);

     */

    //public static final BooleanProperty NORTH;
    //public static final BooleanProperty EAST;
    //public static final BooleanProperty SOUTH;
    //public static final BooleanProperty WEST;
    //public static final BooleanProperty UP;
    //public static final BooleanProperty DOWN;
    //public static final ImmutableMap PROPERTY_BY_DIRECTION;
    //protected final VoxelShape[] shapeByIndex;

    public FluidPipeBlock(Properties properties) {
        super(properties);
        //this.shapeByIndex = this.makeShapes(0.3125F);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(NORTH, PipeConnection.NONE)
                        .setValue(EAST,  PipeConnection.NONE)
                        .setValue(SOUTH, PipeConnection.NONE)
                        .setValue(WEST,  PipeConnection.NONE)
                        .setValue(UP,    PipeConnection.NONE)
                        .setValue(DOWN,  PipeConnection.NONE)
        );
    }

    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FluidPipeBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        //if(level.isClientSide) return null;

        return (lvl, pos, st, blockEntity) -> {
            if (blockEntity instanceof FluidPipeBlockEntity be) {
                be.tickServer();
            }
        };
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    /*
    private VoxelShape[] makeShapes(float apothem) {
        float f = 0.5F - apothem;
        float f1 = 0.5F + apothem;
        VoxelShape voxelshape = Block.box((double)(f * 16.0F), (double)(f * 16.0F), (double)(f * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F), (double)(f1 * 16.0F));
        VoxelShape[] avoxelshape = new VoxelShape[DIRECTIONS.length];

        for(int i = 0; i < DIRECTIONS.length; ++i) {
            Direction direction = DIRECTIONS[i];
            avoxelshape[i] = Shapes.box(0.5 + Math.min((double)(-apothem), (double)direction.getStepX() * 0.5), 0.5 + Math.min((double)(-apothem), (double)direction.getStepY() * 0.5), 0.5 + Math.min((double)(-apothem), (double)direction.getStepZ() * 0.5), 0.5 + Math.max((double)apothem, (double)direction.getStepX() * 0.5), 0.5 + Math.max((double)apothem, (double)direction.getStepY() * 0.5), 0.5 + Math.max((double)apothem, (double)direction.getStepZ() * 0.5));
        }

        VoxelShape[] avoxelshape1 = new VoxelShape[64];

        for(int k = 0; k < 64; ++k) {
            VoxelShape voxelshape1 = voxelshape;

            for(int j = 0; j < DIRECTIONS.length; ++j) {
                if ((k & 1 << j) != 0) {
                    voxelshape1 = Shapes.or(voxelshape1, avoxelshape[j]);
                }
            }

            avoxelshape1[k] = voxelshape1;
        }

        return avoxelshape1;
    }

    protected boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return false;
    }

    /*
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapeByIndex[this.getAABBIndex(state)];
    }

    protected int getAABBIndex(BlockState state) {
        int i = 0;

        for(int j = 0; j < DIRECTIONS.length; ++j) {
            if ((Boolean)state.getValue((Property)PROPERTY_BY_DIRECTION.get(DIRECTIONS[j]))) {
                i |= 1 << j;
            }
        }

        return i;
    }

    static {
        NORTH = BlockStateProperties.NORTH;
        EAST = BlockStateProperties.EAST;
        SOUTH = BlockStateProperties.SOUTH;
        WEST = BlockStateProperties.WEST;
        UP = BlockStateProperties.UP;
        DOWN = BlockStateProperties.DOWN;
        PROPERTY_BY_DIRECTION = ImmutableMap.copyOf((Map) Util.make(Maps.newEnumMap(PipeConnection.class), (parameter) -> {
            parameter.put(Direction.NORTH, NORTH);
            parameter.put(Direction.EAST, EAST);
            parameter.put(Direction.SOUTH, SOUTH);
            parameter.put(Direction.WEST, WEST);
            parameter.put(Direction.UP, UP);
            parameter.put(Direction.DOWN, DOWN);
        }));
    }

     */

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {

        if(level.isClientSide()) return state;

        PipeConnection connection = PipeConnection.NONE;

        if(level instanceof ServerLevel serverLevel) {
            IFluidHandler handler = serverLevel.getCapability(Capabilities.FluidHandler.BLOCK, pos.relative(direction), direction.getOpposite());
            if(handler != null) {
                if(neighborState.getBlock() instanceof FluidPipeBlock) {
                    connection = PipeConnection.PIPE;
                } else {
                    connection = PipeConnection.MACHINE;
                    System.out.println("bruh");
                }
            }
        }

        switch(direction) {
            case UP    -> {return state.setValue(UP,    connection);}
            case DOWN  -> {return state.setValue(DOWN,  connection);}
            case EAST  -> {return state.setValue(EAST,  connection);}
            case WEST  -> {return state.setValue(WEST,  connection);}
            case NORTH -> {return state.setValue(NORTH, connection);}
            case SOUTH -> {return state.setValue(SOUTH, connection);}
        }

        return state;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
