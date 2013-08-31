package com.dafttech.terra.game.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public class Vector2i {
    public int x, y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(Vector2i pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public Vector2i() {
        this.x = 0;
        this.y = 0;
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

    public Vector2i addX(int x) {
        this.x += x;
        return this;
    }

    public Vector2i addY(int y) {
        this.y += y;
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

    public Vector2i set(Vector2 pos) {
        return set(pos.toWorldPosition());
    }

    public Vector2i setXNew(int x) {
        Vector2i newPos = new Vector2i(this);
        newPos.x = x;
        return newPos;
    }

    public Vector2i setYNew(int y) {
        Vector2i newPos = new Vector2i(this);
        newPos.y = y;
        return newPos;
    }

    public Vector2i setNew(int x, int y) {
        Vector2i newPos = new Vector2i(this);
        newPos.x = x;
        newPos.y = y;
        return newPos;
    }

    public Vector2i setNew(Vector2i pos) {
        Vector2i newPos = new Vector2i(this);
        newPos.x = pos.x;
        newPos.y = pos.y;
        return newPos;
    }

    public Vector2i addXNew(int x) {
        Vector2i newPos = new Vector2i(this);
        newPos.x += x;
        return newPos;
    }

    public Vector2i addYNew(int y) {
        Vector2i newPos = new Vector2i(this);
        newPos.y += y;
        return newPos;
    }

    public Vector2i addNew(int x, int y) {
        Vector2i newPos = new Vector2i(this);
        newPos.x += x;
        newPos.y += y;
        return newPos;
    }

    public Vector2i addNew(Vector2i pos) {
        Vector2i newPos = new Vector2i(this);
        newPos.x += pos.x;
        newPos.y += pos.y;
        return newPos;
    }

    public Vector2i setNew(Vector2 pos) {
        Vector2i newPos = new Vector2i(this);
        return newPos.set(pos.toWorldPosition());
    }

    public Vector2i setTile(World world, Tile tile) {
        if (x < 0 || y < 0 || x > world.size.x || y > world.size.y) return null;
        tile.addToWorld(world, this);
        return this;
    }

    public Vector2i destroyTile(Tile tile) {
        tile.world.destroyTile(x, y);
        Events.EVENT_BLOCKCHANGE.callSync(tile);
        return this;
    }

    public Tile getTile(World world) {
        if (x < 0 || y < 0 || x > world.size.x || y > world.size.y) return null;
        return world.map[x][y];
    }
}
