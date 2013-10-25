package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.EntityGlowstick;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.resources.Resources;

public class ItemGlowstick extends ItemEntitySpawner {

    @Override
    public boolean spawnEntity(Player causer, Vector2 position) {
        EntityGlowstick a = new EntityGlowstick(causer.getPosition(), causer.worldObj);
        a.setVelocity(Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)).mul(0.08f));
        return true;
    }

    @Override
    public float getNextUseDelay(Player causer, Vector2 position, boolean leftClick) {
        return 0.4f;
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("glowstick");
    }

}
