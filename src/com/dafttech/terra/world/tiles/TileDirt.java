package com.dafttech.terra.world.tiles;

import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.subtiles.SubtileBone;

public class TileDirt extends Tile {

    public TileDirt(Position pos) {
        super(pos, Resources.TILES.getImage("dirt"));
        
        if(TerraInfinita.rnd.nextDouble() < 0.004) {
            this.addSubtile(new SubtileBone());
        }
    }
    
}
