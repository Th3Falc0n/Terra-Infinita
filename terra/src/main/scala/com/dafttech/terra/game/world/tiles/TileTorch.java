package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.particles.ParticleSpark;
import com.dafttech.terra.resources.Options;
import com.dafttech.terra.resources.Resources$;

public class TileTorch extends TileFalling {
    PointLight light;

    public TileTorch() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.TILES().getImage("torch");
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        if (light == null) light = new PointLight(getPosition().toEntityPos(), 95);

        light.setPosition(getPosition().toEntityPos().$plus(Options.BLOCK_SIZE() / 2, Options.BLOCK_SIZE() / 2));

        for (int i = 0; i < 5; i++) {
            if (TerraInfinita.rnd().nextDouble() < delta * 0.5f) {
                new ParticleSpark(getPosition().toEntityPos().$plus(Options.BLOCK_SIZE() / 2, 0), world).addVelocity(new Vector2(0, -1));
            }
        }
    }

    @Override
    public boolean isCollidableWith(Entity entity) {
        return false;
    }

    @Override
    public boolean isOpaque() {
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

    @Override
    public float getFallSpeed(World world) {
        return 10;
    }

    @Override
    public float getFallDelay(World world) {
        return 0.2f;
    }
}
