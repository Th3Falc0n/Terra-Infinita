package com.dafttech.terra.engine.renderer;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public abstract class TileRenderer {
    public abstract void draw(Vector2 pos, World world, AbstractScreen screen, Tile render, Entity pointOfView, Object... rendererArguments);
}
