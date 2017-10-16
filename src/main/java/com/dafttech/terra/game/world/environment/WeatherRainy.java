package com.dafttech.terra.game.world.environment;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.particles.ParticleRain;

public class WeatherRainy extends Weather {

    @Override
    public void update(World world, float delta) {
        Vector2 top = world.localPlayer().getPosition().sub(0, 0 + (Gdx.graphics.getHeight() / 2));

        if ((delta * 60f) * TerraInfinita.rnd().nextFloat() > 0.5f) {
            new ParticleRain(top.add((TerraInfinita.rnd().nextFloat() - 0.5f) * Gdx.graphics.getWidth(), 0), world).setVelocity(new Vector2(
                    (TerraInfinita.rnd().nextFloat() - 0.1f /* Rain direction */) * 25f, (TerraInfinita.rnd().nextFloat() + 5f) * 15f));
        }
    }

    @Override
    public float getWindSpeed(World world) {
        return 0;
    }
}
