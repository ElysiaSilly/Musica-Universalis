package com.elysiasilly.musalis.common.block.be;

import com.elysiasilly.musalis.common.world.astrom.AstromBlockMass;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class AstromBE extends BlockEntity {

    AstromBlockMass astromBlockMass;

    public AstromBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.ASTROM.get(), pos, blockState);
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);


        if(level.isClientSide) return;

        if(level.getBlockEntity(getBlockPos().north()) instanceof AstromBE be) {
            if(be.getAstromBlockMass() != null) {
                be.getAstromBlockMass().add(this);
                this.astromBlockMass = be.getAstromBlockMass();
                return;
            }
        }
        if(level.getBlockEntity(getBlockPos().south()) instanceof AstromBE be) {
            if(be.getAstromBlockMass() != null) {
                be.getAstromBlockMass().add(this);
                this.astromBlockMass = be.getAstromBlockMass();
                return;

            }
        }
        if(level.getBlockEntity(getBlockPos().east()) instanceof AstromBE be) {
            if(be.getAstromBlockMass() != null) {
                be.getAstromBlockMass().add(this);
                this.astromBlockMass = be.getAstromBlockMass();
                return;

            }
        }
        if(level.getBlockEntity(getBlockPos().west()) instanceof AstromBE be) {
            if(be.getAstromBlockMass() != null) {
                be.getAstromBlockMass().add(this);
                this.astromBlockMass = be.getAstromBlockMass();
                return;

            }
        }

        if(this.astromBlockMass == null) {
            this.astromBlockMass = new AstromBlockMass(level, this);
        }
    }

    public AstromBlockMass getAstromBlockMass() {
        return this.astromBlockMass;
    }

    public void tick() {
        if(level == null) return;
        if(level.isClientSide) return;

        if(this.astromBlockMass == null) return;

        if(this.astromBlockMass.getMain().getBE().equals(this)) {
            this.astromBlockMass.tick();
        }

    }

    @Override
    public void setRemoved() {

        if(level != null) {
            if(!level.isClientSide) {
                if(this.astromBlockMass!= null) {
                    this.astromBlockMass.get(this).destroyManual();;
                }
            }
        }

        super.setRemoved();
    }
}
