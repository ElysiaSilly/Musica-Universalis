package com.elysiasilly.babel.client.gui;

import net.minecraft.client.Camera;

public interface IModifyCameraScreen {

    void camera(Camera camera);

    boolean hideElements();
}
