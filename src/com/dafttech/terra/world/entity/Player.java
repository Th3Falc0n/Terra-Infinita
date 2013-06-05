package com.dafttech.terra.world.entity;

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
        if (Gdx.input.isKeyPressed(Keys.W)) accellerate(new Vector2(0, 5));
        if (Gdx.input.isKeyPressed(Keys.S)) accellerate(new Vector2(0, -5));
        if (Gdx.input.isKeyPressed(Keys.A)) accellerate(new Vector2(-5, 0));
        if (Gdx.input.isKeyPressed(Keys.D)) accellerate(new Vector2(5, 0));
    }
}
