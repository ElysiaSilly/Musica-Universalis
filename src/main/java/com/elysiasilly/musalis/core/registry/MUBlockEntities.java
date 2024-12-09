package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.block.be.AstromBE;
import com.elysiasilly.musalis.common.block.be.CoreHolderBE;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MUBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MusicaUniversalis.MODID);

    public static final Supplier<BlockEntityType<AstromBE>> ASTROM = BLOCKENTITES.register(
            "astrom", () -> BlockEntityType.Builder.of(AstromBE::new, MUBlocks.ASTROM.get()).build(null));

    public static final Supplier<BlockEntityType<CoreHolderBE>> COREHOLDER = BLOCKENTITES.register(
            "coreholder", () -> BlockEntityType.Builder.of(CoreHolderBE::new, MUBlocks.COREHOLDER.get()).build(null));
}
