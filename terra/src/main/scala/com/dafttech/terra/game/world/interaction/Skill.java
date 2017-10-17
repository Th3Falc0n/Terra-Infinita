package com.dafttech.terra.game.world.interaction;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.IDrawableInventory;
import com.dafttech.terra.engine.Vector2;

public abstract class Skill implements IDrawableInventory {
    public abstract TextureRegion getImage();

    public enum ActivationMode {
        Execute, Toggle, Hold, Passive
    }

    @Override
    public void drawInventory(Vector2 pos, AbstractScreen screen) {
        screen.batch().draw(getImage(), pos.x() + 4, pos.y() + 4, 24, 24);
    }

    @Override
    public void update(float delta) {
        // NOPE!
    }
}
