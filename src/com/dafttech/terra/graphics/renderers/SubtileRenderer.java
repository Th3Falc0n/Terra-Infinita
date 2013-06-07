package com.dafttech.terra.graphics.renderers;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.entities.Player;
import com.dafttech.terra.world.subtiles.Subtile;

public abstract class SubtileRenderer {
    public abstract void draw(AbstractScreen screen, Subtile render, Player player, Object... rendererArguments);
}
