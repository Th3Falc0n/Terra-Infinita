package com.dafttech.terra.game.world.tiles;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawable;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.engine.renderer.TileRenderer;
import com.dafttech.terra.engine.renderer.TileRendererBlock;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.items.Item;
import com.dafttech.terra.game.world.entities.items.ItemTile;
import com.dafttech.terra.game.world.subtiles.Subtile;

public abstract class Tile implements IDrawable {
    public Vector2i position = null;
    List<Subtile> subtiles = new ArrayList<Subtile>();
    public World world = null;

    public boolean receivesSunlight = false;
    public Tile sunlightFilter = null;

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

    public Tile addToWorld(World world, Vector2i pos) {
        if (position == null || world == null) {
            this.position = new Vector2i(pos);
            this.world = world;
            // world.map[pos.getX()][pos.getY()] = this;
            world.setTile(pos.x, pos.y, this);
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
    
    public final void setReceivesSunlight(boolean is) {
        receivesSunlight = is;
        if(!isOpaque()) {
            //TODO: pass through sunlight or darkness
        }
    }
    
    public boolean isOpaque() {
        return true;
    }
    
    public boolean providesSunlightFilter(){
        return false;
    }
    
    public Color getFilteredSunlightColor() {
        return getSunlightColor();
    }
    
    public final Color getSunlightColor() {
        if(sunlightFilter == null) {
            return Color.RED;
        }
        else
        {
            return sunlightFilter.getFilteredSunlightColor();
        }
    }

    public void spawnAsEntity() {
        world.addEntity(getItemDropped());
    }

    // TODO: UNUSED
    public void onNeighborChange(Vector2i pos) {

    }
}
