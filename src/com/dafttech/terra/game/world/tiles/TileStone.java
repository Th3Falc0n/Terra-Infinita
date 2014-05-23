package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.resources.Resources;

public class TileStone extends Tile implements ITileRenderBigger {
    public TileStone() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("stone");
    }

    @Override
    public Vector2i getRenderSizeMultiplier() {
        return new Vector2i(2, 2);
    }
}
