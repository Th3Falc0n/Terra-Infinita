package com.dafttech.terra.world.entity;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.math.Rectangle;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IRenderable;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class Entity implements IRenderable {
    Vector2 position;
    Vector2 size;

    public Entity(Vector2 pos) {
        position = pos;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void checkTerrainCollisions(World world) {
        Position mid = position.toWorldPosition();

        Rectangle playerRect = new Rectangle(position.x, position.y, BLOCK_SIZE, BLOCK_SIZE * 2);

        for (int x = mid.getX() - 2; x <= mid.getX() + 2; x++) {
            for (int y = mid.getY() - 2; x <= mid.getY() + 2; y++) {
                Rectangle rect = new Rectangle(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                if (rect.overlaps(playerRect)) {
                    if (rect.x < playerRect.x + playerRect.width) {
                        onCollisionRight(rect.x);
                    }

                    if (rect.x + rect.width > playerRect.x) {
                        onCollisionLeft(rect.x);
                    }

                    if (rect.y < playerRect.y + playerRect.height) {
                        onCollisionBottom(rect.y);
                    }

                    if (rect.y + rect.height > playerRect.y) {
                        onCollisionTop(rect.y);
                    }
                }
            }
        }
    }

    void onCollisionTop(float y) {

    }

    void onCollisionBottom(float y) {

    }

    void onCollisionLeft(float x) {

    }

    void onCollisionRight(float x) {

    }

    @Override
    public void draw(AbstractScreen screen, Player player) {
        // TODO Auto-generated method stub

    }
}
