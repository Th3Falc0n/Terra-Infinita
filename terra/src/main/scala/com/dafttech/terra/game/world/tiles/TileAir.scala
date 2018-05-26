package com.dafttech.terra.game.world.tiles

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.dafttech.terra.resources.Resources
import monix.eval.Task

class TileAir extends Tile {
  override def getImage: Task[TextureRegion] = Resources.TILES.getImage("air")

  override def isAir: Boolean = true
}