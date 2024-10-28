package com.elysiasilly.musalis.common.block;

import com.elysiasilly.musalis.common.data.FluidPropertiesData;
import com.elysiasilly.musalis.common.data.testing.MultiblockData;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

public class TestingBlock extends Block {
    public TestingBlock() {
        super(Properties.ofFullCopy(Blocks.POLISHED_DEEPSLATE));
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if(level.isClientSide) return ItemInteractionResult.SUCCESS;

        // put stuff here


        if(stack.getItem() instanceof BucketItem bucketItem && bucketItem.content.getFluidType() != Fluids.EMPTY.getFluidType()) {

            var fluidProperties = level.registryAccess().registry(FluidPropertiesData.FLUID_PROPERTIES_KEY).get();

            boolean isDefined = fluidProperties.getHolder(ResourceLocation.parse(bucketItem.content.getFluidType().toString())).isPresent();

            if(!isDefined) player.displayClientMessage(Component.literal("No values defined for " + bucketItem.content.getFluidType()), true);

            int temp = fluidProperties.getHolder(ResourceLocation.parse(bucketItem.content.getFluidType().toString())).get().value().temperature();

            player.displayClientMessage(Component.literal(
                            String.format("Temperature of %s is %s°C", bucketItem.content.getFluidType(), temp)),
                    true
            );
        }

        BlockPos test = new BlockPos(0, 64, 0);


        if(stack.is(Items.STICK)) {
            //player.sendSystemMessage(Component.literal(

            System.out.println(
                    String.valueOf(level.registryAccess().registry(MultiblockData.REGISTRY).get().getHolder(ResourceLocation.parse("musica_universalis:test")).isPresent())

                    //Codec.floatRange(1, 0).fieldOf("yeah").fieldOf("bruh").toString()

                    //BlockPos.STREAM_CODEC.encode(FriendlyByteBuf., test)

                    //BlockPos.CODEC.fieldOf("lmao").forGetter(test)

                    //Codec.floatRange(0, 10).encode(1f, JsonOps.INSTANCE, "lol")

            );
        }

        return ItemInteractionResult.SUCCESS;
    }
}
