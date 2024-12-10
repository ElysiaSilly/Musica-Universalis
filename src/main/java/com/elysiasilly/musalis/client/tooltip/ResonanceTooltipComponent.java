package com.elysiasilly.musalis.client.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public class ResonanceTooltipComponent implements TooltipComponent {

    ItemStack stack;

    public ResonanceTooltipComponent(ItemStack stack) {
        this.stack = stack;
    }
}
