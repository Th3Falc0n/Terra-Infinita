package com.dafttech.terra.game.world.subtiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.resources.Resources;

public class SubtileWater extends SubtileFluid {

    public SubtileWater(Tile t) {
        super(t);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("water");
    }

    @Override
    public SubtileFluid getNewFluid(Tile tile) {
        return new SubtileWater(tile);
    }

    @Override
    public boolean clearFloor() {
        return true;
    }

}
