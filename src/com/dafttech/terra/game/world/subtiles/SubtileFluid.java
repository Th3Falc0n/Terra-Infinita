package com.dafttech.terra.game.world.subtiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.tiles.Tile;

public class SubtileFluid extends Subtile {
    float height = 0;

    public SubtileFluid(Tile t) {
        super(t);
    }

    @Override
    public TextureRegion getImage() {
        return null;
    }

}
