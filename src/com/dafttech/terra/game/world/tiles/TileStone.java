package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources;

public class TileStone extends Tile {

    public TileStone() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("stone");
    }

}
