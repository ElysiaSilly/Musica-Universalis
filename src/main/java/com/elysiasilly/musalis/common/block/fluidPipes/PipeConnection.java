package com.elysiasilly.musalis.common.block.fluidPipes;

import net.minecraft.util.StringRepresentable;

public enum PipeConnection implements StringRepresentable {
    NONE    ("none",    false),
    PIPE    ("pipe",    true),
    MACHINE ("machine", true);

    private final String name;
    private final boolean hasConnection;

    private PipeConnection(String name, boolean hasConnection) {
        this.name = name;
        this.hasConnection = hasConnection;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public boolean hasConnection() {
        return this.hasConnection;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
