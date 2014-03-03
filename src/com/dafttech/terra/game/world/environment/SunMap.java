package com.dafttech.terra.game.world.environment;

import java.util.HashMap;

import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.tiles.Tile;

public class SunMap {
    private HashMap<Integer, Integer> heights = new HashMap<Integer, Integer>();
    private HashMap<Integer, Tile> topTiles = new HashMap<Integer, Tile>();

    private void unsetSunlightForX(World w, Tile t) {
        if (topTiles.containsKey(new Integer(t.getPosition().x))) {
            topTiles.get(new Integer(t.getPosition().x)).setReceivesSunlight(w, false);
        }
    }

    private void setSunlightForX(World w, Tile t) {
        topTiles.put(new Integer(t.getPosition().x), t);
        t.setReceivesSunlight(w, true);
    }

    private int getHeightForX(int x) {
        if (heights.containsKey(new Integer(x))) {
            return heights.get(new Integer(x)).intValue();
        }
        return Integer.MAX_VALUE;
    }

    private void setHeightForX(int x, int h) {
        heights.put(new Integer(x), h);
    }

    private Tile getReceivingTile(int x) {
        return topTiles.get(new Integer(x));
    }

    private boolean isReceivingTile(Tile t) {
        if(getReceivingTile(t.getPosition().x) == t) {
            return true;
        }
        return false;
    }

    public void postTilePlace(World w, Tile t) {
        if(!t.isAir()) {
            if(getHeightForX(t.getPosition().x) > t.getPosition().y) {
                setHeightForX(t.getPosition().x, t.getPosition().y);
                unsetSunlightForX(w, t);
                setSunlightForX(w, t);
            }
            else if(getReceivingTile(t.getPosition().x) != null)
            {
                unsetSunlightForX(w, getReceivingTile(t.getPosition().x));
                setSunlightForX(w, getReceivingTile(t.getPosition().x));
            }
        }
    }

    public void postTileRemove(World w, Tile t) {
        if (isReceivingTile(t)) {
            Tile b = w.getNextTileBelow(t.getPosition());
            unsetSunlightForX(w, t);
            setSunlightForX(w, b);
            setHeightForX(b.getPosition().x, b.getPosition().y);
        }
        else if(getReceivingTile(t.getPosition().x) != null)
        {
            unsetSunlightForX(w, getReceivingTile(t.getPosition().x));
            setSunlightForX(w, getReceivingTile(t.getPosition().x));
        }
    }
}
