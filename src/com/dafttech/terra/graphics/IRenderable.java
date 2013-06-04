package com.dafttech.terra.graphics;

import com.dafttech.terra.world.entity.Player;

public interface IRenderable {
    public void update(Player player, float delta);
    public void draw(AbstractScreen screen, Player player);
}
