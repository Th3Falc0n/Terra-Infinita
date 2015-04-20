package com.dafttech.terra.game.world.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.particles.ParticleExplosion;
import com.dafttech.terra.resources.Resources;

public class EntityDynamite extends Entity {
    float explodeTimer;
    int radius;

    public EntityDynamite(Vector2 pos, World world, float explodeTimer, int radius) {
        super(pos, world, new Vector2(1.5f, 1.5f));
        setGravityFactor(0.125f);
        this.explodeTimer = explodeTimer;
        this.radius = radius;
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("dynamite");
    }

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);
        explodeTimer -= delta;
        if (explodeTimer <= 0) {
            worldObj.removeEntity(this);
            new ParticleExplosion(getPosition().add(BLOCK_SIZE * 0.75f, BLOCK_SIZE * 0.75f), worldObj, radius);
            Vector2i destroyPos = new Vector2(getPosition()).toWorldPosition();
            for (int y = -radius + 1; y <= radius; y++) {
                for (int x = -radius + 1; x <= radius; x++) {
                    worldObj.destroyTile(destroyPos.x + x, destroyPos.y + y, this);
                }
            }

        }
    }
}
