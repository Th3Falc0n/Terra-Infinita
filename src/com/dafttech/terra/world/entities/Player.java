package com.dafttech.terra.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Position;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class Player extends Entity {
    public Player(Vector2 pos, World world) {
        super(pos, world, new Vector2(1.9f, 3.8f));
    }

    @Override
    public void update(Player player, float delta) {
        super.update(player, delta);
        if (Gdx.input.isKeyPressed(Keys.A)) addForce(new Vector2(-14f * getCurrentAcceleration(), 0));
        if (Gdx.input.isKeyPressed(Keys.D)) addForce(new Vector2(14f * getCurrentAcceleration(), 0));

        if (Gdx.input.isKeyPressed(Keys.SPACE) && !this.isInAir()) addVelocity(new Vector2(0, -48));

        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            Position destroy = ((Vector2) Vector2.getMouse().add(getPosition()).sub(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2))
                    .toWorldPosition();
            getWorld().destroyTile(destroy.x, destroy.y);
        }

        if (Gdx.input.isButtonPressed(Buttons.RIGHT)) {
            EntityFlamingArrow a = new EntityFlamingArrow(new Vector2(position), worldObj);

            a.setVelocity((Vector2) Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)));

            worldObj.addEntity(a);
        }
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("player");
    }
}
