package com.dafttech.terra.game.world.tiles;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public abstract class TileFalling extends Tile implements ITileRenderOffset {
    private Vector2 renderOffset = new Vector2();
    private float createTime = 0;

    public TileFalling() {
    }

    @Override
    public void onTick(World world, float delta) {
        super.onTick(world, delta);
        fallIfPossible(world);
        if (!renderOffset.is()) {
            float possSpeed = getFallSpeed(world) * delta;
            if (renderOffset.x > 0) renderOffset.x -= possSpeed > renderOffset.x ? renderOffset.x : possSpeed;
            if (renderOffset.y > 0) renderOffset.y -= possSpeed > renderOffset.y ? renderOffset.y : possSpeed;
            if (renderOffset.x < 0) renderOffset.x += possSpeed > -renderOffset.x ? -renderOffset.x : possSpeed;
            if (renderOffset.y < 0) renderOffset.y += possSpeed > -renderOffset.y ? -renderOffset.y : possSpeed;
        }
    }

    public void fall(World world, int x, int y) {
        world.setTile(getPosition().add(x, y), this, true);
        renderOffset.x -= x;
        renderOffset.y -= y;
    }

    public void fallIfPossible(World world) {
        if (renderOffset.is()) {
            if (createTime == 0) createTime = world.time;
            if (createTime + getFallDelay(world) < world.time && world.getTile(getPosition().addY(1)).isReplacable()) {
                fall(world, 0, 1);
            }
        }
    }

    @Override
    public Vector2 getRenderOffset() {
        return renderOffset;
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
