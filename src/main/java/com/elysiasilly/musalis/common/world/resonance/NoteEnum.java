package com.elysiasilly.musalis.common.world.resonance;

import net.minecraft.util.StringRepresentable;

public enum NoteEnum implements StringRepresentable {
    NATURAL("natural"),
    WILD("wild"),
    EXOTIC("exotic");

    private final String name;

    NoteEnum(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
