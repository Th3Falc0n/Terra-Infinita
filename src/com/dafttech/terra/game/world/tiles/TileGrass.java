package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.items.Item;
import com.dafttech.terra.resources.Resources;

public class TileGrass extends Tile implements ITileInworldEvents {
    int grassIndex;

    public TileGrass() {
        super();
        grassIndex = TerraInfinita.rnd.nextInt(5);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("grass", grassIndex);
    }

    @Override
    public boolean onCollisionWith(Entity entity) {
        return false;
    }

    @Override
    public void onNeighborChange(Tile changed) {
        if (world.getTile(position.addNew(0, 1)) == null) world.destroyTile(position.getX(), position.getY(), null);
    }

    @Override
    public void onTileDestroyed(Entity causer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTilePlaced(Entity causer) {
        // TODO Auto-generated method stub

    }

    @Override
    @Deprecated
    public void onTileUsed(Entity causer, Item item) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTileSet() {
        // TODO Auto-generated method stub

    }
}
