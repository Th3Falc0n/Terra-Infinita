package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.resources.Resources;

public class TileSapling extends TileFalling {
    float grothDelay = 4;

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("sapling");
    }

    @Override
    public void onTick(World world, float delta) {
        super.onTick(world, delta);
        if (grothDelay <= 0) {
            world.setTile(getPosition(), new TileLog().setLiving(true), true);
        } else {
            grothDelay -= delta;
        }
    }

    @Override
    public float getNextUseDelay(Player causer, Vector2 position) {
        return 0.2f;
    }
}
