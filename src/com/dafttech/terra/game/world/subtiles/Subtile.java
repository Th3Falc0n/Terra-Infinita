package com.dafttech.terra.game.world.subtiles;

import com.badlogic.gdx.graphics.Color;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInWorld;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.renderer.SubtileRenderer;
import com.dafttech.terra.engine.renderer.SubtileRendererMask;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.tiles.Tile;

public abstract class Subtile extends Item implements IDrawableInWorld {
    Tile tile;

    @Override
    public boolean use(EntityLiving causer, Vector2 position) {
        // TODO Auto-generated method stub
        return false;
    }

    public Subtile() {
    }

    public SubtileRenderer getRenderer() {
        return SubtileRendererMask.$Instance;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile t) {
        tile = t;
    }

    public boolean canBePlacedOn(Tile tile) {
        return true;
    }

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Entity pointOfView) {
        getRenderer().draw(screen, this, pointOfView);
    }

    @Override
    public void update(World world, float delta) {

    }

    @Override
    public void update(float delta) {

    }

    public final void tick(World world, float delta) {
        onTick(world, delta);
    }

    public void onTick(World world, float delta) {

    }

    public boolean isTileIndependent() {
        return false;
    }

    public boolean providesSunlightFilter() {
        return false;
    }

    public Color getFilterColor() {
        return Color.WHITE;
    }
}
