package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.resources.Resources;

public class TileDirt extends Tile implements ITileRenderBigger {
    public TileDirt() {
        super();
        setHardness(3);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("dirt");
    }

    @Override
    public Vector2i getRenderSizeMultiplier() {
        return new Vector2i(2, 2);
    }
}
