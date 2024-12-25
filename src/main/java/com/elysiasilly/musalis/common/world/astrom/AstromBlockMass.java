package com.elysiasilly.musalis.common.world.astrom;

import com.elysiasilly.musalis.common.block.be.AstromBE;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class AstromBlockMass {

    final Level level;
    AstromBlock main;
    List<AstromBlock> blocks = new ArrayList<>();
    final float colour;

    public AstromBlockMass(Level level, AstromBE be) {//, AstromBlock main) {
        this.level = level;
        AstromBlock block = new AstromBlock(be, true, this);
        this.blocks.add(block);
        this.main = block;
        this.colour = level.random.nextFloat();
    }

    public void add(AstromBE be) {
        this.blocks.add(new AstromBlock(be, false, this));
    }

    public void assignNewMain() {
        if(blocks.isEmpty()) return;
        int i = level.random.nextInt(this.blocks.size());
        this.main = blocks.get(i);
        main.setMain();;
    }

    public void tick() {
        for(AstromBlock block : blocks) {
            block.tick();
        }
    }

    public int howMany() {
        return this.blocks.size();
    }

    public void breakAll() {
        for(AstromBlock block : this.blocks) {
            block.breakBlock();
        }
    }

    public AstromBlock getMain() {
        return this.main;
    }

    public AstromBlock get(AstromBE be) {
        for(AstromBlock block : this.blocks) {
            if(block.getBE().equals(be)) return block;
        }

        return null;
    }

    public static class AstromBlock {

        AstromBlockMass mass;
        final AstromBE be;
        boolean isMain;

        public AstromBlock(AstromBE blockEntity, boolean isMain, AstromBlockMass mass) {
            this.mass = mass;
            this.be = blockEntity;
            this.isMain = isMain;
        }

        public void breakBlock() {
            this.mass.level.destroyBlock(be.getBlockPos(), false);
        }

        public void setMain() {
            this.isMain = true;
            this.mass.main = this;
        }

        public void destroyManual() {
            this.mass.blocks.remove(this);
            if(isMain) this.mass.assignNewMain();
        }

        public void tick() {
            //destroyTick();

            if(Minecraft.getInstance().level == null) return;

            if(this.isMain) {
                //Minecraft.getInstance().level.addParticle(ParticleTypes.NOTE, be.getBlockPos().getX() + .5, be.getBlockPos().getY() + 2, be.getBlockPos().getZ() + .5, (double) this.mass.colour, 0, 0);
            } else {
                //Minecraft.getInstance().level.addParticle(ParticleTypes.NOTE, be.getBlockPos().getX() + .5, be.getBlockPos().getY() + 1, be.getBlockPos().getZ() + .5, (double) this.mass.colour, 0, 0);
            }

        }

        public void destroyTick() {
            if(this.be.isRemoved()) {
                destroyManual();
            }
        }

        public AstromBE getBE() {
            return this.be;
        }
    }
}
