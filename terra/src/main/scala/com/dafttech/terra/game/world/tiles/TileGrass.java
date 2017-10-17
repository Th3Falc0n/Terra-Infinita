package com.dafttech.terra.game.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.TerraInfinita;
import com.dafttech.terra.engine.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.items.persistence.Persistent;
import com.dafttech.terra.game.world.subtiles.SubtileGrass;
import com.dafttech.terra.resources.Resources$;

import java.util.Random;

public class TileGrass extends Tile {
    @Persistent
    int grassIndex;

    public TileGrass() {
        super();
        grassIndex = TerraInfinita.rnd().nextInt(5);
    }

    @Override
    public TextureRegion getImage() {
        return Resources$.MODULE$.TILES().getImage("grass", grassIndex);
    }

    @Override
    public boolean isCollidableWith(Entity entity) {
        return false;
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public void onNeighborChange(World world, Tile changed) {
        if (world.getTile(getPosition().$plus(0, 1)).isAir())
            world.destroyTile(getPosition().x(), getPosition().y(), null);
    }

    @Override
    public void onTileDestroyed(World world, Entity causer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTilePlaced(World world, Entity causer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTileSet(World world) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isFlammable() {
        return true;
    }

    @Override
    public boolean isReplacable() {
        return true;
    }

    int spreadDistance = 3;

    @Override
    public void onTick(World world, float delta) {
        Vector2i spreadPosition = getPosition().$plus(new Random().nextInt(spreadDistance * 2) - spreadDistance,
                new Random().nextInt(spreadDistance * 2) - spreadDistance);
        Tile spreadTile = world.getTile(spreadPosition);
        spreadPosition.$minus(0, 1);
        if (spreadTile != null && world.getTile(spreadPosition).isAir() && spreadTile.hasSubtile(SubtileGrass.class, false)) {
            world.setTile(spreadPosition, new TileGrass(), true);
        }
    }
}
