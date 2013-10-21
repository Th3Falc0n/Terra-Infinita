package com.dafttech.terra.game.world.tiles;

import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public interface ITileInworldEvents {
    public void onNeighborChange(World world, Tile changed);

    public void onTileDestroyed(World world, Entity causer);

    public void onTilePlaced(World world, Entity causer);

    public void onTileSet(World world);
}
