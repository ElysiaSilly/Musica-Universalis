package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.block.dissolver.DissolverBE;
import com.elysiasilly.musalis.common.block.fluid.FluidBE;
import com.elysiasilly.musalis.common.block.fluidPipes.FluidPipeBlockEntity;
import com.elysiasilly.musalis.common.block.item.ItemBE;
import com.elysiasilly.musalis.common.block.nodeBasedPipes.PipeNodeBE;
import com.elysiasilly.musalis.common.block.ropeblock.RopeBE;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MUBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MusicaUniversalis.MODID);

    public static final Supplier<BlockEntityType<ItemBE>> ITEM_BE = BLOCKENTITES.register(
            "item_be", () -> BlockEntityType.Builder.of(ItemBE::new, MUBlocks.ITEM_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<FluidBE>> FLUID_BE = BLOCKENTITES.register(
            "fluid_be", () -> BlockEntityType.Builder.of(FluidBE::new, MUBlocks.FLUID_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<FluidPipeBlockEntity>> FLUID_PIPE_BE = BLOCKENTITES.register(
            "fluid_pipe_be", () -> BlockEntityType.Builder.of(FluidPipeBlockEntity::new, MUBlocks.FLUID_PIPE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<PipeNodeBE>> PIPE_NODE_BE = BLOCKENTITES.register(
            "pipe_node_be", () -> BlockEntityType.Builder.of(PipeNodeBE::new, MUBlocks.PIPE_NODE.get()).build(null));

    public static final Supplier<BlockEntityType<RopeBE>> ROPE_BE = BLOCKENTITES.register(
            "rope_be", () -> BlockEntityType.Builder.of(RopeBE::new, MUBlocks.ROPE_BLOCK.get()).build(null));

    public static final Supplier<BlockEntityType<DissolverBE>> DISSOLVER = BLOCKENTITES.register(
            "dissolver_be", () -> BlockEntityType.Builder.of(DissolverBE::new, MUBlocks.DISSOLVER.get()).build(null));
}
