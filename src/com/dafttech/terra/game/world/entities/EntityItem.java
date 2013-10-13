package com.dafttech.terra.game.world.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.items.Item;
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
    public void update(float delta) {
        Vector2 p = getWorld().localPlayer.getPosition();
        Vector2 s = getWorld().localPlayer.getSize();

        Vector2 vp = new Vector2(p.addNew(s.x * Options.BLOCK_SIZE / 2, s.y * Options.BLOCK_SIZE / 2));
        vp.sub(position);

        if (vp.len() < 20) {
            getWorld().removeEntity(this);
        }

        if (vp.len() < 100) {
            addForce(vp.nor().mul(14f / vp.len2()));
        }

        super.update(delta);
    }
}
