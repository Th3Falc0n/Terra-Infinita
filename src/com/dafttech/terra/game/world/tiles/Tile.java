package com.dafttech.terra.game.world.tiles;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.Position;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.items.Item;
import com.dafttech.terra.game.world.entities.items.ItemTile;
import com.dafttech.terra.game.world.subtiles.Subtile;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawable;
import com.dafttech.terra.graphics.lighting.PointLight;
import com.dafttech.terra.graphics.renderer.TileRenderer;
import com.dafttech.terra.graphics.renderer.TileRendererBlock;

public abstract class Tile implements IDrawable {
    public Position position = null;
    List<Subtile> subtiles = new ArrayList<Subtile>();
    public World world = null;

    public World getWorld() {
        return world;
    }

    public Item getItemDropped() {
        return new ItemTile(this);
    }

    public TileRenderer getRenderer() {
        return TileRendererBlock.$Instance;
    }

    public abstract TextureRegion getImage();

    public float getWalkFriction() {
        return 1f;
    }

    public void addedToWorld() {

    }

    public float getWalkAcceleration() {
        return 1f;
    }

    public Tile addSubtile(Subtile... subtile) {
        for (Subtile s : subtile) {
            if (s != null) {
                s.setTile(this);
                subtiles.add(s);
            }
        }
        return this;
    }

    @Override
    public void draw(AbstractScreen screen, Entity pointOfView) {
        getRenderer().draw(screen, this, pointOfView);

        for (Subtile s : subtiles) {
            s.draw(screen, pointOfView);
        }
    }

    @Override
    public void update(float delta) {
        for (Subtile subtile : subtiles) {
            subtile.update(delta);
        }
    }

    public boolean onCollisionWith(Entity entity) {
        // TODO Auto-generated method stub
        return true;
    }

    public Tile addToWorld(World world, Position pos) {
        if (position == null || world == null) {
            this.position = new Position(pos);
            this.world = world;
            world.map[pos.x][pos.y] = this;
            addedToWorld();
        }
        return this;
    }

    public boolean isLightEmitter() {
        return false;
    }

    public PointLight getEmittedLight() {
        return null;
    }

    public void spawnAsEntity() {
        world.addEntity(getItemDropped());
    }
}
