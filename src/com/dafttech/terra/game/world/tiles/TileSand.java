package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.EntityItem;
import com.dafttech.terra.resources.Resources;

public class TileSand extends Tile implements ITileInworldEvents, ITileRenderOffset {
    private Vector2 renderOffset = new Vector2(0, 0);
    private float fallSpeed = 10f;

    public TileSand() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("sand");
    }

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

    public void fallIfPossible() {
        if (world.getTile(position.x, position.y + 1) == null) {
            fall(0, 1);
        }
    }

    public void fall(int x, int y) {
        world.setTile(position.x + x, position.y + y, this, true);
        renderOffset.x -= x;
        renderOffset.y -= y;
    }

    @Override
    public void onNeighborChange(Tile changed) {
        fallIfPossible();
    }

    @Override
    public void onTileDestroyed(Entity causer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTilePlaced(Entity causer) {

    }

    @Override
    public void onTileUsed(Entity causer, EntityItem item) {
        // TODO Auto-generated method stub

    }

    @Override
    public Vector2 getRenderOffset() {
        return renderOffset;
    }

    @Override
    public void onTileSet() {
        fallIfPossible();

    }
}
