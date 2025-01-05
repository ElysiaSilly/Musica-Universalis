package com.elysiasilly.musalis.common.world.astrom;

import com.elysiasilly.musalis.common.interactibles.Interactable;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

public class AstromMassObject extends Interactable<AstromMassManager> {

    public final AstromMass mass;

    public AstromMassObject(AstromMass mass) {
        this.mass = mass;
    }


    @Override
    public void render(RenderLevelStageEvent event) {

    }

    @Override
    public void tick(Level level) {

    }
}
