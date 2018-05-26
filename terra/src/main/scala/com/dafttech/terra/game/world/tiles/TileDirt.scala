package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.engine.Vector2i
import com.dafttech.terra.engine.renderer.TileRendererMarchingSquares
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class TileDirt extends Tile {
  setHardness(3)

  override def getImage: Task[TextureRegion] = Resources.TILES.getImage("dirt")

  override def getRenderer = new TileRendererMarchingSquares
}