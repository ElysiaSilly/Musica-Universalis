package com.elysiasilly.musalis.common.block.nodeBasedPipes;

import net.minecraft.util.StringRepresentable;

public enum NodeConnectionResult implements StringRepresentable {
    CONNECTION_ADDED    ("Connection Added"),
    CONNECTION_REMOVED   ("Connection Removed"),

    CONNECTION_ALREADY_PRESENT ("Connection Already Present"),
    CONNECTION_NOT_PRESENT ("Connection Not Preset"),
    CONNECTION_INVALID ("Connection Invalid");


    private final String name;

    NodeConnectionResult(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
