package com.dafttech.terra.game.world.tiles;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.EntityItem;
import com.dafttech.terra.game.world.subtiles.SubtileGrass;
import com.dafttech.terra.resources.Resources;

public class TileGrass extends Tile implements ITileInworldEvents, ITileInteraction {
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
    public boolean canCollideWith(Entity entity) {
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
    public void onTileUsed(Entity causer, EntityItem item) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTileSet() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isFlammable() {
        return true;
    }

    int spreadDistance = 3;

    @Override
    public void onTick(World world) {
        int spreadX = position.x + new Random().nextInt(spreadDistance * 2) - spreadDistance;
        int spreadY = position.y + new Random().nextInt(spreadDistance * 2) - spreadDistance;
        Tile spreadTile = world.getTile(spreadX, spreadY);
        if (spreadTile != null && spreadTile instanceof TileDirt && world.getTile(spreadX, spreadY - 1) == null) {
            if (spreadTile.hasSubtile(SubtileGrass.class)) {
                world.setTile(spreadX, spreadY - 1, new TileGrass(), true);
            }
        }
    }
}
