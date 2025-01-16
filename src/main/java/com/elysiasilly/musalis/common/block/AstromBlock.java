package com.elysiasilly.musalis.common.block;

import com.elysiasilly.musalis.common.world.astrom.AstromMassManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class AstromBlock extends Block {
    public AstromBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if(level instanceof ServerLevel serverLevel) {
            AstromMassManager manager = (AstromMassManager) new AstromMassManager().get(serverLevel);

            manager.add(pos);
        }
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(level instanceof ServerLevel serverLevel) {
            AstromMassManager manager = (AstromMassManager) new AstromMassManager().get(serverLevel);

            manager.remove(pos);
        }
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if(level instanceof ServerLevel serverLevel) {
            if(entity instanceof ItemEntity item) {
                AstromMassManager manager = (AstromMassManager) new AstromMassManager().get(serverLevel);
            }
        }
    }
}
