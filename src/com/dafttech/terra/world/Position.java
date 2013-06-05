package com.dafttech.terra.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.world.entities.Player;

public class Position {
    public int x, y;

    public Position(int vx, int vy) {
        x = vx;
        y = vy;
    }

    public Vector2 toScreenPos(Player player) {
        return new Vector2(x * BLOCK_SIZE - player.getPosition().x + Gdx.graphics.getWidth() / 2, y * BLOCK_SIZE - player.getPosition().y
                + Gdx.graphics.getHeight() / 2);
    }
    
    public Vector2 toEntityPos() {
        return new Vector2(x * BLOCK_SIZE, y * BLOCK_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int v) {
        x = v;
    }

    public void setY(int v) {
        y = v;
    }
}
