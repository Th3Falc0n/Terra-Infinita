package com.dafttech.terra.world.entity;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class Player extends Entity {
    public Player(Vector2 pos, World world) {
        super(pos, world);
        setSize(1.9f, 3.8f);
    }

    @Override
    public void update(Player player, float delta) {
        super.update(player, delta);
        if (Gdx.input.isKeyPressed(Keys.A)) addForce(new Vector2(-14f * getUndergroundAcceleration(), 0));
        if (Gdx.input.isKeyPressed(Keys.D)) addForce(new Vector2(14f * getUndergroundAcceleration(), 0));

        if (Gdx.input.isKeyPressed(Keys.SPACE) && !this.isInAir()) addVelocity(new Vector2(0, 48));

        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            Position destroy = ((Vector2)Vector2.getMouse().add(position).sub(Gdx.graphics.getWidth() / 2,
                    Gdx.graphics.getHeight() / 2)).toWorldPosition();
            worldObj.destroyTile(destroy.x, destroy.y);
        }
    }
}
