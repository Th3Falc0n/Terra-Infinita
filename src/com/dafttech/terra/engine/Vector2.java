package com.dafttech.terra.engine;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.game.world.Vector2i;

public class Vector2 {
    public float x, y;

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
        int ox = (int) x >= 0 ? (int) x / BLOCK_SIZE : ((int) x + 1) / BLOCK_SIZE - 1;
        int oy = (int) y >= 0 ? (int) y / BLOCK_SIZE : ((int) y + 1) / BLOCK_SIZE - 1;
        return new Vector2i(ox, oy);
    }

    public Vector2 toRenderPosition(Vector2 relateTo) {
        return new Vector2(x - relateTo.x + Gdx.graphics.getWidth() / 2.0f, y - relateTo.y + Gdx.graphics.getHeight() / 2.0f);
    }

    public static Vector2 getMouse() {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
    }

    public Vector2 addNew(Vector2 v) {
        return addNew(v.x, v.y);
    }

    public Vector2 addNew(float x, float y) {
        return new Vector2(this.x + x, this.y + y);
    }

    public Vector2 set(Vector2 p) {
        x = p.x;
        y = p.y;

        return this;
    }

    public Vector2 add(Vector2 v) {
        return add(v.x, v.y);
    }

    public Vector2 add(float x2, float y2) {
        x += x2;
        y += y2;
        return this;
    }

    public Vector2 sub(Vector2 v) {
        return sub(v.x, v.y);
    }

    public Vector2 sub(float x2, float y2) {
        x -= x2;
        y -= y2;
        return this;
    }

    public float len() {
        return (float) Math.sqrt(len2());
    }

    public float len2() {
        return x * x + y * y;
    }

    public Vector2 nor() {
        return mul(1f / len());
    }

    public Vector2 mul(float f) {
        x *= f;
        y *= f;
        return this;
    }

    public float angle() {
        float angle = (float) Math.atan2(this.y, this.x) * 57.295776F;
        if (angle < 0.0F) angle += 360.0F;
        return angle;
    }

    @Override
    public Vector2 clone() {
        return new Vector2(this);
    }
}
