package com.dafttech.terra.game.world.subtiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.particles.ParticleSpark;
import com.dafttech.terra.resources.Options;
import com.dafttech.terra.resources.Resources$;

public class SubtileGlowGoo extends SubtileFluid {
    float img = 0;
    PointLight light;

    public SubtileGlowGoo() {
        super();
    }

    @Override
    public SubtileFluid getNewFluid() {
        return new SubtileGlowGoo();
    }

    @Override
    public float getViscosity() {
        return 100;
    }

    @Override
    public void onTick(World world, float delta) {
        super.onTick(world, delta);
        img += delta;
        if ((int) img > 3) img = 0;

        if (light == null) light = new PointLight(tile.getPosition().toEntityPos(), 95);

        light.setPosition(tile.getPosition().toEntityPos().add(Options.BLOCK_SIZE() / 2, Options.BLOCK_SIZE() / 2));

        for (int i = 0; i < (int) pressure; i++) {
            if (TerraInfinita.rnd().nextDouble() < delta * 0.5f) {
                new ParticleSpark(tile.getPosition().toEntityPos().addX(Options.BLOCK_SIZE() / 2), world).addVelocity(new Vector2(0, -1));
            }
        }
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.TILES().getImage("glowgoo", (int) img);
    }

    @Override
    public int getMaxReach() {
        return 0;
    }

    @Override
    public int getPressCap() {
        return 0;
    }
}
