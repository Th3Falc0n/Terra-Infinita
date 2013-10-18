package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.EntityFlamingArrow;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.resources.Resources;

public class ItemArrow extends Item {

    @Override
    public void update(float delta) {
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("arrow");
    }

    @Override
    public boolean use(Player causer, Vector2 position) {
        EntityFlamingArrow a = new EntityFlamingArrow(position, causer.worldObj);
        a.setVelocity(Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)).mul(0.2f));
        return true;
    }

}
