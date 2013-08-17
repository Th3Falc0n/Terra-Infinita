package com.dafttech.terra.game.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.input.InputHandler;
import com.dafttech.terra.game.world.Position;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class Player extends Entity {
    public Player(Vector2 pos, World world) {
        super(pos, world, new Vector2(1.9f, 3.8f));
    }

    boolean right;

    @Override
    public void update(float delta) {
        super.update(delta);
        if (InputHandler.isKeyDown("LEFT")) addForce(new Vector2(-10f * getCurrentAcceleration(), 0));
        if (InputHandler.isKeyDown("RIGHT")) addForce(new Vector2(10f * getCurrentAcceleration(), 0));

        if (InputHandler.isKeyDown("JUMP") && !this.isInAir()) addVelocity(new Vector2(0, -30));

        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            Position destroy = ((Vector2) Vector2.getMouse().add(getPosition()).sub(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2))
                    .toWorldPosition();
            getWorld().destroyTile(destroy.x, destroy.y);
        }

        if (Gdx.input.isButtonPressed(Buttons.RIGHT) && !right) {
            right = true;
            EntityFlamingArrow a = new EntityFlamingArrow(new Vector2(position), worldObj);

            a.setVelocity((Vector2) Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)));

            worldObj.addEntity(a);
        }

        if (!Gdx.input.isButtonPressed(Buttons.RIGHT) && right) {
            right = false;
        }
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("player");
    }
}
