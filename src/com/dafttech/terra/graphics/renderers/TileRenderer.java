package com.dafttech.terra.graphics.renderers;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.entities.Player;
import com.dafttech.terra.world.tiles.Tile;

public abstract class TileRenderer {
    public abstract void draw(AbstractScreen screen, Tile render, Player player, Object... rendererArguments);
}
