package com.elysiasilly.musalis.core.util;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class MCUtil {

    public static class item {

        public static boolean isEmpty(ItemStack...stacks) {
            for(ItemStack stack : stacks) {
                if(stack == null) return true;
                if(stack.isEmpty()) return true;
            }
            return false;
        }

        public static void serialize(String id, ItemStack stack, CompoundTag compoundTag, HolderLookup.Provider registries) {
            if(isEmpty(stack)) return;
            compoundTag.put(id, stack.save(registries, compoundTag));
        }

        public static ItemStack deserialize(String id, CompoundTag compoundTag, HolderLookup.Provider registries) {
            Tag tag = compoundTag.get(id);
            if(tag == null) return ItemStack.EMPTY;
            Optional<ItemStack> stack = ItemStack.parse(registries, tag);
            return stack.orElse(ItemStack.EMPTY);
        }
    }

    public static class raycast {

        // todo

        public static Vec3 shittyRayCast(Entity entity, float range, float precision) {
            return shittyRayCast(entity.getEyePosition(), entity.getLookAngle(), range, precision, entity.level());
        }

        public static Vec3 shittyRayCast(Vec3 start, Vec3 direction, float range, float precision, Level level) {

            Vec3 end = start.add(direction.multiply(new Vec3(range, range, range)));

            Vec3 ray = start;
            for(float i = 0; i <= 1; i+= precision) {
                Vec3 temp = start.lerp(end, i);
                if(level.getBlockState(MathUtil.vec3ToBlockPos(temp)).isSolid()) return temp;
                ray = temp;
            }

            return ray;
        }
    }

}
