package com.dafttech.terra.engine;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.game.world.Position;

public class Vector2 extends com.badlogic.gdx.math.Vector2 {
    private static final long serialVersionUID = -7851950266404157426L;

    public Vector2(float nx, float ny) {
        x = nx;
        y = ny;
    }

    public Vector2(Vector2 v) {
        x = v.x;
        y = v.y;
    }

    public Vector2 setNull() {
        x = 0;
        y = 0;
        return this;
    }

    public Position toWorldPosition() {
        return new Position((x < (double) (int) x ? (int) x - 1 : (int) x) / BLOCK_SIZE, (y < (double) (int) y ? (int) y - 1 : (int) y) / BLOCK_SIZE);
    }

    public Vector2 toRenderPosition(Vector2 relateTo) {
        return new Vector2(x - relateTo.x + Gdx.graphics.getWidth() / 2.0f, y - relateTo.y + Gdx.graphics.getHeight() / 2.0f);
    }

    public static Vector2 getMouse() {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }

    public Vector2 addNew(Vector2 v) {
        return (Vector2) new Vector2(this).add(v);
    }
}
