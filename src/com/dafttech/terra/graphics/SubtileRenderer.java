package com.dafttech.terra.graphics;

import com.dafttech.terra.world.Subtile;
import com.dafttech.terra.world.entity.Player;

public abstract class SubtileRenderer {
    public abstract void draw(AbstractScreen screen, Subtile render, Player player, Object... rendererArguments);
}
