package com.dafttech.terra.game.world.tiles;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInWorld;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.renderer.TileRenderer;
import com.dafttech.terra.engine.renderer.TileRendererBlock;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.subtiles.Subtile;

public abstract class Tile extends Item implements IDrawableInWorld {
    public Vector2i position = null;
    List<Subtile> subtiles = new ArrayList<Subtile>();
    public World world = null;

    private float breakingProgress = 0;
    private float hardness = 1;

    public boolean receivesSunlight = false;
    public Tile sunlightFilter = null;

    @Override
    public boolean use(Player causer, Vector2 position) {
        if (causer.getPosition().clone().sub(position).len() < 100) {
            Vector2i pos = position.toWorldPosition();
            return causer.getWorld().placeTile(pos.x, pos.y, this, causer);
        }
        return false;
    }

    public boolean isReplacable() {
        return false;
    }

    public void spawnAsEntity() {
        Vector2 p = position.toEntityPos();
        World w = world;

        super.spawnAsEntity(p.addNew(0.5f, 0.5f), w);
    };

    public World getWorld() {
        return world;
    }

    public TileRenderer getRenderer() {
        return TileRendererBlock.$Instance;
    }

    public float getWalkFriction() {
        return 1f;
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

    public final void tick(World world) {
        onTick(world);
        for (int i = 0; i < subtiles.size(); i++) {
            subtiles.get(i).tick(world);
        }
    }

    public void onTick(World world) {

    }

    @Override
    public void update(float delta) {
        for (int i = 0; i < subtiles.size(); i++) {
            subtiles.get(i).update(delta);
        }
        if (breakingProgress > 0) breakingProgress -= delta;
    }

    public boolean canCollideWith(Entity entity) {
        // TODO Auto-generated method stub
        return true;
    }

    public Tile setHardness(float hardness) {
        this.hardness = hardness;
        return this;
    }

    public final void setReceivesSunlight(boolean is) {
        receivesSunlight = is;
        if (!isOpaque()) {
            Tile b = world.getNextTileBelow(position);
            if (b != null) {
                b.setReceivesSunlight(is);
                b.sunlightFilter = is ? this : null;
            }
        }
    }

    public boolean isOpaque() {
        return true;
    }

    public boolean providesSunlightFilter() {
        return false;
    }

    public Color getFilteredSunlightColor() {
        return getSunlightColor();
    }

    public final Color getSunlightColor() {
        if (sunlightFilter == null) {
            return Color.WHITE;
        } else {
            return sunlightFilter.getFilteredSunlightColor();
        }
    }

    public void damage(float damage, Entity causer) {
        breakingProgress += damage;
        if (breakingProgress > hardness) {
            getWorld().destroyTile(position.x, position.y, causer);
        }
    }

    public boolean hasSubtile(Class<? extends Subtile> subtileClass) {
        for (int i = 0; i < subtiles.size(); i++)
            if (subtiles.get(i).getClass() == subtileClass) return true;
        return false;
    }
}
