package com.dafttech.terra.game.world.subtiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class SubtileDryGrass extends SubtileGrass {
    public SubtileDryGrass() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("mask_grass_dry");
    }

    @Override
    public void spread(World world) {
    }
}
