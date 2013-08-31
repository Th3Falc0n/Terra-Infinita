package com.dafttech.terra.game.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public class Vector2i {
    public int x, y; // TODO make private

    public Vector2i(int x, int y) {
        set(x, y);
    }

    public Vector2i(Vector2i pos) {
        set(pos);
    }

    public Vector2i(Vector2 pos) {
        set(pos);
    }

    public Vector2i() {
        set();
    }

    public Vector2 toScreenPos(Entity pointOfView) {
        return new Vector2(x * BLOCK_SIZE - pointOfView.getPosition().x + Gdx.graphics.getWidth() / 2, y * BLOCK_SIZE - pointOfView.getPosition().y
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

    public Vector2i setX(int x) {
        this.x = x;
        return this;
    }

    public Vector2i setY(int y) {
        this.y = y;
        return this;
    }

    public Vector2i set() {
        this.x = 0;
        this.y = 0;
        return this;
    }

    public Vector2i set(int val) {
        this.x = val;
        this.y = val;
        return this;
    }

    public Vector2i set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2i set(Vector2i pos) {
        this.x = pos.x;
        this.y = pos.y;
        return this;
    }

    public Vector2i set(Vector2 pos) {
        return set(pos.toWorldPosition());
    }

    public Vector2i addX(int x) {
        this.x += x;
        return this;
    }

    public Vector2i addY(int y) {
        this.y += y;
        return this;
    }

    public Vector2i add() {
        this.x += 0;
        this.y += 0;
        return this;
    }

    public Vector2i add(int val) {
        this.x += val;
        this.y += val;
        return this;
    }

    public Vector2i add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2i add(Vector2i pos) {
        this.x += pos.x;
        this.y += pos.y;
        return this;
    }

    public Vector2i add(Vector2 pos) {
        return add(pos.toWorldPosition());
    }

    public Vector2i mulX(int x) {
        this.x *= x;
        return this;
    }

    public Vector2i mulY(int y) {
        this.y *= y;
        return this;
    }

    public Vector2i mul() {
        this.x *= 1;
        this.y *= 1;
        return this;
    }

    public Vector2i mul(int val) {
        this.x *= val;
        this.y *= val;
        return this;
    }

    public Vector2i mul(int x, int y) {
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vector2i mul(Vector2i pos) {
        this.x *= pos.x;
        this.y *= pos.y;
        return this;
    }

    public Vector2i mul(Vector2 pos) {
        return mul(pos.toWorldPosition());
    }

    public Vector2i setXNew(int x) {
        return clone().setX(x);
    }

    public Vector2i setYNew(int y) {
        return clone().setY(y);
    }

    public Vector2i setNew() {
        return clone().set();
    }

    public Vector2i setNew(int val) {
        return clone().setNew(val);
    }

    public Vector2i setNew(int x, int y) {
        return clone().set(x, y);
    }

    public Vector2i setNew(Vector2i pos) {
        return clone().set(pos);
    }

    public Vector2i setNew(Vector2 pos) {
        return clone().set(pos);
    }

    public Vector2i addXNew(int x) {
        return clone().addX(x);
    }

    public Vector2i addYNew(int y) {
        return clone().addY(y);
    }

    public Vector2i addNew() {
        return clone().add();
    }

    public Vector2i addNew(int val) {
        return clone().add(val);
    }

    public Vector2i addNew(int x, int y) {
        return clone().add(x, y);
    }

    public Vector2i addNew(Vector2i pos) {
        return clone().add(pos);
    }

    public Vector2i addNew(Vector2 pos) {
        return clone().add(pos);
    }

    public Vector2i mulXNew(int x) {
        return clone().mulX(x);
    }

    public Vector2i mulYNew(int y) {
        return clone().mulY(y);
    }

    public Vector2i mulNew() {
        return clone().mul();
    }

    public Vector2i mulNew(int val) {
        return clone().mul(val);
    }

    public Vector2i mulNew(int x, int y) {
        return clone().mul(x, y);
    }

    public Vector2i mulNew(Vector2i pos) {
        return clone().mul(pos);
    }

    public Vector2i mulNew(Vector2 pos) {
        return clone().mul(pos);
    }

    public Vector2i clone() {
        return new Vector2i(this);
    }

    public Vector2i setTile(World world, Tile tile) { // TODO: to world (unused)
        if (x < 0 || y < 0 || x > world.size.x || y > world.size.y) return null;
        tile.addToWorld(world, this);
        return this;
    }

    public Vector2i destroyTile(Tile tile) {
        tile.world.destroyTile(x, y);
        Events.EVENT_BLOCKCHANGE.callSync(tile);
        return this;
    }

    public Tile getTile(World world) { // TODO: to world (unused)
        if (x < 0 || y < 0 || x > world.size.x || y > world.size.y) return null;
        return null;//world.map[x][y];
    }

    private static int getChunkCoord(int pos, int chunkSize) {
        return pos >= 0 ? pos / chunkSize : (pos * -1 / chunkSize) * -1;
    }

    private static int getChunkPos(int pos, int chunkSize) {
        return pos % chunkSize;
    }

    private static int getWorldPos(int chunkCoord, int chunkSize, int chunkPos) {
        return chunkCoord >= 0 ? chunkCoord * chunkSize + chunkPos : chunkCoord * chunkSize - chunkPos;
    }

    public Vector2i getChunkCoords(World world) {
        return new Vector2i(getChunkCoord(x, world.chunksize.x), getChunkCoord(y, world.chunksize.y));
    }

    public Chunk getChunk(World world) {
        Vector2i chunkCoods = getChunkCoords(world);
        for (Chunk chunk : world.localChunks) {
            if (chunk.pos.x == chunkCoods.x && chunk.pos.y == chunkCoods.y) return chunk;
        }
        return null;
    }

    public Vector2i getChunkPos(World world) {
        return new Vector2i(getChunkPos(x, world.chunksize.x), getChunkPos(y, world.chunksize.y));
    }

    public Vector2i getWorldPos(Chunk chunk) {
        return new Vector2i(getWorldPos(chunk.pos.x, chunk.world.chunksize.x, x), getWorldPos(chunk.pos.y, chunk.world.chunksize.y, y));
    }
}
