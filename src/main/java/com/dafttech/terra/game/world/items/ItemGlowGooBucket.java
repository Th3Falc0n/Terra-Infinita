package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.game.world.subtiles.SubtileGlowGoo;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.resources.Resources;

public class ItemGlowGooBucket extends Item {

    @Override
    public void update(float delta) {
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("glowgoo", 0);
    }

    @Override
    public boolean use(EntityLiving causer, Vector2 position) {
        Tile tile = causer.worldObj.getTile(position.toWorldPosition());
        tile.addSubtile(new SubtileGlowGoo());
        return true;
    }

    @Override
    public int getUsedItemNum(EntityLiving causer, Vector2 position) {
        return 0;
    }

    @Override
    public float getNextUseDelay(EntityLiving causer, Vector2 position, boolean leftClick) {
        return 0.08f;
    }

}
