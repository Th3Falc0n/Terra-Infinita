package com.dafttech.terra.game.world.entities;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.models.EntityThrown;
import com.dafttech.terra.resources.Resources;

public class EntityArrow extends EntityThrown {
    public EntityArrow(Vector2 pos, World world) {
        super(pos, world, new Vector2(2, 0.6f));

        setGravityFactor(0.25f);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("arrow");
    }
    
    @Override
    public float getInAirFriction() {
        return 0.025f;
    }
}
