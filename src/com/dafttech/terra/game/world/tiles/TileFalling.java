package com.dafttech.terra.game.world.tiles;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.EntityItem;

public abstract class TileFalling extends Tile implements ITileInworldEvents, ITileRenderOffset {
    private Vector2 renderOffset = new Vector2();
    private float fallSpeed = 10f;

    @Override
    public void update(float delta) {
        super.update(delta);
        if (renderOffset.x != 0 || renderOffset.y != 0) {
            if (renderOffset.x > 0) renderOffset.x -= fallSpeed * delta;
            if (renderOffset.y > 0) renderOffset.y -= fallSpeed * delta;
            if (renderOffset.x < 0) renderOffset.x += fallSpeed * delta;
            if (renderOffset.y < 0) renderOffset.y += fallSpeed * delta;
        }
    }

    public void fall(int x, int y) {
        world.setTile(getPosition().add(x, y), this, true);
        renderOffset.x -= x;
        renderOffset.y -= y;
    }

    public void fallIfPossible() {
        if (world.getTile(getPosition().addY(1)).isAir()) {
            fall(0, 1);
        }
    }

    @Override
    public Vector2 getRenderOffset() {
        return renderOffset;
    }

    @Override
    public void onTileSet() {
        fallIfPossible();

    }

    @Override
    public void onNeighborChange(Tile changed) {
        fallIfPossible();
    }

    @Override
    public void onTileDestroyed(Entity causer) {

    }

    @Override
    public void onTilePlaced(Entity causer) {

    }

    @Override
    public void onTileUsed(Entity causer, EntityItem item) {
    }
}
