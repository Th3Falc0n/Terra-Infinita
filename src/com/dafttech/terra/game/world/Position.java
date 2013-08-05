package com.dafttech.terra.game.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public class Position {
    public int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position pos) {
        this.x = pos.x;
        this.y = pos.y;
    }

    public Position() {
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

    public Position setX(int x) {
        this.x = x;
        return this;
    }

    public Position setY(int y) {
        this.y = y;
        return this;
    }

    public Position set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Position set(Position pos) {
        this.x = pos.x;
        this.y = pos.y;
        return this;
    }

    public Position addX(int x) {
        this.x += x;
        return this;
    }

    public Position addY(int y) {
        this.y += y;
        return this;
    }

    public Position add(int x, int y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Position add(Position pos) {
        this.x += pos.x;
        this.y += pos.y;
        return this;
    }

    public Position set(Vector2 pos) {
        return set(pos.toWorldPosition());
    }

    public Position setXNew(int x) {
        Position newPos = new Position(this);
        newPos.x = x;
        return newPos;
    }

    public Position setYNew(int y) {
        Position newPos = new Position(this);
        newPos.y = y;
        return newPos;
    }

    public Position setNew(int x, int y) {
        Position newPos = new Position(this);
        newPos.x = x;
        newPos.y = y;
        return newPos;
    }

    public Position setNew(Position pos) {
        Position newPos = new Position(this);
        newPos.x = pos.x;
        newPos.y = pos.y;
        return newPos;
    }

    public Position addXNew(int x) {
        Position newPos = new Position(this);
        newPos.x += x;
        return newPos;
    }

    public Position addYNew(int y) {
        Position newPos = new Position(this);
        newPos.y += y;
        return newPos;
    }

    public Position addNew(int x, int y) {
        Position newPos = new Position(this);
        newPos.x += x;
        newPos.y += y;
        return newPos;
    }

    public Position addNew(Position pos) {
        Position newPos = new Position(this);
        newPos.x += pos.x;
        newPos.y += pos.y;
        return newPos;
    }

    public Position setNew(Vector2 pos) {
        Position newPos = new Position(this);
        return newPos.set(pos.toWorldPosition());
    }

    public Position setTile(World world, Tile tile) {
        if (x < 0 || y < 0 || x > world.size.x || y > world.size.y) return null;
        tile.addToWorld(world, this);
        return this;
    }

    public Position destroyTile(Tile tile) {
        tile.world.destroyTile(x, y);
        Events.EVENT_BLOCKCHANGE.callSync(tile);
        return this;
    }

    public Tile getTile(World world) {
        if (x < 0 || y < 0 || x > world.size.x || y > world.size.y) return null;
        return world.map[x][y];
    }
}
