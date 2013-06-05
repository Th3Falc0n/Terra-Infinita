package com.dafttech.terra.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.World;

public class TileStone extends Tile {

    public TileStone(Position pos, World world) {
        super(pos, world);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("stone");
    }

}
