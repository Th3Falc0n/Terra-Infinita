package com.dafttech.terra.world;

import com.badlogic.gdx.Gdx;

public class Position {
    int x, y;

    public Position(int vx, int vy) {
        x = vx;
        y = vy;
    }

    public Vector2 toScreenPos(Player player) {
        return new Vector2(x * 8 - player.position.x + Gdx.graphics.getWidth() / 2, y * 8 - player.position.y + Gdx.graphics.getHeight() / 2);
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
