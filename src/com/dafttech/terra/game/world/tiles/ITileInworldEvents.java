package com.dafttech.terra.game.world.tiles;

import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.items.Item;

public interface ITileInworldEvents {
    public void onNeighborChange(Tile changed);
    public void onTileDestroyed(Entity causer); 
    public void onTilePlaced(Entity causer);
    @Deprecated //TODO: implement!
    public void onTileUsed(Entity causer, Item item);
}
