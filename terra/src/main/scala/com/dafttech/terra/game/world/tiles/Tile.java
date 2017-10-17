package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInWorld;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.Vector2i;
import com.dafttech.terra.engine.renderer.TileRenderer;
import com.dafttech.terra.engine.renderer.TileRendererBlock$;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.EntityItem;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.subtiles.Subtile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class Tile extends Item implements IDrawableInWorld {
    private World world = null;
    private Vector2i position = Vector2i.Null();
    private volatile List<Subtile> subtiles = new ArrayList<Subtile>();

    private float breakingProgress = 0;
    private float hardness = 1;

    public boolean receivesSunlight = false;
    public Tile sunlightFilter = null;

    @Override
    public boolean use(EntityLiving causer, Vector2 position) {
        if (causer.getPosition().$minus(position).length() < 100) {
            Vector2i pos = position.toWorldPosition();
            return causer.getWorld().placeTile(pos.x(), pos.y(), this, causer);
        }
        return false;
    }

    public boolean isAir() {
        return false;
    }

    public boolean isReplacable() {
        return isAir();
    }

    public boolean isOpaque() {
        return !isAir();
    }

    public boolean isCollidableWith(Entity entity) {
        return !isAir();
    }

    public boolean isFlammable() {
        return false;
    }

    public boolean isWaterproof() {
        return isOpaque();
    }

    public boolean isBreakable() {
        return !isAir();
    }

    public int getTemperature() {
        return 0;
    }

    public Item getDroppedItem() {
        return this;
    }

    public EntityItem spawnAsEntity(World world) {
        Item dropped = getDroppedItem();
        return dropped == null ? null : new EntityItem(getPosition().toEntityPos().$plus(new Vector2(0.5f, 0.5f)), world, new Vector2(0.5f, 0.5f), dropped);
    }

    ;

    public TileRenderer getRenderer() {
        return TileRendererBlock$.MODULE$.$Instance();
    }

    public float getWalkFriction() {
        return 1f;
    }

    public float getWalkAcceleration() {
        return 1f;
    }

    public World getWorld() {
        return world;
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

    public Tile removeSubtile(Subtile... subtile) {
        for (Subtile s : subtile) {
            if (s != null) {
                subtiles.remove(s);
            }
        }
        return this;
    }

    public Tile removeAndUnlinkSubtile(Subtile... subtile) {
        for (Subtile s : subtile) {
            if (s != null) {
                s.setTile(null);
                subtiles.remove(s);
            }
        }
        return this;
    }

    public List<Subtile> getSubtiles() {
        return new ArrayList<Subtile>(subtiles);
    }

    public boolean hasSubtile(Class<? extends Subtile> subtileClass, boolean inherited) {
        return getSubtile(subtileClass, inherited) != null;
    }

    public Subtile getSubtile(Class<? extends Subtile> subtileClass, boolean inherited) {
        Subtile subtile = null;
        for (Iterator<Subtile> i = subtiles.iterator(); i.hasNext(); ) {
            subtile = i.next();
            if ((inherited && subtileClass.isAssignableFrom(subtile.getClass())) || (!inherited && subtile.getClass() == subtileClass)) {
                return subtile;
            }
        }
        return null;
    }

    public Tile setWorld(World world) {
        this.world = world;
        return this;
    }

    public Tile setPosition(Vector2i position) {
        this.position = position;
        return this;
    }

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Entity pointOfView) {
        getRenderer().draw2(pos, world, screen, this, pointOfView);

        for (Subtile subtile : subtiles) {
            subtile.draw(pos, world, screen, pointOfView);
        }
    }

    public final void tick(World world, float delta) {
        onTick(world, delta);
        for (int i = 0; i < subtiles.size(); i++) {
            subtiles.get(i).tick(world, delta);
        }
    }

    public void onTick(World world, float delta) {

    }

    public void onNeighborChange(World world, Tile changed) {

    }

    public void onTileDestroyed(World world, Entity causer) {

    }

    public void onTilePlaced(World world, Entity causer) {

    }

    public void onTileSet(World world) {

    }

    @Override
    public void update(World world, float delta) {
        for (int i = 0; i < subtiles.size(); i++) {
            subtiles.get(i).update(delta);
        }
        if (breakingProgress > 0) breakingProgress -= delta;
    }

    @Override
    public void update(float delta) {
    }

    public Tile setHardness(float hardness) {
        this.hardness = hardness;
        return this;
    }

    public final void setReceivesSunlight(World world, boolean is) {
        receivesSunlight = is;
        if (!is) this.sunlightFilter = null;
        if (!isOpaque()) {
            Tile b = world.getNextTileBelow(getPosition());
            if (b != null && b != this) {
                b.setReceivesSunlight(world, is);
                b.sunlightFilter = is ? this : null;
            }
        }
    }

    public Color getFilterColor() {
        return Color.WHITE;
    }

    public final Color getSunlightColor() {
        if (sunlightFilter == null) {
            return Color.WHITE;
        } else {
            Color sunlightColor = sunlightFilter.getSunlightColor().cpy().mul(sunlightFilter.getFilterColor());
            for (Subtile subtile : sunlightFilter.subtiles) {
                if (subtile.providesSunlightFilter()) sunlightColor = sunlightColor.cpy().mul(subtile.getFilterColor());
            }
            return sunlightColor;
        }
    }

    public void damage(World world, float damage, Entity causer) {
        breakingProgress += damage;
        if (breakingProgress > hardness) {
            world.destroyTile(getPosition().x(), getPosition().y(), causer);
        }
    }

    public Vector2i getPosition() {
        return position;
    }

}
