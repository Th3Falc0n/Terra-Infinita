package com.dafttech.terra.engine.renderer;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.subtiles.Subtile;

public abstract class SubtileRenderer {
    public abstract void draw(AbstractScreen screen, Subtile render, Entity pointOfView, Object... rendererArguments);
}
