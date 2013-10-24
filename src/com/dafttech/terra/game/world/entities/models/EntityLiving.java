package com.dafttech.terra.game.world.entities.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public abstract class EntityLiving extends Entity {
    private float maxHealth = 10;
    private float health = maxHealth;

    public EntityLiving(Vector2 pos, World world, Vector2 s) {
        super(pos, world, s);
    }

    @Override
    public TextureRegion getImage() {
        return null;
    }

    public EntityLiving damage(float damage) {
        return heal(-damage);
    }

    public EntityLiving heal(float health) {
        this.health += health;
        if (this.health < 0) this.health = 0;
        if (this.health > maxHealth) this.health = maxHealth;
        return this;
    }

    protected EntityLiving setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
        return this;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }
}
