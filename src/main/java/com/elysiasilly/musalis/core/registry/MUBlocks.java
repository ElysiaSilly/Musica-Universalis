package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.block.TestingBlock;
import com.elysiasilly.musalis.common.block.dissolver.DissolverBlock;
import com.elysiasilly.musalis.common.block.fluid.FluidBlock;
import com.elysiasilly.musalis.common.block.fluidPipes.FluidPipeBlock;
import com.elysiasilly.musalis.common.block.item.ItemBlock;
import com.elysiasilly.musalis.common.block.nodeBasedPipes.PipeNodeBlock;
import com.elysiasilly.musalis.common.block.ropeblock.RopeBlock;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MUBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, MusicaUniversalis.MODID);
    public static final DeferredRegister.Items BLOCKITEMS = DeferredRegister.createItems(MusicaUniversalis.MODID);


    //public static final DeferredRegister.Blocks BLOCK = DeferredRegister.createBlocks(Musalis.MODID);

    public static final Supplier<? extends Block> ITEM_BLOCK
            = registerBlockItem("item_block", () -> new ItemBlock(getProperties(Blocks.POLISHED_DEEPSLATE)));


    public static final Supplier<? extends Block> FLUID_BLOCK
            = registerBlockItem("fluid_block", () -> new FluidBlock(getProperties(Blocks.POLISHED_DEEPSLATE)));

    public static final Supplier<? extends Block> FLUID_PIPE_BLOCK
            = registerBlockItem("fluid_pipe_block", () -> new FluidPipeBlock(getProperties(Blocks.POLISHED_DEEPSLATE).noOcclusion()));

    public static final Supplier<? extends Block> PIPE_NODE
            = registerBlockItem("pipe_node", () -> new PipeNodeBlock(getProperties(Blocks.POLISHED_DEEPSLATE).noOcclusion()));

    public static final Supplier<? extends Block> DISSOLVER
            = registerBlockItem("dissolver", () -> new DissolverBlock(getProperties(Blocks.POLISHED_DEEPSLATE)));

    //public static final DeferredBlock<ItemBlock> ITEM_BLOCK = BLOCK.register("milk_canister", () -> new ItemBlock(getProperties(Blocks.POLISHED_DEEPSLATE)));

    public static final Supplier<Block> ASTROM = registerBlockItem("astrom");
    public static final Supplier<Block> RIMESTONE = registerBlockItem("rimestone");


    public static final Supplier<Block> AZRAQ_IVORY = registerBlockItem("azraq_ivory");
    public static final Supplier<Block> AHMAR_IVORY = registerBlockItem("ahmar_ivory");

    public static final Supplier<Block> CRUDE_TANK = registerBlockItem("crude_tank");

    public static final Supplier<? extends Block> TESTING
            = registerBlockItem("testing_block", TestingBlock::new);

    public static final Supplier<? extends Block> ROPE_BLOCK
            = registerBlockItem("rope_block", RopeBlock::new);
    // HELPERS

    // registers block and item if class extends block
    private static Supplier<? extends Block> registerBlockItem(String resourceLocation, Supplier<? extends Block> blockType) {
        var tempBlock = BLOCKS.register(resourceLocation, blockType);
        BLOCKITEMS.registerSimpleBlockItem(tempBlock);
        return tempBlock;
    }

    @SuppressWarnings("unchecked")
    // specifically for block
    private static Supplier<Block> registerBlockItem(String resourceLocation) {
        return (Supplier<Block>) registerBlockItem(resourceLocation, () -> new Block(BlockBehaviour.Properties.of()));
    }

    // returns block properties
    private static BlockBehaviour.Properties getProperties(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }
}
