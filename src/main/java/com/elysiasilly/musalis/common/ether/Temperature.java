package com.elysiasilly.musalis.common.ether;

import com.elysiasilly.musalis.common.world.ether.Ether;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class Temperature extends Ether {

    @Override
    public void passiveDissipate(ItemStack stack, BlockPos pos, Level level) {
        //BlockPos pos = level.getBlockRandomPos(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ(), 10);

        int x = level.random.nextIntBetweenInclusive(-10, 10);
        int y = level.random.nextIntBetweenInclusive(-10, 10);
        int z = level.random.nextIntBetweenInclusive(-10, 10);

        pos = pos.offset(x, y, z);

        if(level.getBlockState(pos).canBeReplaced() && level.getBlockState(pos.below()).isSolid() && !level.isClientSide) {
            level.setBlock(pos, Blocks.FIRE.defaultBlockState(), 3);
        }


        level.addParticle(ParticleTypes.LAVA, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
    }

    @Override
    public void volatileDissipate(ItemStack stack, BlockPos pos, Level level) {

    }
}
