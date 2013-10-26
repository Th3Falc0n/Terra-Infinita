package com.dafttech.terra.game.world.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.models.EntityThrown;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.game.world.tiles.TileAir;
import com.dafttech.terra.resources.Resources;

public class EntityArrow extends EntityThrown {
    Tile stuckIn = new TileAir();

    public EntityArrow(Vector2 pos, World world) {
        super(pos, world, new Vector2(2, 0.6f));

        setGravityFactor(0.25f);
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("arrow");
    }

    @Override
    public void update(World world, float delta) {
        stuckIn = world.getTile(stuckIn.getPosition());

        if (stuckIn.isAir() || !stuckIn.isCollidableWith(this)) {
            setGravityFactor(0.25f);
        }

        super.update(world, delta);
    }
    
    @Override
    public boolean collidesWith(Entity e) {
        return !(e instanceof Player);
    }

    @Override
    public void onTerrainCollision(Tile tile) {
        stuckIn = tile;
        setGravityFactor(0);
        setVelocity(new Vector2(0, 0));
    }

    @Override
    public float getInAirFriction() {
        return 0.025f;
    }
}
