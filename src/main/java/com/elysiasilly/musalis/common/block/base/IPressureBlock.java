package com.elysiasilly.musalis.common.block.base;

public interface IPressureBlock {

    float pressure = 0;

    default float getPressure() {
        return pressure;
    }

    default void setPressure(float newPressure) {
        //pressure = newPressure;
    }

    default void addPressure(float pressure) {
        setPressure(getPressure() + pressure);
    }
}
