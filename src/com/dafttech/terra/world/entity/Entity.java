package com.dafttech.terra.world.entity;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IRenderable;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class Entity implements IRenderable {
    Vector2 position = new Vector2(0, 0);
    Vector2 velocity = new Vector2(0, 0);
    Vector2 accelleration = new Vector2(0, 0);
    Vector2 size;
    World worldObj;

    public Entity(Vector2 pos, World world) {
        position = pos;
        worldObj = world;
        world.localEntities.add(this);
    }

    public Vector2 getPosition() {
        return position;
    }

    public void checkTerrainCollisions(World world) {
        Position mid = position.toWorldPosition();

        Rectangle playerRect = new Rectangle(position.x, position.y, BLOCK_SIZE, BLOCK_SIZE * 2);

        for (int x = mid.getX() - 2; x <= mid.getX() + 2; x++) {
            for (int y = mid.getY() - 2; y <= mid.getY() + 2; y++) {
                if (world.getTile(x, y) != null && world.getTile(x, y).onCollisionWith(this)) {
                    Rectangle rect = new Rectangle(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                    if (rect.overlaps(playerRect)) {
                        if (rect.x < playerRect.x + playerRect.width) {
                            onCollisionRight(rect.x);
                        }

                        if (rect.x + rect.width > playerRect.x) {
                            onCollisionLeft(rect.x);
                        }

                        if (rect.y < playerRect.y + playerRect.height) {
                            Gdx.app.log("Collision", "Bottom y=" + y);
                            onCollisionBottom(rect.y);
                        }

                        if (rect.y + rect.height > playerRect.y) {
                            onCollisionTop(rect.y);
                        }
                    }
                }
            }
        }
    }

    void onCollisionTop(float y) {

    }

    void onCollisionBottom(float y) {
        if (velocity.y < 0) {
            velocity.y = 0;
            position.y = y + BLOCK_SIZE;
        }
    }

    void onCollisionLeft(float x) {

    }

    void onCollisionRight(float x) {

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
        this.accelleration.add(accelleration);
        return this;
    }

    @Override
    public void update(Player player, float delta) {
        delta *= 10;

        accellerate(new Vector2(0, -9.81f));

        velocity.x += accelleration.x * delta;
        velocity.y += accelleration.y * delta;

        accelleration.setNull();

        checkTerrainCollisions(player.worldObj);

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;

        velocity.mul(0.99f);
    }

    public boolean isInRenderRange(Player player) {
        int sx = 2 + Gdx.graphics.getWidth() / BLOCK_SIZE / 2;
        int sy = 2 + Gdx.graphics.getHeight() / BLOCK_SIZE / 2;

        if (position.x >= player.getPosition().x / BLOCK_SIZE - sx && position.x <= player.getPosition().x / BLOCK_SIZE + sx
                && position.y >= player.getPosition().y / BLOCK_SIZE - sy && position.y <= player.getPosition().y / BLOCK_SIZE + sy) return true;
        return false;
    }
}
