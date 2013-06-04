package com.dafttech.terra.world.subtiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Subtile;
import com.dafttech.terra.world.Tile;

public class SubtileGrass extends Subtile {

    public SubtileGrass() {
        super(null, Resources.TILES.getImage("mask_grass"));
    }
    
}
