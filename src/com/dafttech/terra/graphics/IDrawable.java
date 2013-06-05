package com.dafttech.terra.graphics;

import com.dafttech.terra.world.entities.Player;

public interface IDrawable {
    public void update(Player player, float delta);

    public void draw(AbstractScreen screen, Player player);
}
