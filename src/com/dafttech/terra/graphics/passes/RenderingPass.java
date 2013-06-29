package com.dafttech.terra.graphics.passes;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.entities.Entity;

public abstract class RenderingPass {
    public static PassObjects rpObjects = new PassObjects();
    public static PassLighting rpLighting = new PassLighting();
    public static PassGaussian rpGaussian = new PassGaussian();

    public abstract void applyPass(AbstractScreen screen, Entity pointOfView, World w, Object... arguments);
}
