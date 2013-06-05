package com.dafttech.terra.world;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.world.entities.Player;

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

    public Position setTile(World world, Tile tile) {
        if (tile.position == null) {
            tile.position = new Position(this);
            world.map[x][y] = tile;
            tile.addedToWorld();
        }
        return this;
    }

    public Tile getTile(World world) {
        return world.map[x][y];
    }
}
