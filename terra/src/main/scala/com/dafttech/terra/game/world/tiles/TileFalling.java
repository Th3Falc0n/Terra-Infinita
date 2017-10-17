package com.dafttech.terra.game.world.tiles;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.renderer.TileRenderer;
import com.dafttech.terra.engine.renderer.TileRendererBlock;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public abstract class TileFalling extends Tile {
    private Vector2 renderOffset = Vector2.Null();
    private float createTime = 0;

    public TileFalling() {
    }

    @Override
    public void onTick(World world, float delta) {
        super.onTick(world, delta);
        fallIfPossible(world);
        if (!renderOffset.isNull()) {
            float possSpeed = getFallSpeed(world) * delta;
            if (renderOffset.x() > 0)
                renderOffset = renderOffset.$minus(possSpeed > renderOffset.x() ? renderOffset.x() : possSpeed, 0);
            if (renderOffset.y() > 0)
                renderOffset = renderOffset.$minus(0, possSpeed > renderOffset.y() ? renderOffset.y() : possSpeed);
            if (renderOffset.x() < 0)
                renderOffset = renderOffset.$plus(possSpeed > -renderOffset.x() ? -renderOffset.x() : possSpeed, 0);
            if (renderOffset.y() < 0)
                renderOffset = renderOffset.$plus(0, possSpeed > -renderOffset.y() ? -renderOffset.y() : possSpeed);
        }
    }

    public void fall(World world, int x, int y) {
        renderOffset = renderOffset.$minus(x, y);
        world.setTile(getPosition().$plus(x, y), this, true);
    }

    public void fallIfPossible(World world) {
        if (renderOffset.isNull()) {
            if (createTime == 0) createTime = world.time();
            if (createTime + getFallDelay(world) < world.time() && world.getTile(getPosition().$plus(0, 1)).isReplacable()) {
                fall(world, 0, 1);
            }
        }
    }

    public Vector2 getRenderOffset() {
        return renderOffset;
    }

    @Override
    public TileRenderer getRenderer() {
        TileRendererBlock tileRenderer = new TileRendererBlock();
        tileRenderer.setOffset(getRenderOffset());
        return tileRenderer;
    }

    @Override
    public void onTileSet(World world) {
        fallIfPossible(world);
    }

    @Override
    public void onNeighborChange(World world, Tile changed) {
        fallIfPossible(world);
    }

    @Override
    public void onTileDestroyed(World world, Entity causer) {

    }

    @Override
    public void onTilePlaced(World world, Entity causer) {

    }

    public abstract float getFallSpeed(World world);

    public abstract float getFallDelay(World world);
}
