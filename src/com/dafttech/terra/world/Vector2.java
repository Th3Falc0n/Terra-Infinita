package com.dafttech.terra.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;

public class Vector2 extends com.badlogic.gdx.math.Vector2 {
    private static final long serialVersionUID = -7851950266404157426L;

    public Vector2(float nx, float ny) {
        x = nx;
        y = ny;
    }

    public Vector2 setNull() {
        x = 0;
        y = 0;
        return this;
    }

    public Position toWorldPosition() {
        return new Position((int) x / BLOCK_SIZE, (int) y / BLOCK_SIZE);
    }

    public Vector2 toRenderPosition(Vector2 relateTo) {
        return new Vector2(x - relateTo.x + Gdx.graphics.getWidth() / 2, y - relateTo.y + Gdx.graphics.getHeight() / 2);
    }

    public static Vector2 getMouse() {
        return new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
    }
}
