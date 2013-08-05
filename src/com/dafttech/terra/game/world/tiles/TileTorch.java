package com.dafttech.terra.game.world.tiles;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.graphics.lighting.PointLight;
import com.dafttech.terra.resources.Resources;

public class TileTorch extends Tile {

    PointLight light;

    public TileTorch() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("torch");
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (light == null) light = new PointLight(position.toEntityPos(), 95);

        light.setPosition((Vector2) position.toEntityPos().add(BLOCK_SIZE / 2, BLOCK_SIZE / 2));
    }

    @Override
    public boolean onCollisionWith(Entity entity) {
        return false;
    }

    @Override
    public boolean isLightEmitter() {
        return true;
    }

    @Override
    public PointLight getEmittedLight() {
        return light;
    }

}
