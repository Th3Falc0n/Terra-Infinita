package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;

public interface ITileRenderRounded {
    public boolean isFlatTo(World world, Vector2i pos);

    public TextureRegion[] getEdgeImages();
}
