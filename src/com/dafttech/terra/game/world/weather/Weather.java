package com.dafttech.terra.game.world.weather;

import com.dafttech.terra.game.world.World;

public abstract class Weather {
    public abstract void update(World world, float delta);
}
