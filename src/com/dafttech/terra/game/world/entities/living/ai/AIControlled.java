package com.dafttech.terra.game.world.entities.living.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.input.InputHandler;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.tiles.Tile;

public class AIControlled extends ArtificialIntelligence {
    @Override
    public void update(float delta) {
        if (InputHandler.$.isKeyDown("LEFT")) assignedEntity.walkLeft();
        if (InputHandler.$.isKeyDown("RIGHT")) assignedEntity.walkRight();
        if (InputHandler.$.isKeyDown("JUMP") && !assignedEntity.isInAir()) assignedEntity.jump();
    }
}
