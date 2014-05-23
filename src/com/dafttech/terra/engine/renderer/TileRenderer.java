package com.dafttech.terra.engine.renderer;

import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public abstract class TileRenderer {
    protected Vector2 offset = new Vector2();

    public abstract void draw(Vector2 pos, World world, AbstractScreen screen, Tile render, Entity pointOfView, Object... rendererArguments);

    public void setOffset(Vector2 offset) {
        this.offset = offset;
    }

    public Vector2 getOffset() {
        return offset;
    }
}
