package com.dafttech.terra.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.subtiles.SubtileBone;

public class TileDirt extends Tile {

    public TileDirt() {
        super();

        if (TerraInfinita.rnd.nextDouble() < 0.004) {
            this.addSubtile(new SubtileBone());
        }
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("dirt");
    }

}
