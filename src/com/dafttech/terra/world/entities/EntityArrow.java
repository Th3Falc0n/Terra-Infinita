package com.dafttech.terra.world.entities;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.resources.Resources;
import com.dafttech.terra.world.Vector2;
import com.dafttech.terra.world.World;

public class EntityArrow extends Entity {

    public EntityArrow(Vector2 pos, World world) {
        super(pos, world, new Vector2(2, 0.6f));
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
    }

}
