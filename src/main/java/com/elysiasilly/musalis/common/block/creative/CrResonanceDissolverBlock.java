package com.elysiasilly.musalis.common.block.creative;

import com.elysiasilly.musalis.common.blockentity.creative.CrResonanceDissolverBE;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class CrResonanceDissolverBlock extends BaseEntityBlock {
    public CrResonanceDissolverBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CrResonanceDissolverBE(blockPos, blockState);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {

        /*
        if(level.isClientSide) return ItemInteractionResult.SUCCESS;

        if(level.getBlockEntity(pos) instanceof CrResonanceDissolverBE be) {

            if(stack.has(MUComponents.DATA_DISK_REC)) {
                DataDiskComponent data = stack.get(MUComponents.DATA_DISK_REC);
                if(data != null) stack.set(MUComponents.DATA_DISK_REC, new DataDiskComponent(be.extractNotes()));
            } else {
                be.dissolveItem(stack);
            }

        }

         */
        return ItemInteractionResult.SUCCESS;
    }
}
