package com.dafttech.terra.game.world.subtiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;
import com.dafttech.terra.graphics.AbstractScreen;
import com.dafttech.terra.graphics.IDrawable;
import com.dafttech.terra.graphics.renderer.SubtileRenderer;
import com.dafttech.terra.graphics.renderer.SubtileRendererMask;

public class Subtile implements IDrawable {
    Tile tile;
    TextureRegion image;

    public Subtile(Tile t, TextureRegion textureRegion) {
        tile = t;
        image = textureRegion;
    }

    public SubtileRenderer getRenderer() {
        return SubtileRendererMask.$Instance;
    }

    public TextureRegion getImage() {
        return image;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile t) {
        tile = t;
    }

    public boolean canBePlacedOn(Tile tile) {
        return true;
    }

    @Override
    public void draw(AbstractScreen screen, Entity pointOfView) {
        getRenderer().draw(screen, this, pointOfView);
    }

    @Override
    public void update(float delta) {
        // TODO Auto-generated method stub

    }
}
