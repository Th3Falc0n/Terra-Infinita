package com.dafttech.terra.game.world.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.items.Item;
import com.dafttech.terra.game.world.items.inventories.Stack;
import com.dafttech.terra.game.world.items.persistence.Prototype;
import com.dafttech.terra.resources.Options;

public class EntityItem extends Entity {
    Item wrapped;

    public EntityItem(Vector2 pos, World world, Vector2 size, Item w) {
        super(pos, world, size);
        wrapped = w;
    }

    @Override
    public TextureRegion getImage() {
        return wrapped.getImage();
    }

    @Override
    public Prototype toPrototype() {
        return wrapped.toPrototype();
    }

    @Override
    public boolean collidesWith(Entity e) {
        return false;
    }

    @Override
    public void update(World world, float delta) {
        Vector2 p = getWorld().localPlayer().getPosition();
        Vector2 s = getWorld().localPlayer().getSize();

        Vector2 vp = p.$plus(s.x() * Options.BLOCK_SIZE() / 2, s.y() * Options.BLOCK_SIZE() / 2);
        vp.$minus(getPosition());

        if (vp.length$u00B2() < 400) {
            getWorld().localPlayer().inventory().add(Stack.apply(wrapped, 1));

            getWorld().removeEntity(this);
        }

        if (vp.length$u00B2() < 10000) {
            addForce(vp.normalized().$times(14f / vp.length$u00B2()));
        }

        super.update(world, delta);
    }
}
