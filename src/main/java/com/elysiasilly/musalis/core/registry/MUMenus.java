package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.menu.RMIMenu;
import com.elysiasilly.musalis.common.menu.ResonanceComposerMenu;
import com.elysiasilly.musalis.core.Musalis;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MUMenus {

    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, Musalis.MODID);

    public static final Supplier<MenuType<ResonanceComposerMenu>> RESONANCE_COMPOSER =
            MENUS.register("resonance_composer", () -> IMenuTypeExtension.create(ResonanceComposerMenu::new));

    public static final Supplier<MenuType<RMIMenu>> RMI =
            MENUS.register("rmi", () -> IMenuTypeExtension.create(RMIMenu::new));
}
