package com.dafttech.terra.graphics;

import com.dafttech.terra.world.entity.Player;

public interface IRenderable {
    public void draw(AbstractScreen screen, Player player);
}
