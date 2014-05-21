package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.EntityDiggerBeam;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.resources.Resources;

public class ItemDigStaff extends ItemEntitySpawner {
    @Override
    public boolean spawnEntity(EntityLiving causer, Vector2 position) {
        EntityDiggerBeam a = new EntityDiggerBeam(causer.getPosition(), causer.worldObj);
        a.setVelocity(Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)).mul(0.2f));
        return false;
    }

    @Override
    public float getNextUseDelay(EntityLiving causer, Vector2 position, boolean leftClick) {
        return 1;
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ITEMS.getImage("digStaff");
    }
}