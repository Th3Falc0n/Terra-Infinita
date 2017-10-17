package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.EntityFlamingArrow;
import com.dafttech.terra.game.world.entities.models.EntityLiving;

public class ItemFlamingArrow extends ItemArrow {
    @Override
    public boolean spawnEntity(EntityLiving causer, Vector2 position) {
        EntityFlamingArrow a = new EntityFlamingArrow(causer.getPosition(), causer.worldObj);
        a.setVelocity(Vector2.mousePos().$minus(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2).$times(0.2f));
        return true;
    }
}
