package com.dafttech.terra.game.world.items;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.EntityDynamite;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.resources.Resources$;

public class ItemDynamite extends ItemEntitySpawner {

    @Override
    public boolean spawnEntity(EntityLiving causer, Vector2 position) {
        new EntityDynamite(causer.getPosition(), causer.worldObj, 3, 4);
        return true;
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.ENTITIES().getImage("dynamite");
    }

    @Override
    public float getNextUseDelay(EntityLiving causer, Vector2 position, boolean leftClick) {
        return 1;
    }
}
