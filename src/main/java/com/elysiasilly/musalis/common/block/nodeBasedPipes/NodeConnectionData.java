package com.elysiasilly.musalis.common.block.nodeBasedPipes;

public class NodeConnectionData {

    private final PipeNodeBE node;
    private final int distance;
    private final int heightDifference;

    public NodeConnectionData(PipeNodeBE node, int distance, int heightDifference) {
        this.node = node;
        this.distance = distance;
        this.heightDifference = heightDifference;
    }

    public PipeNodeBE node() {
        return node;
    }

    public int distance() {
        return distance;
    }

    public int heightDifference() {
        return heightDifference;
    }
}
