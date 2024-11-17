package com.elysiasilly.musalis.common.block.item;

import com.elysiasilly.musalis.common.resonance.Resonance;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ItemBlock extends BaseEntityBlock {

    public ItemBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ItemBE(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide) return null;

        return (lvl, pos, st, blockEntity) -> {
            if (blockEntity instanceof ItemBE be) {
                be.tickServer();
            }
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        if(level.isClientSide) return ItemInteractionResult.SUCCESS;

        Registry<Resonance> registry = level.registryAccess().registry(MUResourceKeys.RESONANCE).get();

        System.out.println(registry.get(ResourceLocation.parse("musica_universalis:test")).crawlStrings());

        //for(Map.Entry<ResourceKey<String>, String> id : registry.entrySet()) {
        //    System.out.println(id.getKey());
        //}



        /*
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof ItemBE complexBlockEntity) {
            ItemStack itemstack = player.getItemInHand(hand);

            if (!level.isClientSide && complexBlockEntity.placeItem(player, player.getAbilities().instabuild ? itemstack.copy() : itemstack)) {
                return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.CONSUME;
        }

         */
        return ItemInteractionResult.SUCCESS;
    }
}
