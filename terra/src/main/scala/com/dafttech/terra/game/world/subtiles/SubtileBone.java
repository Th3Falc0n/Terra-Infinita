package com.dafttech.terra.game.world.subtiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources$;

public class SubtileBone extends Subtile {
    public SubtileBone() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.TILES().getImage("bone");
    }

}