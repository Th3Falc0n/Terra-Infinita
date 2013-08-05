package com.dafttech.terra.graphics.renderer;

import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.subtiles.Subtile;
import com.dafttech.terra.graphics.AbstractScreen;

public abstract class SubtileRenderer {
    public abstract void draw(AbstractScreen screen, Subtile render, Entity pointOfView, Object... rendererArguments);
}
