package com.dafttech.terra.engine;

import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;

public interface IDrawableInWorld {
    public void update(World world, float delta);

    public void draw(Vector2 pos, World world, AbstractScreen screen, Entity pointOfView);
}
