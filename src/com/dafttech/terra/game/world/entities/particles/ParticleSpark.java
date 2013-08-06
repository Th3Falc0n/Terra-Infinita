package com.dafttech.terra.game.world.entities.particles;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.graphics.lighting.PointLight;
import com.dafttech.terra.resources.Resources;

public class ParticleSpark extends Particle {

    PointLight light;

    public ParticleSpark(Vector2 pos, World world) {
        super(pos, world, 0.5f, new Vector2(0.5f, 0.5f));
        setHasGravity(false);
        setVelocity(new Vector2(2f * (0.5f - TerraInfinita.rnd.nextFloat()), 2f * (0.5f - TerraInfinita.rnd.nextFloat())));
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("flame");
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub
        super.update(delta);

        if (light == null) light = new PointLight(position, 30);

        light.setPosition((Vector2) new Vector2(position).add(getSize().x * BLOCK_SIZE / 2, getSize().y * BLOCK_SIZE / 2));
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
