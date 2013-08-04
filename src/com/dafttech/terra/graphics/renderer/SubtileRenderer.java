package com.dafttech.terra.graphics.renderer;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.entities.Entity;
import com.dafttech.terra.world.subtiles.Subtile;

public abstract class SubtileRenderer {
    public abstract void draw(AbstractScreen screen, Subtile render, Entity pointOfView, Object... rendererArguments);
}
