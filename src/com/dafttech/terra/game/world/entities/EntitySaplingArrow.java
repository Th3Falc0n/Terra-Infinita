package com.dafttech.terra.game.world.entities;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileSapling;

public class EntitySaplingArrow extends EntityFlamingArrow {

    public EntitySaplingArrow(Vector2 pos, World world) {
        super(pos, world);
    }

    @Override
    public void placeBlockOnHit(int x, int y) {
        Tile spreadTile = worldObj.getTile(x, y);
        if (spreadTile.isReplacable()) {
            worldObj.setTile(x, y, new TileSapling(), true);
        }
    }
}
