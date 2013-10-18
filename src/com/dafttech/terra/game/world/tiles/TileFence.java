package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.resources.Resources;

public class TileFence extends Tile {

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("fence");
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return !(entity instanceof Player);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
