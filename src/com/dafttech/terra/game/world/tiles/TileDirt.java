package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.subtiles.SubtileBone;
import com.dafttech.terra.resources.Resources;

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
