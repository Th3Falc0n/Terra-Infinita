package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.resources.Resources;

public class TileGrass extends Tile {
    int grassIndex;

    public TileGrass() {
        super();
        grassIndex = TerraInfinita.rnd.nextInt(5);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("grass", grassIndex);
    }

    @Override
    public boolean onCollisionWith(Entity entity) {
        return false;
    }
}
