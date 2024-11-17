package com.elysiasilly.musalis.common.block.dissolver;

import com.elysiasilly.musalis.common.resonance.Resonance;
import com.elysiasilly.musalis.common.resonance.ResonanceDataMap;
import com.elysiasilly.musalis.common.resonance.String;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DissolverBlock extends BaseEntityBlock {
    public DissolverBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DissolverBE(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.isClientSide) return ItemInteractionResult.SUCCESS;

        if(level.getBlockEntity(pos) instanceof DissolverBE be) {

            Holder<Item> item = stack.getItemHolder();
            ResonanceDataMap data = item.getData(ResonanceDataMap.DATAMAP);
            if(data != null) {

                List<Holder<String>> crawl = new ArrayList<>();

                System.out.println(data.getResonance().value());

                crawl.addAll(data.getResonance().value().crawlStrings());
                if(be.getStore() != null) crawl.addAll(be.getStore().crawlStrings());

                List<Holder<Resonance>> resonance = new ArrayList<>();

                Resonance combined = new Resonance(crawl, resonance);

                be.setStore(combined);

                System.out.println(be.getStore());


            } else if (!player.isShiftKeyDown()) {

                level.registryAccess().registryOrThrow(MUResourceKeys.RESONANCE).stream().forEach(resonance -> {

                    if(resonance.equals(be.getStore())) System.out.println(resonance);

                });
            }


            if(player.isShiftKeyDown()) {
                System.out.println(be.getStore().crawlStrings());
            }
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if(level.getBlockEntity(pos) instanceof DissolverBE be) {
            if(player.isShiftKeyDown()) {
                System.out.println(be.getSoup());
            }
        }
     return InteractionResult.SUCCESS;
    }
}
