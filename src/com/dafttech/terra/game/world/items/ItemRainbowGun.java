package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.EntityRainbow;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.resources.Resources;

public class ItemRainbowGun extends ItemEntitySpawner {
    @Override
    public boolean spawnEntity(Player causer, Vector2 position) {
        EntityRainbow a = new EntityRainbow(causer.getPosition(), causer.worldObj);
        a.setVelocity(Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)).mul(0.08f));
        return true;
    }

    @Override
    public int getUsedItemNum(Player causer, Vector2 position) {
        return 0;
    }

    @Override
    public float getNextUseDelay(Player causer, Vector2 position) {
        return 0;
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ITEMS.getImage("rainbowgun");
    }
}
