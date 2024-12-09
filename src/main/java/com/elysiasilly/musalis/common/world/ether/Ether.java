package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.core.MURegistries;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class Ether {

    /// runs every tick when dissipating
    public abstract void passiveDissipate(ItemStack stack, BlockPos pos, Level level);

    /// runs once when a core is aggravated
    public abstract void volatileDissipate(ItemStack stack, BlockPos pos, Level level);

    public Component getName() {
        return Component.translatable(Util.makeDescriptionId("ether", MURegistries.ETHER.getKey(this)));
    }
}
