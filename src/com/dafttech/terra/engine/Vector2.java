package com.dafttech.terra.engine;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.game.world.Chunk;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;

public class Vector2 {
    public float x, y;

    public Vector2() {
        x = 0;
        y = 0;
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vec) {
        x = vec.x;
        y = vec.y;
    }

    public Vector2(Vector2i vec) {
        x = vec.x;
        y = vec.y;
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

    public Vector2i toVector2i() {
        return new Vector2i((int) x >= 0 ? (int) x : (int) x - 1, (int) y >= 0 ? (int) y : (int) y - 1);
    }

    public static Vector2 getMouse() {
        return new Vector2(Gdx.input.getX(), Gdx.input.getY());
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

    public float angle() {
        float angle = (float) Math.atan2(this.y, this.x) * 57.295776F;
        if (angle < 0.0F) angle += 360.0F;
        return angle;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vector2 setX(float x) {
        this.x = x;
        return this;
    }

    public Vector2 setY(float y) {
        this.y = y;
        return this;
    }

    public Vector2 set() {
        this.x = 0;
        this.y = 0;
        return this;
    }

    public Vector2 set(float val) {
        this.x = val;
        this.y = val;
        return this;
    }

    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2 set(Vector2 pos) {
        this.x = pos.x;
        this.y = pos.y;
        return this;
    }

    public Vector2 addX(float x) {
        this.x += x;
        return this;
    }

    public Vector2 addY(float y) {
        this.y += y;
        return this;
    }

    public Vector2 add() {
        this.x += 0;
        this.y += 0;
        return this;
    }

    public Vector2 add(float val) {
        this.x += val;
        this.y += val;
        return this;
    }

    public Vector2 add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2 add(Vector2 pos) {
        this.x += pos.x;
        this.y += pos.y;
        return this;
    }

    public Vector2 mulX(float x) {
        this.x *= x;
        return this;
    }

    public Vector2 mulY(float y) {
        this.y *= y;
        return this;
    }

    public Vector2 mul() {
        this.x *= 1;
        this.y *= 1;
        return this;
    }

    public Vector2 mul(float val) {
        this.x *= val;
        this.y *= val;
        return this;
    }

    public Vector2 mul(float x, float y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2 mul(Vector2 pos) {
        this.x *= pos.x;
        this.y *= pos.y;
        return this;
    }

    public Vector2 setXNew(float x) {
        return clone().setX(x);
    }

    public Vector2 setYNew(float y) {
        return clone().setY(y);
    }

    public Vector2 setNew() {
        return clone().set();
    }

    public Vector2 setNew(float val) {
        return clone().setNew(val);
    }

    public Vector2 setNew(float x, float y) {
        return clone().set(x, y);
    }

    public Vector2 setNew(Vector2 pos) {
        return clone().set(pos);
    }

    public Vector2 addXNew(float x) {
        return clone().addX(x);
    }

    public Vector2 addYNew(float y) {
        return clone().addY(y);
    }

    public Vector2 addNew() {
        return clone().add();
    }

    public Vector2 addNew(float val) {
        return clone().add(val);
    }

    public Vector2 addNew(float x, float y) {
        return clone().add(x, y);
    }

    public Vector2 addNew(Vector2 pos) {
        return clone().add(pos);
    }

    public Vector2 mulXNew(float x) {
        return clone().mulX(x);
    }

    public Vector2 mulYNew(float y) {
        return clone().mulY(y);
    }

    public Vector2 mulNew() {
        return clone().mul();
    }

    public Vector2 mulNew(float val) {
        return clone().mul(val);
    }

    public Vector2 mulNew(float x, float y) {
        return clone().mul(x, y);
    }

    public Vector2 mulNew(Vector2 pos) {
        return clone().mul(pos);
    }

    @Override
    public Vector2 clone() {
        return new Vector2(this);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Vector2i && ((Vector2i) obj).x == x && ((Vector2i) obj).y == y);
    }

    @Override
    public int hashCode() {
        return ((Float) x).hashCode() + ((Float) y).hashCode();
    }

    private static int getChunkPos(float blockInWorldPos, int chunkSize) {
        return (int) blockInWorldPos >= 0 ? (int) blockInWorldPos / chunkSize : ((int) blockInWorldPos + 1) / chunkSize - 1;
    }

    private static float getBlockInChunkPos(float blockInWorldPos, int chunkSize) {
        return blockInWorldPos - getChunkPos(blockInWorldPos, chunkSize) * chunkSize;
    }

    private static float getBlockInWorldPos(int chunkPos, int chunkSize, float blockInChunkPos) {
        return chunkSize * chunkPos + blockInChunkPos;
    }

    public Vector2 getChunkPos(World world) {
        return new Vector2(getChunkPos(x, world.chunksize.x), getChunkPos(y, world.chunksize.y));
    }

    public Vector2 getBlockInChunkPos(World world) {
        return new Vector2(getBlockInChunkPos(x, world.chunksize.x), getBlockInChunkPos(y, world.chunksize.y));
    }

    public Vector2 getBlockInWorldPos(Chunk chunk) {
        return new Vector2(getBlockInWorldPos(chunk.pos.x, chunk.world.chunksize.x, x), getBlockInWorldPos(chunk.pos.y, chunk.world.chunksize.y, y));
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
