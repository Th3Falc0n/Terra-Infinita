package com.dafttech.terra.engine;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.game.world.Vector2i;

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

    public Vector2i toWorldPosition() {
        int ox = (int)x >= 0 ? (int)x / BLOCK_SIZE : ((int)x + 1) / BLOCK_SIZE - 1;
        int oy = (int)y >= 0 ? (int)y / BLOCK_SIZE : ((int)y + 1) / BLOCK_SIZE - 1;
        return new Vector2i(ox, oy);
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

    public Vector2 addNew(float x, float y) {
        return new Vector2(this.x + x, this.y + y);
    }
}
