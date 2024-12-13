package com.elysiasilly.musalis.common.world.ether;

import com.elysiasilly.musalis.common.block.sinterface.IEtherCoreHolder;
import com.elysiasilly.musalis.core.key.MUResourceKeys;
import com.elysiasilly.musalis.core.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class EtherCoreEntity extends Entity {

    // todo : bounce and proper rotation
    private final EtherCore CORE;
    //Vec3 rotation;

    public EtherCoreEntity(EntityType<?> entityType, Level level, EtherCore core) {
        super(entityType, level);
        this.CORE = core;
    }

    public EtherCoreEntity(EntityType<EtherCoreEntity> etherCoreEntityEntityType, Level level) {
        super(etherCoreEntityEntityType, level);
        this.CORE = level.registryAccess().registry(MUResourceKeys.registries.ETHER_CORE).flatMap(registry -> registry.getRandom(level.random)).get().value();
        this.setYRot(this.random.nextFloat() * 360.0F);
    }

    public EtherCore getCore() {
        return this.CORE;
    }

    //public Vec3 getRotation() {
    //    return this.rotation;
    //}

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected double getDefaultGravity() {
        return 0.06;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    protected AABB makeBoundingBox() {
        return super.makeBoundingBox();
    }

    /*
    @Override
    public AABB getBoundingBoxForCulling() {
        return super.getBoundingBoxForCulling();
    }
     */

    @Override
    public void tick() {
        //if(!this.onGround()) {
        super.tick();

        this.noPhysics = false;
        this.applyGravity();
        //setDeltaMovement(new Vec3(0, 1, 0));
        //this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());

        BlockPos pos = MathUtil.vec3ToBlockPos(this.position()).below();
        //pos = pos.offset(-1, -1, -1);
        //System.out.println(pos);

        if(level().getBlockEntity(pos) instanceof IEtherCoreHolder holder) {
            //System.out.println("true");

            this.push(holder.snapPosition().subtract(this.position()).multiply(.2, .2, .2));
            //this.moveTo(holder.snapPosition());
            //this.setPos(holder.snapPosition());
        }


        for(Entity entity : this.level().getEntities(null, new AABB(this.position(), this.position()).inflate(0.1).inflate(0, 0.05, 0))) {
            this.push(entity);
        }

        if (!this.onGround() || this.getDeltaMovement().horizontalDistanceSqr() > 9.999999747378752E-6 || (this.tickCount + this.getId()) % 4 == 0) {
            this.move(MoverType.SELF, this.getDeltaMovement());
            float f = 0.98F;
            if (this.onGround()) {
                BlockPos groundPos = this.getBlockPosBelowThatAffectsMyMovement();
                f = this.level().getBlockState(groundPos).getFriction(this.level(), groundPos, this) * 0.98F;
            }

            this.setDeltaMovement(this.getDeltaMovement().multiply((double)f, 0.98, (double)f));
            if (this.onGround()) {
                Vec3 vec31 = this.getDeltaMovement();
                if (vec31.y < 0.0) {
                    this.setDeltaMovement(vec31.multiply(1.0, -0.5, 1.0));
                }
            }
        }

        //this.moveTo(this.position().add(new Vec3(0, 0, .01)));
        //}
    }

    @Override
    public void push(Entity entity) {
        if (!this.isPassengerOfSameVehicle(entity) && !entity.noPhysics && !this.noPhysics) {
            double x = entity.getX() - this.getX();
            double y = entity.getY() - this.getY();
            double z = entity.getZ() - this.getZ();

            double d2 = Mth.absMax(x, z);

            if (d2 >= 0.009999999776482582) {
                d2 = Math.sqrt(d2);
                x /= d2;
                z /= d2;
                double d3 = 1.0 / d2;
                if (d3 > 1.0) {
                    d3 = 1.0;
                }
                x *= d3;
                z *= d3;
                x *= 0.05000000074505806;
                z *= 0.05000000074505806;
                if (!this.isVehicle() && this.isPushable()) {
                    this.push(-x, 0.0, -z);
                    this.setXRot((float) -x * 1000 + this.getXRot());
                    this.setYRot((float) -z * 100 + this.getYRot());
                }
            }
        }

    }
}
