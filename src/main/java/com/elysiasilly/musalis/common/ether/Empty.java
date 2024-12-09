package com.elysiasilly.musalis.common.ether;

import com.elysiasilly.musalis.common.world.ether.Ether;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Empty extends Ether {

    @Override
    public void passiveDissipate(ItemStack stack, BlockPos pos, Level level) {

    }

    @Override
    public void volatileDissipate(ItemStack stack, BlockPos pos, Level level) {

    }
}
