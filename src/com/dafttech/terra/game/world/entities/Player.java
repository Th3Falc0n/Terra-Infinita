package com.dafttech.terra.game.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventFilter;
import com.dafttech.eventmanager.EventListener;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.input.InputHandler;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.inventories.Inventory;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.resources.Resources;

public class Player extends Entity {
    public Player(Vector2 pos, World world) {
        super(pos, world, new Vector2(1.9f, 3.8f));
        Events.EVENTMANAGER.registerEventListener(this);
    }

    long left;
    boolean right;

    @Override
    public void update(float delta) {
        super.update(delta);
        if (InputHandler.$.isKeyDown("LEFT")) addForce(new Vector2(-10f * getCurrentAcceleration(), 0));
        if (InputHandler.$.isKeyDown("RIGHT")) addForce(new Vector2(10f * getCurrentAcceleration(), 0));

        if (InputHandler.$.isKeyDown("JUMP") && !this.isInAir()) addVelocity(new Vector2(0, -30));

        if (Gdx.input.isButtonPressed(Buttons.LEFT) && System.currentTimeMillis() - left > 10) {
            left = System.currentTimeMillis();
            Vector2i destroy = (Vector2.getMouse().add(getPosition()).sub(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2))
                    .toWorldPosition();
            Tile damagedTile = getWorld().getTile(destroy.x, destroy.y);
            if (damagedTile != null) damagedTile.damage(0.2f, this);
        }

        if (Gdx.input.isButtonPressed(Buttons.RIGHT) && !right) {
            right = true;
            EntityFlamingArrow a = new EntityFlamingArrow(position.clone(), worldObj);
            a.setVelocity(Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)).mul(0.2f));
            worldObj.addEntity(a);
        }

        if (!Gdx.input.isButtonPressed(Buttons.RIGHT) && right) {
            right = false;
        }
    }

    @EventListener(value = "KEYDOWN", filter = "filterOnBombKeyPressed")
    public void onBombKeyPressed(Event event) {
        EntityDynamite dynamite = new EntityDynamite(position.clone(), worldObj, 3, 4);
        worldObj.addEntity(dynamite);
    }

    @EventFilter("filterOnBombKeyPressed")
    public String filter1() {
        return "BOMB";
    }

    @EventListener(value = "KEYDOWN", filter = "filterOnGlowstickKeyPressed")
    public void onGlowstickKeyPressed(Event event) {
        EntityGlowstick a = new EntityGlowstick(position.clone(), worldObj);
        a.setVelocity(Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)).mul(0.08f));
        worldObj.addEntity(a);
    }

    @EventFilter("filterOnGlowstickKeyPressed")
    public String filter2() {
        return "GLOWSTICK";
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("player");
    }

    public Inventory getInventory() {
        // TODO Auto-generated method stub
        return null;
    }
}
