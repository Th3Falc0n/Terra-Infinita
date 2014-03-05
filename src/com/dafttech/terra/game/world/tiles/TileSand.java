package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class TileSand extends TileFalling {

    public TileSand() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("sand");
    }

    @Override
    public float getFallSpeed(World world) {
        return 10;
    }

    @Override
    public float getFallDelay(World world) {
        return 0.2f;
    }
}
