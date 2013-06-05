package com.dafttech.terra.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.World;

public class TileStone extends Tile {

<<<<<<< HEAD
    public TileStone() {
        super();
=======
    public TileStone(Position pos, World world) {
        super(pos, world);
>>>>>>> branch 'master' of https://github.com/Th3Falc0n/Terra-Infinita.git
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("stone");
    }

}
