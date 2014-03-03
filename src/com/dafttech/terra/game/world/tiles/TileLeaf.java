package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.resources.Resources;

public class TileLeaf extends TileLog {
    @Override
    public TileLog getLog() {
        return new TileLeaf();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("leaf");
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
    public TextureRegion[] getEdgeImages() {
        return Resources.TILES.getRoundedImageEdges("leaf");
    }

    @Override
    public Color getFilterColor() {
        return Color.GREEN;// new Color(0.94f, 0.94f, 0.99f, 1);
    }

}
