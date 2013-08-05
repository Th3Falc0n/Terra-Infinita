package com.dafttech.terra.graphics.renderer;

import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.graphics.AbstractScreen;

public abstract class TileRenderer {
    public abstract void draw(AbstractScreen screen, Tile render, Entity pointOfView, Object... rendererArguments);
}
