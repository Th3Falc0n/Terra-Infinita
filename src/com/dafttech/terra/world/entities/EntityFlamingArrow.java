package com.dafttech.terra.world.entities;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.lighting.PointLight;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class EntityFlamingArrow extends EntityArrow {

    PointLight light;
    
    public EntityFlamingArrow(Vector2 pos, World world) {
        super(pos, world);
        // TODO Auto-generated constructor stub
    }

    @Override
    public TextureRegion getImage() {
        return Resources.ENTITIES.getImage("arrow");
    }

    @Override
    public void draw(AbstractScreen screen, Player player) {
        Vector2 screenVec = this.getPosition().toRenderPosition(player.getPosition());
        
        Vector2 v = new Vector2(velocity);
        
        screen.batch.draw(this.getImage(), screenVec.x, screenVec.y, BLOCK_SIZE * size.x / 2, BLOCK_SIZE * size.y / 2, BLOCK_SIZE * size.x, BLOCK_SIZE * size.y, 1, 1, v.angle());
        
        if (light == null) light = new PointLight(position, 30);

        light.setPosition((Vector2) new Vector2(position).add(BLOCK_SIZE / 2, BLOCK_SIZE / 2));
    }
    
    @Override
    public float getInAirFriction() {
        return 0.025f;
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
