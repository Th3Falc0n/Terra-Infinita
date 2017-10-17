package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources$;

public class TileAir extends Tile {

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.TILES().getImage("air");
    }

    @Override
    public boolean isAir() {
        return true;
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub

    }
}
