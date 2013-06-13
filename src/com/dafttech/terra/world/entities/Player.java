package com.dafttech.terra.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.lighting.Light;
import com.dafttech.terra.graphics.lighting.PointLight;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

public class Player extends Entity {
    public Player(Vector2 pos, World world) {
        super(pos, world, new Vector2(1.9f, 3.8f));
    }

    PointLight light = new PointLight(position, 150);

    @Override
    public void update(Player player, float delta) {
        super.update(player, delta);
        if (Gdx.input.isKeyPressed(Keys.A)) addForce(new Vector2(-14f * getUndergroundAcceleration(), 0));
        if (Gdx.input.isKeyPressed(Keys.D)) addForce(new Vector2(14f * getUndergroundAcceleration(), 0));

        if (Gdx.input.isKeyPressed(Keys.SPACE) && !this.isInAir()) addVelocity(new Vector2(0, -48));

        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            Position destroy = ((Vector2) Vector2.getMouse().add(getPosition()).sub(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2))
                    .toWorldPosition();
            getWorld().destroyTile(destroy.x, destroy.y);
        }

        light.setPosition(new Vector2(position.x + size.x * BLOCK_SIZE / 2, position.y + size.y * BLOCK_SIZE / 2));
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("error");
    }

    @Override
    public boolean isLightEmitter() {
        return true;
    }

    @Override
    public Light getEmittedLight() {
        return light;
    }
}
