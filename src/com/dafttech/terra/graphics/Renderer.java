package com.dafttech.terra.graphics;

import com.dafttech.terra.entity.Player;
import com.dafttech.terra.world.Tile;

public abstract class Renderer {
    public abstract void draw(AbstractScreen screen, Tile tile, Player player);
}
