package com.elysiasilly.musalis.common.ether;

import com.elysiasilly.musalis.common.world.ether.Ether;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Energy extends Ether {

    @Override
    public void passiveDissipate(ItemStack stack, BlockPos pos, Level level) {
        int x = level.random.nextIntBetweenInclusive(-10, 10);
        int y = level.random.nextIntBetweenInclusive(-10, 10);
        int z = level.random.nextIntBetweenInclusive(-10, 10);


        pos = pos.offset(x, y, z);

        if(level.getBlockState(pos).canBeReplaced() && level.getBlockState(pos.below()).isSolid() && !level.isClientSide) {
            if(level instanceof ServerLevel serverLevel) {
                LightningBolt lightning = EntityType.LIGHTNING_BOLT.spawn(serverLevel, pos, MobSpawnType.NATURAL);
                assert lightning != null;
                level.addFreshEntity(lightning);
            }
        }

        Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 10, false);
        if(player == null) return;
        player.displayClientMessage(Component.literal("Ether is leaking..."), true);
        player.level().addParticle(ParticleTypes.SMALL_GUST, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);

    }

    @Override
    public void volatileDissipate(ItemStack stack, BlockPos pos, Level level) {

    }
}
