package com.dafttech.terra.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.resources.Resources;
<<<<<<< HEAD
=======
import com.dafttech.terra.world.Entity;
import com.dafttech.terra.world.Position;
>>>>>>> branch 'master' of https://github.com/Th3Falc0n/Terra-Infinita.git
import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.World;

public class TileGrass extends Tile {
<<<<<<< HEAD
    public TileGrass() {
        super();
=======
    int grassIndex;
    
    public TileGrass(Position pos, World world) {
        super(pos, world);
        
        grassIndex = TerraInfinita.rnd.nextInt(5);
>>>>>>> branch 'master' of https://github.com/Th3Falc0n/Terra-Infinita.git
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("grass" + grassIndex);
    }

    @Override
    public boolean onCollisionWith(Entity entity) {
        return false;
    }
}
