package com.dafttech.terra.engine.renderer;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public abstract class TileRenderer {
    public abstract void draw(AbstractScreen screen, Tile render, Entity pointOfView, Object... rendererArguments);
}