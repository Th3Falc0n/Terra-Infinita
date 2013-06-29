package com.dafttech.terra.graphics;

import com.dafttech.terra.world.entities.Entity;

public interface IDrawable {
    public void update(float delta);

    public void draw(AbstractScreen screen, Entity pointOfView);
}
