package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.renderer.TileRenderer;
import com.dafttech.terra.engine.renderer.TileRendererMultiblock;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources$;

public class TileLeaf extends TileLog {
    @Override
    public TileLog getLog() {
        return new TileLeaf();
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.TILES().getImage("leaf");
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public boolean isFlatTo(World world, Vector2i pos) {
        return world.getTile(pos) instanceof TileLog;
    }

    @Override
    public Color getFilterColor() {
        return new Color(0.94f, 0.99f, 0.91f, 1);
    }

    @Override
    public TileRenderer getRenderer() {
        return new TileRendererMultiblock(new Vector2i(3, 3));
    }
}
