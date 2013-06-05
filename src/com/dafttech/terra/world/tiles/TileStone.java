package com.dafttech.terra.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Tile;

public class TileStone extends Tile {

    public TileStone() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("stone");
    }

}
