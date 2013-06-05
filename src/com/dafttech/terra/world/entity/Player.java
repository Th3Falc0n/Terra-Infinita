package com.dafttech.terra.world.entity;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class Player extends Entity {
    public Player(Vector2 pos, World world) {
        super(pos, world);
    }
    @Override
    public void update(Player player, float delta) {
        super.update(player, delta);
        if (Gdx.input.isKeyPressed(Keys.A)) addForce(new Vector2(-10f * getUndergroundAcceleration(), 0));
        if (Gdx.input.isKeyPressed(Keys.D)) addForce(new Vector2(10f * getUndergroundAcceleration(), 0));
        
        if (Gdx.input.isKeyPressed(Keys.SPACE) && !this.isInAir()) addVelocity(new Vector2(0, 75));
        
        
    }
}
