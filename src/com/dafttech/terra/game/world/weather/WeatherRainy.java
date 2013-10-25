package com.dafttech.terra.game.world.weather;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.particles.Particle;
import com.dafttech.terra.game.world.entities.particles.ParticleSpark;

public class WeatherRainy extends Weather {
    public Particle[] particles = new Particle[250];
    
    @Override
    public void update(World world, float delta) {
        Vector2 top = world.localPlayer.getPosition().sub(0, 0 + (Gdx.graphics.getHeight() / 2));
        
        //TODO: Following mechanics are a particle emitter. CREATE A CLASS PARTICLEEMITTER!
        for(int i = 0; i < particles.length; i++) {
            if(particles[i] == null || particles[i].isDead()) {
                particles[i] = new ParticleSpark(top.add((TerraInfinita.rnd.nextFloat() - 0.5f) * Gdx.graphics.getWidth(), 0), world);
            }
        }
    }
}
