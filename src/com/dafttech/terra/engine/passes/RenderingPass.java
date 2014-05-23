package com.dafttech.terra.engine.passes;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public abstract class RenderingPass {
    public static PassObjects rpObjects = new PassObjects();
    public static PassLighting rpLighting = new PassLighting();
    public static PassGaussian rpGaussian = new PassGaussian();
    public static PassGUIContainer rpGUIContainer = new PassGUIContainer();

    public abstract void applyPass(AbstractScreen screen, Entity pointOfView, World w, Object... arguments);
    
    public RenderingPass() {
        Events.EVENTMANAGER.registerEventListener(this);
    }
}
