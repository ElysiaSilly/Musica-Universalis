package com.elysiasilly.musalis.core.registry;

import com.elysiasilly.musalis.common.blockentity.AstromBE;
import com.elysiasilly.musalis.common.blockentity.RMIBE;
import com.elysiasilly.musalis.common.blockentity.creative.*;
import com.elysiasilly.musalis.core.Musalis;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MUBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCKENTITES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Musalis.MODID);

    public static final Supplier<BlockEntityType<AstromBE>> ASTROM = BLOCKENTITES.register(
            "astrom", () -> BlockEntityType.Builder.of(AstromBE::new, MUBlocks.ASTROM.get()).build(null));

    public static final Supplier<BlockEntityType<RMIBE>> RMI = BLOCKENTITES.register(
            "rmi", () -> BlockEntityType.Builder.of(RMIBE::new, MUBlocks.RMI.get()).build(null));

    public static final Supplier<BlockEntityType<CrEtherDissipatorBE>> CREATIVE_ETHER_DISSIPATOR = BLOCKENTITES.register(
            "creative_ether_dissipator", () -> BlockEntityType.Builder.of(CrEtherDissipatorBE::new, MUBlocks.CREATIVE_ETHER_DISSIPATOR.get()).build(null));

    public static final Supplier<BlockEntityType<CrResonanceDissolverBE>> CREATIVE_RESONANCE_DISSOLVER = BLOCKENTITES.register(
            "creative_resonance_dissolver", () -> BlockEntityType.Builder.of(CrResonanceDissolverBE::new, MUBlocks.CREATIVE_RESONANCE_DISSOLVER.get()).build(null));

    public static final Supplier<BlockEntityType<CrResonanceComposerBE>> CREATIVE_RESONANCE_COMPOSER = BLOCKENTITES.register(
            "creative_resonance_composer", () -> BlockEntityType.Builder.of(CrResonanceComposerBE::new, MUBlocks.CREATIVE_RESONANCE_COMPOSER.get()).build(null));

    public static final Supplier<BlockEntityType<CrDataBankBE>> CREATIVE_NOTE_TANK = BLOCKENTITES.register(
            "creative_note_tank", () -> BlockEntityType.Builder.of(CrDataBankBE::new, MUBlocks.CREATIVE_NOTE_TANK.get()).build(null));

    public static final Supplier<BlockEntityType<CrResonanceMaterialiserBE>> CREATIVE_RESONANCE_MATERIALISER = BLOCKENTITES.register(
            "creative_resonance_materialiser", () -> BlockEntityType.Builder.of(CrResonanceMaterialiserBE::new, MUBlocks.CREATIVE_RESONANCE_MATERIALISER.get()).build(null));

    public static final Supplier<BlockEntityType<CrResonanceRecorderBE>> CREATIVE_RESONANCE_RECORDER = BLOCKENTITES.register(
            "creative_resonance_recorder", () -> BlockEntityType.Builder.of(CrResonanceRecorderBE::new, MUBlocks.CREATIVE_RESONANCE_RECORDER.get()).build(null));
}
