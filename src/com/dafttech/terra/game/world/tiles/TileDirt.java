package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.renderer.TileRenderer;
import com.dafttech.terra.engine.renderer.TileRendererMultiblock;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.resources.Resources;

public class TileDirt extends Tile {
    public TileDirt() {
        super();
        setHardness(3);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("dirt");
    }

    @Override
    public TileRenderer getRenderer() {
        return new TileRendererMultiblock(new Vector2i(2, 2));
    }
}
