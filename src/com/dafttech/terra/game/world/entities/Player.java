package com.dafttech.terra.game.world.entities;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.modules.ModuleHUDBottom;
import com.dafttech.terra.engine.input.InputHandler;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.Events;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.game.world.entities.particles.ParticleDust;
import com.dafttech.terra.game.world.items.ItemDigStaff;
import com.dafttech.terra.game.world.items.ItemDynamite;
import com.dafttech.terra.game.world.items.ItemFlamingArrow;
import com.dafttech.terra.game.world.items.ItemGlowstick;
import com.dafttech.terra.game.world.items.ItemRainbowGun;
import com.dafttech.terra.game.world.items.ItemWaterBucket;
import com.dafttech.terra.game.world.items.inventories.Inventory;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileDirt;
import com.dafttech.terra.game.world.tiles.TileSand;
import com.dafttech.terra.game.world.tiles.TileTorch;
import com.dafttech.terra.resources.Resources;

public class Player extends EntityLiving {
    public Player(Vector2 pos, World world) {
        super(pos, world, new Vector2(1.9f, 3.8f));
        Events.EVENTMANAGER.registerEventListener(this);

        hudBottom = new ModuleHUDBottom();
        hudBottom.create();

        hudBottom.slots[0].assignPair(new TileDirt(), inventory);
        hudBottom.slots[1].assignPair(new TileSand(), inventory);
        hudBottom.slots[2].assignPair(new ItemFlamingArrow(), inventory);
        hudBottom.slots[3].assignPair(new ItemGlowstick(), inventory);
        hudBottom.slots[4].assignPair(new TileTorch(), inventory);
        hudBottom.slots[5].assignPair(new ItemRainbowGun(), inventory);
        hudBottom.slots[6].assignPair(new ItemWaterBucket(), inventory);
        hudBottom.slots[7].assignPair(new ItemDigStaff(), inventory);

        inventory.add(new TileDirt(), 1000);
        inventory.add(new ItemFlamingArrow(), 100);
        inventory.add(new ItemGlowstick(), 100);
        inventory.add(new ItemDynamite(), 10);
        inventory.add(new ItemRainbowGun(), 1);
        inventory.add(new ItemWaterBucket(), 1);
        inventory.add(new TileTorch(), 100);
        inventory.add(new ItemDigStaff(), 20);
    }

    long left;
    boolean right;

    public Inventory inventory = new Inventory();

    public ModuleHUDBottom hudBottom;

    PointLight light;

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);
        if (InputHandler.$.isKeyDown("LEFT")) walkLeft();
        if (InputHandler.$.isKeyDown("RIGHT")) walkRight();

        if (InputHandler.$.isKeyDown("JUMP") && !this.isInAir()) jump();

        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            Vector2 mouseInWorldPos = Vector2.getMouse().add(getPosition()).sub(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            if (!hudBottom.getActiveSlot().useAssignedItem(this, mouseInWorldPos, true) && System.currentTimeMillis() - left > 10) {
                left = System.currentTimeMillis();
                Vector2i destroy = (Vector2.getMouse().add(getPosition()).sub(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2))
                        .toWorldPosition();
                Tile damagedTile = getWorld().getTile(destroy.x, destroy.y);
                if (damagedTile != null) damagedTile.damage(world, 0.2f, this);
            }
        }

        if (Gdx.input.isButtonPressed(Buttons.RIGHT) && !right) {
            Vector2 mouseInWorldPos = Vector2.getMouse().add(getPosition()).sub(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            hudBottom.getActiveSlot().useAssignedItem(this, mouseInWorldPos, false);
        }

        if (!Gdx.input.isButtonPressed(Buttons.RIGHT) && right) {
            right = false;
        }

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

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("player");
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Entity pointOfView) {
        Vector2 screenVec = this.getPosition().toRenderPosition(pointOfView.getPosition());

        screen.batch.setColor(color);
        screen.batch.draw(this.getImage(), screenVec.x, screenVec.y, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y);
        screen.batch.flush();
        // hudBottom.getActiveSlot().draw(screen, Entity pointOfView);
    }
}
