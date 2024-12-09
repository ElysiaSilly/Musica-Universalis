package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.block.AstromBlock;
import com.elysiasilly.musalis.common.block.CoreHolderBlock;
import com.elysiasilly.musalis.core.MusicaUniversalis;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.function.Supplier;

public class MUBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(MusicaUniversalis.MODID);
    public static final DeferredRegister.Items BLOCKITEMS = DeferredRegister.createItems(MusicaUniversalis.MODID);


    public static final DeferredBlock<Block> ASTROM =
            regWithItem("astrom", () -> new AstromBlock(getProp(Blocks.DEEPSLATE)));

    public static final DeferredBlock<Block> COREHOLDER =
            regWithItem("coreholder", () -> new CoreHolderBlock(getProp(Blocks.DEEPSLATE)));

    /// register block and item
    @SuppressWarnings({"unchecked"})
    private static <T extends Block> DeferredBlock<T> regWithItem(String id, Supplier<? extends Block> blockType) {
        var tempBlock = BLOCKS.register(id, blockType);
        BLOCKITEMS.registerSimpleBlockItem(tempBlock);
        return (DeferredBlock<T>) tempBlock;
    }

    /// register block
    @SuppressWarnings({"unchecked"})
    private static <T extends Block> DeferredBlock<T> reg(String id, Supplier<? extends Block> blockType) {
        return (DeferredBlock<T>) BLOCKS.register(id, blockType);
    }

    /// register colour set of block and item
    @SuppressWarnings({"unchecked"})
    private static <T extends Block> Dictionary<DyeColor, DeferredBlock<T>> regDyeSet(String id, Supplier<? extends Block> blockType, boolean withItem) {
        Dictionary<DyeColor, DeferredBlock<T>> blocks = new Hashtable<>();

        for(DyeColor colour : DyeColor.values()) {
            String name = String.format(id, colour.getName());
            Supplier<? extends Block> block = withItem ? regWithItem(name, blockType) : reg(name, blockType);
            blocks.put(colour, (DeferredBlock<T>) block);
        }
        return blocks;
    }

    /// returns block properties
    private static BlockBehaviour.Properties getProp(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }

    private static BlockBehaviour.Properties getProp(DeferredBlock<Block> block) {
        return BlockBehaviour.Properties.ofFullCopy(block.get());
    }

    private static BlockState getState(DeferredBlock<Block> block) {
        return block.get().defaultBlockState();
    }
}
