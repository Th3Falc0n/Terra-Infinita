package com.dafttech.terra.graphics.renderer;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.entities.Entity;
import com.dafttech.terra.world.tiles.Tile;

public abstract class TileRenderer {
    public abstract void draw(AbstractScreen screen, Tile render, Entity pointOfView, Object... rendererArguments);
}
