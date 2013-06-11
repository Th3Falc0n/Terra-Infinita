package com.dafttech.terra.graphics.passes;

import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.world.World;
import com.dafttech.terra.world.entities.Player;

public abstract class RenderingPass {
    public abstract void applyPass(AbstractScreen screen, Player player, World w);
}
