package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.engine.renderer.TileRendererMultiblock
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class TileStone() extends Tile {
  override def getImage: Task[TextureRegion] = Resources.TILES.getImage("stone")

  override def getRenderer = new TileRendererMultiblock(Vector2i(2, 2))
}