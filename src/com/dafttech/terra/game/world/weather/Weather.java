package com.dafttech.terra.game.world.weather;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.dafttech.terra.game.world.World;

public abstract class Weather {

    public abstract void update(World world, float delta);
}
