package com.dafttech.terra.game.world.entities.particles;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class ParticleSpark extends Particle {

    PointLight light;

    public ParticleSpark(Vector2 pos, World world) {
        super(pos, world, 1.5f, new Vector2(0.5f, 0.5f));
        
        getSize().x = TerraInfinita.rnd.nextFloat() * 0.2f + 0.1f;
        getSize().y = getSize().x;
        
        setHasGravity(true);        
        setGravityFactor(0.125f);
        setVelocity(new Vector2(4f * (0.5f - TerraInfinita.rnd.nextFloat()), 4f * (0.5f - TerraInfinita.rnd.nextFloat())));
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("flame");
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if(light != null)
            light.setPosition((Vector2) new Vector2(position).add(getSize().x * BLOCK_SIZE / 2, getSize().y * BLOCK_SIZE / 2));
    }

    @Override
    public boolean isLightEmitter() {
        return getSize().x % 0.01f > 0.0085f;        
    }

    @Override
    public void checkTerrainCollisions(World world) {

    }

    @Override
    public PointLight getEmittedLight() {
        if (light == null) {
            light = new PointLight(position, 55);
            light.setColor(new Color(1, 0.9f, 0.9f, 0.4f));
        }        
        
        return light;
    }

}
