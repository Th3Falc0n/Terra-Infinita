package com.dafttech.terra.graphics;

import com.dafttech.terra.game.world.entities.Entity;

public interface IDrawable {
    public void update(float delta);

    public void draw(AbstractScreen screen, Entity pointOfView);
}
