package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.EntityArrow;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.resources.Resources;

public class ItemArrow extends ItemEntitySpawner {

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("arrow");
    }

    @Override
    public float getNextUseDelay(EntityLiving causer, Vector2 position, boolean leftClick) {
        return 0.2f;
    }

    @Override
    public boolean spawnEntity(EntityLiving causer, Vector2 position) {
        EntityArrow a = new EntityArrow(causer.getPosition(), causer.worldObj);
        a.setVelocity(Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)).mul(0.2f));
        return true;
    }
}
