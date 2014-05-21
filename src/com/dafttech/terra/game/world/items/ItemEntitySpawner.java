package com.dafttech.terra.game.world.items;

import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.entities.Player;
import com.dafttech.terra.game.world.entities.models.EntityLiving;

public abstract class ItemEntitySpawner extends Item {

    @Override
    public void update(float delta) {
    }

    @Override
    public boolean use(EntityLiving causer, Vector2 position) {
        return spawnEntity(causer, position);
    }

    public abstract boolean spawnEntity(EntityLiving causer, Vector2 position);
}
