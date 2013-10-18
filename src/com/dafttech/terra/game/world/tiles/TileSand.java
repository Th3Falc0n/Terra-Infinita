package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources;

public class TileSand extends TileFalling {

    public TileSand() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("sand");
    }
}
