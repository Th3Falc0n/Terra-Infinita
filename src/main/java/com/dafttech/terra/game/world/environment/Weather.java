package com.dafttech.terra.game.world.environment;

import com.dafttech.terra.game.world.World;

public abstract class Weather {
    public abstract void update(World world, float delta);

    public abstract float getWindSpeed(World world);
}
