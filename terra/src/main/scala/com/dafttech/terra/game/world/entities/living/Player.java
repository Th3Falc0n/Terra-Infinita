package com.dafttech.terra.game.world.entities.living;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.engine.gui.modules.ModuleCrafting;
import com.dafttech.terra.engine.gui.modules.ModuleHUDBottom;
import com.dafttech.terra.engine.gui.modules.ModuleInventory;
import com.dafttech.terra.engine.input.InputHandler$;
import com.dafttech.terra.engine.lighting.PointLight;
import com.dafttech.terra.game.Events$;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.entities.models.EntityLiving;
import com.dafttech.terra.game.world.entities.particles.ParticleDust;
import com.dafttech.terra.game.world.interaction.Skill;
import com.dafttech.terra.game.world.items.*;
import com.dafttech.terra.game.world.items.inventories.Inventory;
import com.dafttech.terra.game.world.items.inventories.Stack;
import com.dafttech.terra.game.world.tiles.*;
import com.dafttech.terra.resources.Options;
import com.dafttech.terra.resources.Resources$;

public class Player extends EntityLiving {
    public Player(Vector2 pos, World world) {
        super(pos, world, new Vector2(1.9f, 3.8f));
        Events$.MODULE$.EVENTMANAGER().registerEventListener(this);

        hudBottom = new ModuleHUDBottom();
        hudBottom.create();

        guiInventory = new ModuleInventory(inventory);
        guiInventory.create();

        guiCrafting = new ModuleCrafting(this);
        guiCrafting.create();

        hudBottom.slots()[0].assignStack(new Stack(new TileDirt(), 1000));
        hudBottom.slots()[1].assignStack(new Stack(new ItemFlamingArrow(), 10000));
        hudBottom.slots()[2].assignStack(new Stack(new ItemGlowstick(), 100));
        hudBottom.slots()[3].assignStack(new Stack(new ItemDynamite(), 100));
        hudBottom.slots()[4].assignStack(new Stack(new ItemRainbowGun(), 1));
        hudBottom.slots()[5].assignStack(new Stack(new ItemWaterBucket(), 1));
        hudBottom.slots()[6].assignStack(new Stack(new TileTorch(), 10000));
        hudBottom.slots()[7].assignStack(new Stack(new ItemDigStaff(), 1));
        inventory.add(new Stack(new TileSapling(), 1000));
        inventory.add(new Stack(new TileFence(), 1000));
        inventory.add(new Stack(new TileFire(), 1000));
    }

    long left;
    boolean right;

    public Inventory inventory = new Inventory();
    public Inventory equip = new Inventory();

    public ModuleHUDBottom hudBottom;
    public ModuleInventory guiInventory;
    public ModuleCrafting guiCrafting;

    PointLight light;

    @Override
    public void update(World world, float delta) {
        super.update(world, delta);
        if (InputHandler$.MODULE$.isKeyDown("LEFT")) walkLeft();
        if (InputHandler$.MODULE$.isKeyDown("RIGHT")) walkRight();

        if (InputHandler$.MODULE$.isKeyDown("JUMP") && !this.isInAir()) jump();

        if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
            Vector2 mouseInWorldPos = Vector2.mousePos().$plus(getPosition()).$minus(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            if (!hudBottom.getActiveSlot().useAssignedItem(this, mouseInWorldPos, true) && System.currentTimeMillis() - left > 10) {
                left = System.currentTimeMillis();
                Vector2i destroy = (Vector2.mousePos().$plus(getPosition()).$minus(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2))
                        .toWorldPosition();
                Tile damagedTile = getWorld().getTile(destroy.x(), destroy.y());
                if (damagedTile != null) damagedTile.damage(world, 0.2f, this);
            }
        }

        if (Gdx.input.isButtonPressed(Buttons.RIGHT) && !right) {
            Vector2 mouseInWorldPos = Vector2.mousePos().$plus(getPosition()).$minus(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
            hudBottom.getActiveSlot().useAssignedItem(this, mouseInWorldPos, false);
        }

        if (!Gdx.input.isButtonPressed(Buttons.RIGHT) && right) {
            right = false;
        }

        for (int i = 0; i < 5; i++) {
            if (TerraInfinita.rnd().nextDouble() < delta * velocity.length() * 2f) {
                if (getUndergroundTile() != null && !inAir) {
                    new ParticleDust(getPosition().$plus(size.x() * Options.BLOCK_SIZE() / 2, size.y() * Options.BLOCK_SIZE()).$plus(
                            (TerraInfinita.rnd().nextFloat() - 0.5f) * Options.BLOCK_SIZE() * 2, (TerraInfinita.rnd().nextFloat() - 1f) * 4f), worldObj,
                            getUndergroundTile().getImage());
                }
            }
        }

        hudBottom.healthBar().setValue(getHealth() / getMaxHealth() * 100);

        guiInventory.update(delta);
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.ENTITIES().getImage("player");
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Entity pointOfView) {
        Vector2 screenVec = this.getPosition().toRenderPosition(pointOfView.getPosition());

        screen.batch().setColor(color);
        screen.batch().draw(this.getImage(), screenVec.xFloat(), screenVec.yFloat(), Options.BLOCK_SIZE() * size.xFloat(), Options.BLOCK_SIZE() * size.yFloat());
        screen.batch().flush();
        // hudBottom.getActiveSlot().draw(screen, Entity pointOfView);
    }

    public Skill getSkillForID(int skillID) {
        return null;
    }
}
