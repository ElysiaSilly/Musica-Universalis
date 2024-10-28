package com.elysiasilly.musalis.common.block.nodeBasedPipes;

import com.elysiasilly.musalis.common.block.base.FluidBlockBE;
import com.elysiasilly.musalis.core.registry.MUBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import org.joml.Vector3i;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

public class PipeNodeBE extends FluidBlockBE {

    private float pressure = 0;

    private final String NBT = "NodeTank";
    private final String NBT2 = "NodeConnections";

    private final Dictionary<BlockPos, NodeConnectionData> connectedNodes = new Hashtable<>();

    public PipeNodeBE(BlockPos pos, BlockState blockState) {
        super(MUBlockEntities.PIPE_NODE_BE.get(), pos, blockState);
    }

    public NodeConnectionResult addConnection(BlockPos nodePos) {

        if(nodePos.equals(getBlockPos())) return NodeConnectionResult.CONNECTION_INVALID;

        if(level.getBlockEntity(nodePos) instanceof PipeNodeBE node) {
            if (connectedNodes.get(nodePos) == null) {

                int distance = (int) (Math.floor(Vector3i.distance(
                        getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(),
                        nodePos.getX(), nodePos.getY(), nodePos.getZ())));

                int properDistance = (int) ((Vector3i.distance(
                                                        getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(),
                                                        nodePos.getX(), nodePos.getY(), nodePos.getZ()) - 1) / Math.sqrt(2));

                //System.out.println(properDistance);

                int heightDifference = nodePos.getY() - getBlockPos().getY();

                connectedNodes.put(nodePos, new NodeConnectionData(node, distance, heightDifference)); // TODO : YOU


                node.addConnection(getBlockPos());
                return NodeConnectionResult.CONNECTION_ADDED;
            } else {
                return NodeConnectionResult.CONNECTION_ALREADY_PRESENT;
            }
        }

        return NodeConnectionResult.CONNECTION_INVALID;
    }

    public NodeConnectionResult removeConnection(BlockPos nodePos) {
        connectedNodes.remove(nodePos);

        if(level.getBlockEntity(nodePos) instanceof PipeNodeBE be) {
            if(be.getConnectedNodes().get(getBlockPos()) != null) {
                be.removeConnection(getBlockPos());
                return NodeConnectionResult.CONNECTION_REMOVED;
            } else {
                return NodeConnectionResult.CONNECTION_NOT_PRESENT;
            }
        }

        return NodeConnectionResult.CONNECTION_INVALID;
    }

    @Override
    public void setRemoved() {
        removeAllConnections();
        super.setRemoved();
    }

    public void removeAllConnections() {

        if(getConnectedNodes().isEmpty()) return;

        Enumeration<BlockPos> k = getConnectedNodes().keys();

        while (k.hasMoreElements()) {

            BlockPos key = k.nextElement();

            if(level.getBlockEntity(key) instanceof PipeNodeBE connectedNode) connectedNode.removeConnection(getBlockPos());
        }

    }

    public Dictionary<BlockPos, NodeConnectionData> getConnectedNodes() {
        return connectedNodes;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float newPressure) {
        pressure = newPressure;
    }

    public void tickServer() {

        //assert level != null;
        if (level.getGameTime() % 10 != 0) return;

        pressure = (float) ((double) getFluidHandler().getFluidAmount() / getFluidHandler().getCapacity());

        if(level instanceof ServerLevel serverLevel) {
            IFluidHandler handler = serverLevel.getCapability(Capabilities.FluidHandler.BLOCK, getBlockPos().above(), Direction.DOWN);
            if(handler != null) {


                if(handler.getFluidInTank(0).isEmpty()) {
                    FluidStack drainedFluid = drainFluid(getFluidHandler().getFluid(), IFluidHandler.FluidAction.EXECUTE);

                    System.out.println("draining my tank: " + drainedFluid);

                    handler.fill(drainedFluid, IFluidHandler.FluidAction.EXECUTE);
                } else {
                    FluidStack drainedFluid = drainFluid(getFluidHandler().getFluid(), IFluidHandler.FluidAction.EXECUTE);

                    System.out.println("draining above tank: " + drainedFluid);

                    getFluidHandler().fill(drainedFluid, IFluidHandler.FluidAction.EXECUTE);
                }
            }
        }
        //getFluidHandler().drain(10, IFluidHandler.FluidAction.EXECUTE);


        if(!getConnectedNodes().isEmpty()) {
            /*
            NodeConnectionData data1 = connectedNodes.elements().nextElement();

            PipeNodeBE node = data1.node();

            if(getPressure() > node.getPressure()) {

                FluidStack drainedFluid = drainFluid((int) (getFluidHandler().getFluidAmount() * getPressure()), IFluidHandler.FluidAction.EXECUTE);

                //FluidStack drainedFluid = getFluidHandler().drain((int) (getFluidHandler().getFluidAmount() * getPressure()), IFluidHandler.FluidAction.EXECUTE);

                node.getFluidHandler().fill(drainedFluid, IFluidHandler.FluidAction.EXECUTE);

            }

             */

            // yeah

            Enumeration<BlockPos> k = getConnectedNodes().keys();
            int connections = getConnectedNodes().size();
            int fluidToDrain = (int) (getFluidHandler().getFluidAmount() * getPressure()) / connections;

            while (k.hasMoreElements()) {

                BlockPos key = k.nextElement();

                NodeConnectionData data = connectedNodes.get(key);

                //if (level.getGameTime() % data.distance() != 0) return;

                pressure = (float) ((double) getFluidHandler().getFluidAmount() / getFluidHandler().getCapacity());

                if(data.node().getFluidHandler().getCapacity() != data.node().getFluidHandler().getFluidAmount()) {

                    //System.out.println("the node i wanna push into isnt full");
                    /*
                    if(data.heightDifference() > 0 && getFluidHandler().getFluidAmount() == getFluidHandler().getCapacity()) {
                        FluidStack drainedFluid = drainFluid((int) (getFluidHandler().getFluidAmount() * getPressure()) + data.heightDifference(), IFluidHandler.FluidAction.EXECUTE);

                        data.node().getFluidHandler().fill(drainedFluid, IFluidHandler.FluidAction.EXECUTE);
                    }

                     */

                    if(data.heightDifference() <= 0) {
                        FluidStack drainedFluid = drainFluid((int) (getFluidHandler().getFluidAmount() * getPressure()) + (Math.abs(data.heightDifference() * 8)), IFluidHandler.FluidAction.EXECUTE);
                        //System.out.println("the node i wanna push into isnt full");
                        data.node().getFluidHandler().fill(drainedFluid, IFluidHandler.FluidAction.EXECUTE);
                    }
                }
            }
        }
    }

    public int fillFluid(FluidStack fluid, IFluidHandler.FluidAction action) {
        if(getBlockState().getValue(PipeNodeBlock.LOCKED)) return 0;
        return getFluidHandler().fill(fluid, action);
    }

    public FluidStack drainFluid(FluidStack fluid, IFluidHandler.FluidAction action) {
        if(getBlockState().getValue(PipeNodeBlock.LOCKED)) return new FluidStack(Fluids.EMPTY, 0);
        return getFluidHandler().drain(fluid, action);
    }

    public FluidStack drainFluid(int fluid, IFluidHandler.FluidAction action) {
        if(getBlockState().getValue(PipeNodeBlock.LOCKED)) return new FluidStack(Fluids.EMPTY, 0);
        return getFluidHandler().drain(fluid, action);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
    }
}
