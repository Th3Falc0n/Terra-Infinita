package com.dafttech.terra.game.world.entities.particles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;

public class ParticleDust extends Particle {
    TextureRegion assignedTexture;
    float size;

    public ParticleDust(Vector2 pos, World world, TextureRegion tex) {
        super(pos, world, 0.6f + (0.75f * TerraInfinita.rnd().nextFloat()), new Vector2(0.0f, 0.0f));

        assignedTexture = tex;
        size = TerraInfinita.rnd().nextFloat() * 0.2f + 0.25f;
        setVelocity(new Vector2((TerraInfinita.rnd().nextFloat() - 0.5f) * 5f, (TerraInfinita.rnd().nextFloat() - 1f) * 2f));
        setHasGravity(false);
        setMidPos(pos);
    }

    @Override
    public TextureRegion getImage() {
        return assignedTexture;
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);

        getSize().x_$eq(size * (1 - (lifetime / lifetimeMax)));
        getSize().y_$eq(getSize().x());
    }

    @Override
    public void checkTerrainCollisions(World world) {

    }

}
