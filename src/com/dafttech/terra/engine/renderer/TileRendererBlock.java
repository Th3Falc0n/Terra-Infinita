package com.dafttech.terra.engine.renderer;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.ITileRenderBigger;
import com.dafttech.terra.game.world.tiles.ITileRenderOffset;
import com.dafttech.terra.game.world.tiles.ITileRenderRounded;
import com.dafttech.terra.game.world.tiles.Tile;

public class TileRendererBlock extends TileRenderer {
    public static TileRenderer $Instance = new TileRendererBlock();

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Tile tile, Entity pointOfView, Object... rendererArguments) {
        Vector2 screenVec = tile.getPosition().toScreenPos(pointOfView);

        float offX = 0, offY = 0;
        if (tile instanceof ITileRenderOffset) {
            Vector2 offset = ((ITileRenderOffset) tile).getRenderOffset();
            if (offset != null) {
                offX = offset.x;
                offY = offset.y;
            }
        }
        if (tile instanceof ITileRenderBigger) {
            int cols = ((ITileRenderBigger) tile).getRenderSizeMultiplier().x, rows = ((ITileRenderBigger) tile).getRenderSizeMultiplier().y;
            TextureRegion tex = new TextureRegion(tile.getImage());
            int width = tex.getRegionWidth() / cols, height = tex.getRegionHeight() / rows;
            int x = width * ((int) Math.abs(pos.x) % cols), y = height * ((int) Math.abs(pos.y) % rows);
            tex.setRegion(x, y, width, height);
            tex.flip(tile.getImage().isFlipX(), tile.getImage().isFlipY());
            screen.batch.draw(tex, screenVec.x + offX * BLOCK_SIZE, screenVec.y + offY * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        } else if (tile instanceof ITileRenderRounded) {
            ITileRenderRounded rTile = (ITileRenderRounded) tile;
            TextureRegion[] edgeTextures = rTile.getEdgeImages();
            if (edgeTextures.length == 4) {
                boolean t = rTile.isFlatTo(world, tile.getPosition().addY(-1));
                boolean b = rTile.isFlatTo(world, tile.getPosition().addY(1));
                boolean l = rTile.isFlatTo(world, tile.getPosition().addX(-1));
                boolean r = rTile.isFlatTo(world, tile.getPosition().addX(1));
                if (t || l)
                    screen.batch.draw(edgeTextures[0], screenVec.x + offX * BLOCK_SIZE, screenVec.y + offY * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                if (t || r)
                    screen.batch.draw(edgeTextures[1], screenVec.x + offX * BLOCK_SIZE, screenVec.y + offY * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                if (b || l)
                    screen.batch.draw(edgeTextures[2], screenVec.x + offX * BLOCK_SIZE, screenVec.y + offY * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
                if (b || r)
                    screen.batch.draw(edgeTextures[3], screenVec.x + offX * BLOCK_SIZE, screenVec.y + offY * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
            }
        } else {
            screen.batch.draw(tile.getImage(), screenVec.x + offX * BLOCK_SIZE, screenVec.y + offY * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }
    }
}
