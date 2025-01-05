package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.common.interactibles.InteractableManager;
import com.elysiasilly.musalis.common.interactibles.managers;
import com.elysiasilly.musalis.util.MCUtil;
import com.elysiasilly.musalis.util.MathUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EtherCoreManager extends InteractableManager<EtherCoreObject> {

    public EtherCoreManager() {
        super(Tick.PRE, "ether_cores");
    }

    @Override
    public Codec<EtherCoreObject> getCodec() {
        return EtherCoreObject.codec.CODEC;
    }

    @Override
    public EtherCoreManager load(CompoundTag compoundTag, HolderLookup.Provider provider) {
        EtherCoreManager manager = create();

        int size = compoundTag.getInt("size");

        for(int i = 1; i <= size; i++) {
            EtherCoreObject result = EtherCoreObject.codec.CODEC.parse(NbtOps.INSTANCE, compoundTag.get(String.valueOf(i))).getOrThrow();
            result.manager = manager;
            manager.interactables.add(result);
        }

        return manager;
    }

    @Override
    public EtherCoreManager create() {
        return new EtherCoreManager();
    }

    public void addCore(Level level, Vec3 pos) {
        this.interactables.add(new EtherCoreObject(level, Vec3.ZERO, pos));
        setDirty();
    }

    @Override
    public CompoundTag save(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putInt("size", this.interactables.size());

        int index = 1;
        for(EtherCoreObject core : this.interactables) {
            compoundTag.put(String.valueOf(index), EtherCoreObject.codec.CODEC.encodeStart(NbtOps.INSTANCE, core).getOrThrow());
            index++;
        }

        return compoundTag;
    }


    static Vec3 previous;

    public static void drag(Player player, Level level) {
        List<Vec3> raycast = MCUtil.raycast.shittyRayCast(player, MCUtil.raycast.GOOD_ENOUGH);

        if(level instanceof ServerLevel serverLevel) {

            EtherCoreManager manager = (EtherCoreManager) managers.MANAGERS.getFirst(); //EtherCoreManager.get(serverLevel);

            for(EtherCoreObject object : manager.interactables) {

                for(Vec3 point : raycast) {
                    if(MathUtil.withinBounds(point, MathUtil.vectors.add(object.pos(), -.5), MathUtil.vectors.add(object.pos(), .5))) {
                        if(previous == null) previous = point;
                        //object.setVelocity(MathUtil.vector.multiply(point.subtract(previous), 2));
                        object.pos(MathUtil.vectors.offset(player.getEyePosition(), player.getLookAngle(), 3));
                        //System.out.println(MathUtil.vector.multiply(point.subtract(previous), 2));
                        //if(!point.equals(previous)) object.setPosition(point);
                        previous = point;
                    }
                }
            }
        }
    }

}
