package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.block.container.ResonanceComposerMenu;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MUMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, MusicaUniversalis.MODID);

    public static final Supplier<MenuType<ResonanceComposerMenu>> RESONANCE_COMPOSER =
            MENUS.register("resonance_composer", () -> IMenuTypeExtension.create(ResonanceComposerMenu::new));
}
