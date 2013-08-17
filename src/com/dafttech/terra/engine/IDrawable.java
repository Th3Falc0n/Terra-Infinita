package com.dafttech.terra.engine;

import com.dafttech.terra.game.world.entities.Entity;

public interface IDrawable {
    public void update(float delta);

    public void draw(AbstractScreen screen, Entity pointOfView);
}
