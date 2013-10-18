package com.dafttech.terra.game.world.entities;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.eventmanager.Event;
import com.dafttech.eventmanager.EventFilter;
import com.dafttech.eventmanager.EventListener;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.modules.ModuleHUDBottom;
import com.dafttech.terra.engine.input.InputHandler;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.particles.ParticleDust;
import com.dafttech.terra.game.world.items.inventories.Inventory;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileDirt;
import com.dafttech.terra.resources.Resources;

public class Player extends EntityLiving {
    public Player(Vector2 pos, World world) {
        super(pos, world, new Vector2(1.9f, 3.8f));
        Events.EVENTMANAGER.registerEventListener(this);

        hudBottom = new ModuleHUDBottom();
        hudBottom.create();

        hudBottom.slots[0].assignItem(new TileDirt(), inventory);
    }

    long left;
    boolean right;

    public Inventory inventory = new Inventory();

    public ModuleHUDBottom hudBottom;

    PointLight light;

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
            Vector2 mouseInWorldPos = Vector2.getMouse().add(getPosition()).sub(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            hudBottom.getActiveSlot().useAssignedItem(this, mouseInWorldPos);
        }

        if (!Gdx.input.isButtonPressed(Buttons.RIGHT) && right) {
            right = false;
        }

        if (light == null) {
            light = new PointLight(getPosition(), 95);
            light.setColor(new Color(255, 200, 40, 255));
        }
        light.setPosition(getPosition().add(size.x * BLOCK_SIZE / 2, size.y * BLOCK_SIZE / 2));

        for (int i = 0; i < 5; i++) {
            if (TerraInfinita.rnd.nextDouble() < delta * velocity.len() * 2f) {
                if (getUndergroundTile() != null && !inAir) {
                    new ParticleDust(getPosition().addNew(size.x * BLOCK_SIZE / 2, size.y * BLOCK_SIZE).addNew(
                            (TerraInfinita.rnd.nextFloat() - 0.5f) * BLOCK_SIZE * 2, (TerraInfinita.rnd.nextFloat() - 1f) * 4f), worldObj,
                            getUndergroundTile().getImage());
                }
            }
        }

        hudBottom.healthBar.setValue(getHealth() / getMaxHealth() * 100);
    }

    @EventListener(value = "KEYDOWN", filter = "filterOnBombKeyPressed")
    public void onBombKeyPressed(Event event) {
        new EntityDynamite(getPosition(), worldObj, 3, 4);
    }

    @EventFilter("filterOnBombKeyPressed")
    public String filter1() {
        return "BOMB";
    }

    @EventListener(value = "KEYDOWN", filter = "filterOnGlowstickKeyPressed")
    public void onGlowstickKeyPressed(Event event) {
        EntityGlowstick a = new EntityGlowstick(getPosition(), worldObj);
        a.setVelocity(Vector2.getMouse().sub(new Vector2(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2)).mul(0.08f));
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

    @Override
    public boolean isLightEmitter() {
        return true;
    }

    @Override
    public PointLight getEmittedLight() {
        return light;
    }
}
