package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.EntityRainbow;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.resources.Resources;

public class ItemRainbowGun extends ItemEntitySpawner {
    @Override
    public boolean spawnEntity(EntityLiving causer, Vector2 position) {
        EntityRainbow a = new EntityRainbow(causer.getPosition(), causer.worldObj);
        a.setVelocity(Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)).mul(0.08f));
        return true;
    }

    @Override
    public int getUsedItemNum(EntityLiving causer, Vector2 position) {
        return 0;
    }

    @Override
    public float getNextUseDelay(EntityLiving causer, Vector2 position, boolean leftClick) {
        return 0;
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ITEMS.getImage("rainbowgun");
    }
}
