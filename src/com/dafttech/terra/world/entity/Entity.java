package com.dafttech.terra.world.entity;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IRenderable;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class Entity implements IRenderable {
    Vector2 position = new Vector2(0, 0);
    Vector2 velocity = new Vector2(0, 0);
    Vector2 accelleration = new Vector2(10, 0);
    World worldObj;

    public Entity(Vector2 pos, World world) {
        position = pos;
        worldObj = world;
        world.localEntities.add(this);
    }

    public Vector2 getPosition() {
        return position;
    }

    @Override
    public void draw(AbstractScreen screen, Player player) {
        // TODO Auto-generated method stub

    }

    public Entity setPosition(Vector2 position) {
        this.position = position;
        return this;
    }

    public Entity setVelocity(Vector2 velocity) {
        this.velocity = velocity;
        return this;
    }

    public Entity accellerate(Vector2 accelleration) {
        this.accelleration = accelleration;
        return this;
    }

    @Override
    public void update(Player player, float delta) {
        velocity.x += accelleration.x * delta;
        velocity.y += accelleration.y * delta;
        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }

    public boolean isInRenderRange(Player player) {
        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        if (position.x >= player.getPosition().x / BLOCK_SIZE - sx && position.x <= player.getPosition().x / BLOCK_SIZE + sx
                && position.y >= player.getPosition().y / BLOCK_SIZE - sy && position.y <= player.getPosition().y / BLOCK_SIZE + sy) return true;
        return false;
    }
}
