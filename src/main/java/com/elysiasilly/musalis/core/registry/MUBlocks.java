package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.block.AstromBlock;
import com.elysiasilly.musalis.common.block.RMIBlock;
import com.elysiasilly.musalis.common.block.RimeStoneBlock;
import com.elysiasilly.musalis.common.block.creative.*;
import com.elysiasilly.musalis.core.Musalis;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
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

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks(Musalis.MODID);
    public static final DeferredRegister.Items BLOCKITEMS = DeferredRegister.createItems(Musalis.MODID);

    public static final DeferredBlock<Block> RIMESTONE_BLOCK =
            regWithItem("rimestone_block", () -> new Block(getProp(Blocks.NETHERITE_BLOCK)));

    public static final DeferredBlock<Block> RIMESTONE =
            regWithItem("rimestone", () -> new RimeStoneBlock(getProp(Blocks.NETHERITE_BLOCK)));

    public static final DeferredBlock<Block> ASTROM =
            regWithItem("astrom", () -> new AstromBlock(getProp(Blocks.DEEPSLATE)));

    public static final DeferredBlock<Block> RMI =
            regWithItem("rmi", () -> new RMIBlock(getProp(Blocks.DEEPSLATE)));

    public static final DeferredBlock<Block> CREATIVE_ETHER_DISSIPATOR =
            regWithItem("creative_ether_dissipator", () -> new CrEtherDissipatorBlock(getProp(Blocks.DEEPSLATE)), new Item.Properties().rarity(Rarity.EPIC));

    public static final DeferredBlock<Block> CREATIVE_RESONANCE_COMPOSER =
            regWithItem("creative_resonance_composer", () -> new CrResonanceComposerBlock(getProp(Blocks.DEEPSLATE)), new Item.Properties().rarity(Rarity.EPIC));

    public static final DeferredBlock<Block> CREATIVE_RESONANCE_DISSOLVER =
            regWithItem("creative_resonance_dissolver", () -> new CrResonanceDissolverBlock(getProp(Blocks.DEEPSLATE)), new Item.Properties().rarity(Rarity.EPIC));

    public static final DeferredBlock<Block> CREATIVE_NOTE_TANK =
            regWithItem("creative_note_tank", () -> new CrEtherDissipatorBlock(getProp(Blocks.DEEPSLATE)), new Item.Properties().rarity(Rarity.EPIC));

    public static final DeferredBlock<Block> CREATIVE_RESONANCE_MATERIALISER =
            regWithItem("creative_resonance_materialiser", () -> new CrResonanceMaterialiserBlock(getProp(Blocks.DEEPSLATE)), new Item.Properties().rarity(Rarity.EPIC));

    public static final DeferredBlock<Block> CREATIVE_RESONANCE_RECORDER =
            regWithItem("creative_resonance_recorder", () -> new CrResonanceRecorderBlock(getProp(Blocks.DEEPSLATE)), new Item.Properties().rarity(Rarity.EPIC));

    /// register block and item
    @SuppressWarnings({"unchecked"})
    private static <T extends Block> DeferredBlock<T> regWithItem(String id, Supplier<? extends Block> blockType) {
        var tempBlock = BLOCKS.register(id, blockType);
        BLOCKITEMS.registerSimpleBlockItem(tempBlock);
        return (DeferredBlock<T>) tempBlock;
    }

    @SuppressWarnings({"unchecked"})
    private static <T extends Block> DeferredBlock<T> regWithItem(String id, Supplier<? extends Block> blockType, Item.Properties itemProperties) {
        var tempBlock = BLOCKS.register(id, blockType);
        BLOCKITEMS.registerSimpleBlockItem(tempBlock, itemProperties);
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
