package com.elysiasilly.musalis.networking;

import com.elysiasilly.musalis.common.world.rimestar.RimestarChunkWeight;
import com.elysiasilly.musalis.networking.payloads.RimestarChunkWeightPayload;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {

    public static void RimestarChunkWeightHandler(final RimestarChunkWeightPayload data, final IPayloadContext context) {
        RimestarChunkWeight.weights.put(new BlockPos(data.x(), 0, data.z()), data.weight());
    }
}
