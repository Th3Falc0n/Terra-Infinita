package com.dafttech.terra.world.tiles;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.graphics.lighting.PointLight;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.entities.Entity;

public class TileTorch extends Tile {
    int grassIndex;

    PointLight light;

    public TileTorch() {
        super();
        grassIndex = TerraInfinita.rnd.nextInt(5);
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
