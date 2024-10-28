package com.elysiasilly.musalis.common.block.fluid;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class FluidBlock extends BaseEntityBlock {

    public FluidBlock(Properties properties) {
        super(properties.noOcclusion());
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new FluidBE(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        //if(level.isClientSide) return null;

        return (lvl, pos, st, blockEntity) -> {
            if (blockEntity instanceof FluidBE be) {
                be.tickServer();
                //System.out.println("hey");
            }
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        BlockEntity be = level.getBlockEntity(pos);

        //if(!level.isClientSide) return ItemInteractionResult.CONSUME;

        if(be instanceof FluidBE blockEntity) {
            ItemStack itemstack = player.getItemInHand(hand);

            if(itemstack.is(Items.BUCKET) && !blockEntity.getTank().isEmpty()) {
                if(!level.isClientSide && !player.isCreative()) player.setItemInHand(hand, blockEntity.getTank().getFluid().getFluid().getBucket().getDefaultInstance());

                blockEntity.getTank().getFluid().setAmount(blockEntity.getTank().getFluid().getAmount() - 1000);
            }

            if(itemstack.getItem() instanceof BucketItem bucketItem && bucketItem.content.getFluidType() != Fluids.EMPTY.getFluidType() && blockEntity.getTank().getCapacity() != blockEntity.getTank().getFluidAmount()) {


                if(bucketItem.content == blockEntity.getTank().getFluid().getFluid() || blockEntity.getTank().getFluid().getFluidType() == Fluids.EMPTY.getFluidType()) {
                    FluidStack fluid = new FluidStack(bucketItem.content, 1000);

                    blockEntity.fillTank(player, fluid);
                    if(!level.isClientSide && !player.isCreative()) player.setItemInHand(hand, Items.BUCKET.getDefaultInstance());

                }


            }
            //if(!level.isClientSide) player.sendSystemMessage(Component.literal(blockEntity.getTank().getFluidAmount() + " : " + blockEntity.getTank().getFluid().getFluid().getFluidType() + " : " +                     Minecraft.getInstance().getConnection().registryAccess().registry(ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Musalis.MODID, "fluid_properties"))).get().get(ResourceLocation.parse(blockEntity.getTank().getFluid().getFluid().getFluidType().toString())));

            // TODO : temp

            FluidStack fluid = blockEntity.getTank().getFluid();

            Holder<Fluid> holder = fluid.getFluidHolder();

            //FluidPropertiesData.FluidProperties data = holder.getData(FluidPropertiesData.DATA);

            /*
            System.out.println(

                    Minecraft.getInstance().getConnection().registryAccess().registry(ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Musalis.MODID, "fluid_properties"))).get().get(ResourceLocation.parse(blockEntity.getTank().getFluid().getFluid().getFluidType().toString()))

            );

             */
            //FluidPropertiesData.FluidProperties data = fluid.getFluidHolder().getData(FluidPropertiesData.DATA).

            //if(data != null) {
            //if(!level.isClientSide) System.out.println("fluidstack: " + blockEntity.getTank().getFluid());
            //if(!level.isClientSide) System.out.println("fluidtype: " + blockEntity.getTank().getFluid().getFluidType());
            //if(!level.isClientSide) System.out.println("fluid: " + blockEntity.getTank().getFluid().getFluid());

            //}

        }
        return ItemInteractionResult.SUCCESS;
    }
}
