package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.Gdx;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.EntitySaplingArrow;
import com.dafttech.terra.game.world.entities.Player;

public class ItemSaplingArrow extends ItemArrow {
    @Override
    public boolean spawnEntity(Player causer, Vector2 position) {
        EntitySaplingArrow a = new EntitySaplingArrow(causer.getPosition(), causer.worldObj);
        a.setVelocity(Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)).mul(0.2f));
        return true;
    }
}