package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.resources.Resources;

public class TileWeed extends Tile {

    public TileWeed() {
        super();
    }

    @Override
    public TextureRegion getImage() {
        return Resources.TILES.getImage("weed");
    }

    @Override
    public boolean canCollideWith(Entity entity) {
        return false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (TerraInfinita.rnd.nextInt(20) == 0) {
            tryGrow(position.addNew(TerraInfinita.rnd.nextInt(3) - 1, TerraInfinita.rnd.nextInt(3) - 1));
        }
    }

    public void tryGrow(Vector2i pos) {
        if (pos.getTile(world) != null && !(pos.getTile(world) instanceof TileGrass)) return;
        Tile tile = pos.addYNew(1).getTile(world);
        if (tile instanceof TileWeed || tile instanceof TileDirt) {
            pos.setTile(world, new TileWeed());
        }
    }
}
