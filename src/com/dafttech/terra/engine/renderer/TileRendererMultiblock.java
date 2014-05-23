package com.dafttech.terra.engine.renderer;

import static com.dafttech.terra.resources.Options.BLOCK_SIZE;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.dafttech.terra.engine.AbstractScreen;
import com.dafttech.terra.engine.Vector2;
import com.dafttech.terra.game.world.Vector2i;
import com.dafttech.terra.game.world.World;
import com.dafttech.terra.game.world.entities.Entity;
import com.dafttech.terra.game.world.tiles.Tile;

public class TileRendererMultiblock extends TileRendererBlock {
    protected Vector2i multiblockSize;

    public TileRendererMultiblock(Vector2i multiblockSize) {
        this.multiblockSize = multiblockSize;
    }

    @Override
    public void draw(Vector2 pos, World world, AbstractScreen screen, Tile tile, Entity pointOfView, Object... rendererArguments) {
        Vector2 screenVec = tile.getPosition().toScreenPos(pointOfView);
        TextureRegion texture = tile.getImage();
        int cols = multiblockSize.x, rows = multiblockSize.y;
        int col = ((int) Math.abs(pos.x) % cols), row = ((int) Math.abs(pos.y) % rows);
        if (pos.x < 0) col = ((cols - 1) - col + (cols - 1)) % cols;
        if (pos.y < 0) row = ((rows - 1) - row + (rows - 1)) % rows;
        TextureRegion newTexture = new TextureRegion(texture);
        double width = newTexture.getRegionWidth() / (double) cols, height = newTexture.getRegionHeight() / (double) rows;
        double x = width * col, y = height * row;
        setRegion(newTexture, (float) x, (float) y, (float) width, (float) height);
        newTexture.flip(texture.isFlipX(), texture.isFlipY());
        screen.batch.draw(newTexture, screenVec.x + offset.x * BLOCK_SIZE, screenVec.y + offset.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
    }

    public static void setRegion(TextureRegion texture, float x, float y, float width, float height) {
        float invTexWidth = 1.0F / texture.getTexture().getWidth();
        float invTexHeight = 1.0F / texture.getTexture().getHeight();
        texture.setRegion(x * invTexWidth, y * invTexHeight, (x + width) * invTexWidth, (y + height) * invTexHeight);
    }
}
