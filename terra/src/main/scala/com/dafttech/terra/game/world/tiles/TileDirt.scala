package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.engine.renderer.TileRendererMultiblock
import com.dafttech.terra.resources.Resources

class TileDirt extends Tile {
  setHardness(3)

  override def getImage: TextureRegion = Resources.TILES.getImage("dirt")

  override def getRenderer = new TileRendererMultiblock(Vector2i(2, 2))
}