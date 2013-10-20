package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources;

public class TileLeaf extends TileLog {
    @Override
    public TileLog getLog() {
        return new TileLeaf();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("leaf");
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
