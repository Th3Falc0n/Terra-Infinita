package com.dafttech.terra.game.world.tiles;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInWorld;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.renderer.TileRenderer;
import com.dafttech.terra.engine.renderer.TileRendererBlock;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.EntityItem;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.subtiles.Subtile;

public abstract class Tile extends Item implements IDrawableInWorld {
    private World world = null;
    private Vector2i position = new Vector2i();
    private List<Subtile> subtiles = new CopyOnWriteArrayList<Subtile>();

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
        return dropped == null ? null : new EntityItem(getPosition().toEntityPos().add(0.5f, 0.5f), world, new Vector2(0.5f, 0.5f), dropped);
    };

    public TileRenderer getRenderer() {
        return TileRendererBlock.$Instance;
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

    public boolean hasSubtile(Class<? extends Subtile> subtileClass, boolean inherited) {
        return getSubtile(subtileClass, inherited) != null;
    }

    public Subtile getSubtile(Class<? extends Subtile> subtileClass, boolean inherited) {
        Subtile subtile = null;
        for (Iterator<Subtile> i = subtiles.iterator(); i.hasNext();) {
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
        this.position.set(position);
        return this;
    }

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Entity pointOfView) {
        getRenderer().draw(pos, world, screen, this, pointOfView);

        Subtile subtile = null;
        for (Iterator<Subtile> i = subtiles.iterator(); i.hasNext();) {
            subtile = i.next();
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
        if (!isOpaque()) {
            Tile b = world.getNextTileBelow(getPosition());
            if (b != null) {
                b.setReceivesSunlight(world, is);
                b.sunlightFilter = is ? this : null;
            }
        }
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

    public void damage(World world, float damage, Entity causer) {
        breakingProgress += damage;
        if (breakingProgress > hardness) {
            world.destroyTile(getPosition().x, getPosition().y, causer);
        }
    }

    public Vector2i getPosition() {
        return position.clone();
    }

}
