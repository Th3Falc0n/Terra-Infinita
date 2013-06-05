package com.dafttech.terra.graphics;

import com.dafttech.terra.world.Tile;
import com.dafttech.terra.world.entity.Player;

public abstract class TileRenderer {
    public abstract void draw(AbstractScreen screen, Tile render, Player player, Object... rendererArguments);
}
